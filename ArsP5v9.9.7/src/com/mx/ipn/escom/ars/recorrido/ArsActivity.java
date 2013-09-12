
package com.mx.ipn.escom.ars.recorrido;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

import min3d.Min3d;
import min3d.Shared;
import min3d.animation.AnimationObject3d;
import min3d.core.Object3dContainer;
import min3d.core.RenderCaps;
import min3d.core.Scene;
import min3d.interfaces.ISceneController;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.Number3d;
//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.ElementoVistoDao;
import com.mx.ipn.escom.ars.restClient.dao.LogroDao;
import com.mx.ipn.escom.ars.restClient.dao.LogroObtenidoDao;
import com.qualcomm.QCAR.QCAR;
import com.ArsMovil.R;

/** The main activity for the ArsActivity sample. */
public class ArsActivity extends Activity implements ISceneController {
	ElementoVistoDao elementoVistoDao;
//	BaseARSHelper baseArsH;
	LogroObtenidoDao logroObtenidoDao;
	// Audio
	boolean audioActivo = false;
	String expoId;

	// Min3d
	public Scene scene;
	protected Handler _initSceneHander;
	protected Handler _updateSceneHander;
	

	public static final String PREFS_NAME = "Configuracion";
	SharedPreferences settings;	

	final Runnable _initSceneRunnable = new Runnable() {
		public void run() {
			onInitScene();
		}
	};

	final Runnable _updateSceneRunnable = new Runnable() {
		public void run() {
			onUpdateScene();
		}
	};

	// Application status constants:
	private static final int APPSTATUS_UNINITED = -1;
	private static final int APPSTATUS_INIT_APP = 0;
	private static final int APPSTATUS_INIT_QCAR = 1;
	private static final int APPSTATUS_INIT_TRACKER = 2;
	private static final int APPSTATUS_INIT_APP_AR = 3;
	private static final int APPSTATUS_LOAD_TRACKER = 4;
	private static final int APPSTATUS_INITED = 5;
	private static final int APPSTATUS_CAMERA_STOPPED = 6;
	private static final int APPSTATUS_CAMERA_RUNNING = 7;

	// Name of the native dynamic libraries to load:
	private static final String NATIVE_LIB_SAMPLE = "Ars";
	private static final String NATIVE_LIB_QCAR = "QCAR";

	// Our OpenGL view:
	private ArsGLView mGlView;

	// The view to display the sample splash screen:
	private ImageView mSplashScreenView;

	// The handler and runnable for the splash screen time out task.
	private Handler mSplashScreenHandler;
	private Runnable mSplashScreenRunnable;

	// The minimum time the splash screen should be visible:
	private static final long MIN_SPLASH_SCREEN_TIME = 2000;

	// The time when the splash screen has become visible:
	long mSplashScreenStartTime = 0;

	// Our renderer:
	private ArsRenderer mRenderer;

	// Display size of the device
	private int mScreenWidth = 0;
	private int mScreenHeight = 0;

	// The current application status
	private int mAppStatus = APPSTATUS_UNINITED;

	// The async tasks to initialize the QCAR SDK
	private InitQCARTask mInitQCARTask;
	private LoadTrackerTask mLoadTrackerTask;
	private Object mShutdownLock = new Object();

	// QCAR initialization flags
	private int mQCARFlags = 0;

	private int mSplashScreenImageResource = 0;

	// The menu item for swapping data sets:
	MenuItem mDataSetMenuItem = null;
	boolean mIsStonesAndChipsDataSetActive = false;

	Intent in;
	/** Static initializer block to load native libraries on start-up. */
	static {
		loadLibrary(NATIVE_LIB_QCAR);
		loadLibrary(NATIVE_LIB_SAMPLE);				
	}

	/** An async task to initialize QCAR asynchronously. */
	private class InitQCARTask extends AsyncTask<Void, Integer, Boolean> {
		private int mProgressValue = -1;
		protected Boolean doInBackground(Void... params) {
			synchronized (mShutdownLock) {
				QCAR.setInitParameters(ArsActivity.this, mQCARFlags);

				do {
					mProgressValue = QCAR.init();
					publishProgress(mProgressValue);
				} while (!isCancelled() && mProgressValue >= 0
						&& mProgressValue < 100);
				return (mProgressValue > 0);
			}
		}

		protected void onProgressUpdate(Integer... values) {
		}

		protected void onPostExecute(Boolean result) {
			if (result) {
				DebugLog.LOGD("InitQCARTask::onPostExecute: QCAR initialization"
						+ " successful");

				updateApplicationStatus(APPSTATUS_INIT_TRACKER);
			} else {
				// Create dialog box for display error:
				AlertDialog dialogError = new AlertDialog.Builder(
						ArsActivity.this).create();
				dialogError.setButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Exiting application
								System.exit(1);
							}
						});

				String logMessage;

				if (mProgressValue == QCAR.INIT_DEVICE_NOT_SUPPORTED) {
					logMessage = "Failed to initialize QCAR because this "
							+ "device is not supported.";
				} else {
					logMessage = "Failed to initialize QCAR.";
				}

				// Log error:
				DebugLog.LOGE("InitQCARTask::onPostExecute: " + logMessage
						+ " Exiting.");

				dialogError.setMessage(logMessage);
				dialogError.show();
			}
		}
	}

	/** An async task to load the tracker data asynchronously. */
	private class LoadTrackerTask extends AsyncTask<Void, Integer, Boolean> {
		protected Boolean doInBackground(Void... params) {
			synchronized (mShutdownLock) {
				String path = Environment.getExternalStorageDirectory() + "/Ars/current/Expo_"+expoId+"/"+expoId+".xml";
				return (loadTrackerData(path) > 0);
			}
		}

		protected void onPostExecute(Boolean result) {
			DebugLog.LOGD("LoadTrackerTask::onPostExecute: execution "
					+ (result ? "successful" : "failed"));

			if (result) {
				// The stones and chips data set is now active:
				mIsStonesAndChipsDataSetActive = true;

				// Done loading the tracker, update application status:
				updateApplicationStatus(APPSTATUS_INITED);
			} else {
				// Create dialog box for display error:
				AlertDialog dialogError = new AlertDialog.Builder(
						ArsActivity.this).create();
				dialogError.setButton("Close",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Exiting application
								System.exit(1);
							}
						});

				// Show dialog box with error message:
				dialogError.setMessage("Failed to load tracker data.");
				dialogError.show();
			}
		}
	}

	private void storeScreenDimensions() {
		// Query display dimensions
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;
		mScreenHeight = metrics.heightPixels;
	}

	/**
	 * Called when the activity first starts or the user navigates back to an
	 * activity.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		DebugLog.LOGD("ArsActivity::onCreate");
		super.onCreate(savedInstanceState);
		logroObtenidoDao=new LogroObtenidoDao(this);
		settings = getSharedPreferences(PREFS_NAME, 0);
		
		ArsRenderer.context = null;
		Bundle datos = this.getIntent().getExtras();		
		expoId=datos.getString("ExpoID");
		
		_initSceneHander = new Handler();
		_updateSceneHander = new Handler();
		
		mSplashScreenImageResource = R.drawable.loading;
		
		mQCARFlags = getInitializationFlags();

		updateApplicationStatus(APPSTATUS_INIT_APP);		
		
		createAudio();
	}

	/**
	 * We want to load specific textures from the APK, which we will later use
	 * for rendering.
	 */
	private void loadTextures() {
	}

	/** Configure QCAR with the desired version of OpenGL ES. */
	private int getInitializationFlags() {
		int flags = 0;

		// Query the native code:
		if (getOpenGlEsVersionNative() == 1) {
			flags = QCAR.GL_11;
		} else {
			flags = QCAR.GL_20;
		}

		return flags;
	}

	/**
	 * native method for querying the OpenGL ES version. Returns 1 for OpenGl ES
	 * 1.1, returns 2 for OpenGl ES 2.0.
	 */
	public native int getOpenGlEsVersionNative();

	/** Native tracker initialization and deinitialization. */
	public native int initTracker();

	public native void deinitTracker();

	/** Native functions to load and destroy tracking data. */
	public native int loadTrackerData(String fileName);

	public native void destroyTrackerData();

	/** Native sample initialization. */
	public native void onQCARInitializedNative();

	/** Native methods for starting and stoping the camera. */
	private native void startCamera();

	private native void stopCamera();

	/**
	 * Native method for setting / updating the projection matrix for AR content
	 * rendering
	 */
	private native void setProjectionMatrix();

	/** Called when the activity will start interacting with the user. */
	protected void onResume() {
		DebugLog.LOGD("ArsActivity::onResume");
		super.onResume();
		playAudio();
		// QCAR-specific resume operation
		QCAR.onResume();
		
		// We may start the camera only if the QCAR SDK has already been
		// initialized
		if (mAppStatus == APPSTATUS_CAMERA_STOPPED) {
			updateApplicationStatus(APPSTATUS_CAMERA_RUNNING);

			// Reactivate flash if it was active before pausing the app
			if (mFlash) {
				boolean result = activateFlash(mFlash);
				DebugLog.LOGI("Turning flash " + (mFlash ? "ON" : "OFF") + " "
						+ (result ? "WORKED" : "FAILED") + "!!");
			}
		}

		// Resume the GL view:
		if (mGlView != null) {
			mGlView.setVisibility(View.VISIBLE);
			mGlView.onResume();
		}
	}

	public void onConfigurationChanged(Configuration config) {
		DebugLog.LOGD("ArsActivity::onConfigurationChanged");
		super.onConfigurationChanged(config);

		storeScreenDimensions();

		// Set projection matrix:
		if (QCAR.isInitialized())
			setProjectionMatrix();
	}

	@Override
	public void onBackPressed() {
	    	   
		elementoVistoDao=new ElementoVistoDao(this);				
		Double pr=elementoVistoDao.vistos(Integer.parseInt(expoId));
		
		if(pr>=70){
			Log.v("result", "validacion");
			in = new Intent();
			setResult(Integer.parseInt(expoId),in);			
		}
		Log.v("ArsActivity", "Porcentaje visto: "+pr);
		
	    
	    super.onBackPressed();
	}
	
	/** Called when the system is about to start resuming a previous activity. */
	protected void onPause() {
		DebugLog.LOGD("ArsActivity::onPause");
		super.onPause();
				
		
		pauseAudio();

		if (mGlView != null) {
			mGlView.setVisibility(View.INVISIBLE);
			mGlView.onPause();
		}

		if (mAppStatus == APPSTATUS_CAMERA_RUNNING) {
			updateApplicationStatus(APPSTATUS_CAMERA_STOPPED);
		}

		QCAR.onPause();
	}

	/** Native function to deinitialize the application. */
	private native void deinitApplicationNative();

	/** The final call you receive before your activity is destroyed. */
	protected void onDestroy() {
		DebugLog.LOGD("ArsActivity::onDestroy");
		super.onDestroy();

		stopAudio();

		// Dismiss the splash screen time out handler:
		if (mSplashScreenHandler != null) {
			mSplashScreenHandler.removeCallbacks(mSplashScreenRunnable);
			mSplashScreenRunnable = null;
			mSplashScreenHandler = null;
		}

		// Cancel potentially running tasks
		if (mInitQCARTask != null
				&& mInitQCARTask.getStatus() != InitQCARTask.Status.FINISHED) {
			mInitQCARTask.cancel(true);
			mInitQCARTask = null;
		}

		if (mLoadTrackerTask != null
				&& mLoadTrackerTask.getStatus() != LoadTrackerTask.Status.FINISHED) {
			mLoadTrackerTask.cancel(true);
			mLoadTrackerTask = null;
		}

		// Ensure that all asynchronous operations to initialize QCAR and
		// loading
		// the tracker datasets do not overlap:
		synchronized (mShutdownLock) {

			// Do application deinitialization in native code
			deinitApplicationNative();

			// Destroy the tracking data set:
			destroyTrackerData();

			// Deinit the tracker:
			deinitTracker();

			// Deinitialize QCAR SDK
			QCAR.deinit();
		}

		System.gc();
		
	}

	/**
	 * NOTE: this method is synchronized because of a potential concurrent
	 * access by ArsActivity::onResume() and InitQCARTask::onPostExecute().
	 */
	private synchronized void updateApplicationStatus(int appStatus) {
		// Exit if there is no change in status
		if (mAppStatus == appStatus)
			return;

		// Store new status value
		mAppStatus = appStatus;

		// Execute application state-specific actions
		switch (mAppStatus) {
		case APPSTATUS_INIT_APP:
			initApplication();			
			// Proceed to next application initialization status
			updateApplicationStatus(APPSTATUS_INIT_QCAR);
			break;

		case APPSTATUS_INIT_QCAR:
			try {
				mInitQCARTask = new InitQCARTask();
				mInitQCARTask.execute();
			} catch (Exception e) {
				DebugLog.LOGE("Initializing QCAR SDK failed");
			}
			break;

		case APPSTATUS_INIT_TRACKER:
			// Initialize the ImageTracker
			if (initTracker() > 0) {
				// Proceed to next application initialization status
				updateApplicationStatus(APPSTATUS_INIT_APP_AR);
			}
			break;

		case APPSTATUS_INIT_APP_AR:
			initApplicationAR();

			// Proceed to next application initialization status
			updateApplicationStatus(APPSTATUS_LOAD_TRACKER);
			break;

		case APPSTATUS_LOAD_TRACKER:
			try {
				mLoadTrackerTask = new LoadTrackerTask();
				mLoadTrackerTask.execute();
			} catch (Exception e) {
				DebugLog.LOGE("Loading tracking data set failed");
			}
			break;

		case APPSTATUS_INITED:
			System.gc();

			// Native post initialization:
			onQCARInitializedNative();

			// The elapsed time since the splash screen was visible:
			long splashScreenTime = System.currentTimeMillis()
					- mSplashScreenStartTime;
			long newSplashScreenTime = 0;
			if (splashScreenTime < MIN_SPLASH_SCREEN_TIME) {
				newSplashScreenTime = MIN_SPLASH_SCREEN_TIME - splashScreenTime;
			}

			mSplashScreenHandler = new Handler();
			mSplashScreenRunnable = new Runnable() {
				public void run() {

					mSplashScreenView.setVisibility(View.INVISIBLE);

					mRenderer.mIsActive = true;
					
					addContentView(mGlView, new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

					updateApplicationStatus(APPSTATUS_CAMERA_RUNNING);
				}
			};

			mSplashScreenHandler.postDelayed(mSplashScreenRunnable,
					newSplashScreenTime);
			break;

		case APPSTATUS_CAMERA_STOPPED:
			stopCamera();
			break;

		case APPSTATUS_CAMERA_RUNNING:
			startCamera();
			setProjectionMatrix();
			break;

		default:
			throw new RuntimeException("Invalid application state");
		}
	}

	/** Tells native code whether we are in portait or landscape mode */
	private native void setActivityPortraitMode(boolean isPortrait);

	/** Initialize application GUI elements that are not related to AR. */
	private void initApplication() {

		int screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

		setRequestedOrientation(screenOrientation);

		setActivityPortraitMode(screenOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		storeScreenDimensions();

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mSplashScreenView = new ImageView(this);
		mSplashScreenView.setImageResource(mSplashScreenImageResource);
		addContentView(mSplashScreenView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		mSplashScreenStartTime = System.currentTimeMillis();

	}

	/** Native function to initialize the application. */
	private native void initApplicationNative(int width, int height);

	/** Initializes AR application components. */
	private void initApplicationAR() {
		// Do application initialization in native code (e.g. registering
		// callbacks, etc.)
		initApplicationNative(mScreenWidth, mScreenHeight);

		// Create OpenGL ES view:
		int depthSize = 16;
		int stencilSize = 0;
		boolean translucent = QCAR.requiresAlpha();

		// Min 3D
		Shared.context(this);
		scene = new Scene(this);
		mRenderer = new ArsRenderer(scene, this);
		Shared.renderer(mRenderer);

		
		mGlView = new ArsGLView(this);
		
		mGlView.init(mQCARFlags, translucent, depthSize, stencilSize);

		mGlView.setRenderer(mRenderer);
		// mGlView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	/**
	 * Invoked the first time when the options menu is displayed to give the
	 * Activity a chance to populate its Menu with menu items.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add("Reproducir");
		return true;
	}

	/** Tells native code to switch dataset as soon as possible */
	private native void switchDatasetAsap();

	/** Invoked when the user selects an item from the Menu */
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Reproducir")){
			fatso.setFps(5);
			fatso.play(44,79,true);	        
	        this.playAudio();	        
		}		 		

		return true;
	}

	private MenuItem checked;
	private boolean mFlash = false;

	private native boolean activateFlash(boolean flash);

	private native boolean autofocus();

	private native boolean setFocusMode(int mode);	

	/** A helper for loading native libraries stored in "libs/armeabi*". */
	public static boolean loadLibrary(String nLibName) {
		try {
			System.loadLibrary(nLibName);
			DebugLog.LOGI("Native library lib" + nLibName + ".so loaded");
			return true;
		} catch (UnsatisfiedLinkError ulee) {
			DebugLog.LOGE("The library lib" + nLibName+ ".so could not be loaded");
		} catch (SecurityException se) {
			DebugLog.LOGE("The library lib" + nLibName+ ".so was not allowed to be loaded");
		}

		return false;
	}

	/**
	 * Min 3D
	 */
	
	private AnimationObject3d fatso; 	
	IParser parser;
	
	public void initScene() {
		Log.i(Min3d.TAG, "escena iniciada");
					        	
	    IParser parser;
		parser = Parser.createParser(Parser.Type.MD2, getResources(),
				settings.getString("modelDefault", "Carl"), true);
		parser.parse();

		fatso = parser.getParsedAnimationObject(1);
		fatso.scale().x = fatso.scale().y = fatso.scale().z = 1.00f;
		fatso.position().x = -20;
		fatso.position().y = -10;
		fatso.rotation().z = -90;
//		fatso.rotation().x = -10;
		fatso.rotation().x = -90; //Rota el modelo
		scene.addChild(fatso);		    
		
		Light light = new Light();
		scene.lights().add(light);

		scene.camera().position.y = 1;
		scene.camera().target = new Number3d(0, 1, 0);

		fatso.setFps(10);
		fatso.play(1,15,false,fatso);
			
	}

	public void updateScene() {

	}

	public void onInitScene() {
	}

	public void onUpdateScene() {
	}

	public Handler getInitSceneHandler() {
		return _initSceneHander;
	}

	public Handler getUpdateSceneHandler() {
		return _updateSceneHander;
	}

	public Runnable getInitSceneRunnable() {
		return _initSceneRunnable;
	}

	public Runnable getUpdateSceneRunnable() {
		return _updateSceneRunnable;
	}

	GLSurfaceView glSurfaceView() {
		return mGlView;
	}

	/**
	 * Audio
	 */
	private MediaPlayer mp;
	private AssetFileDescriptor descriptor;
	private String archivo;

	/**
	 * Se crean todo lo necesario para manejar el audio
	 */
	public void createAudio() {
		mp = new MediaPlayer();
		mp.setLooping(false);
		
	}

	/**
	 * Con este metodo se seteara el archivo de audio que se reproducira,
	 * createAuio debio ser llamado antes
	 */
	public void setAudio(String audioNombre) {
		try {			
			String PATH_TO_FILE = Environment.getExternalStorageDirectory() + "/Ars/current/Expo_"+expoId+"/"+audioNombre;						
			mp.reset();
			mp.setDataSource((new FileInputStream(PATH_TO_FILE)).getFD());
			mp.prepare();
			
			setArchivo(audioNombre);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!mp.equals(null)){
			mp.setOnCompletionListener(new OnCompletionListener() {			
				public void onCompletion(MediaPlayer arg0) {					
					LayoutInflater inflater = getLayoutInflater();
					View layout = inflater.inflate(R.layout.toast_logro_layout,(ViewGroup) findViewById(R.id.toast_layout_root));
					final Toast toast = new Toast(getApplicationContext());
					final TextView text = (TextView) layout.findViewById(R.id.text);
					MensajeLogro mensajeLogro;
										
					mensajeLogro=logroObtenidoDao.ObtenerLogro(getArchivo().substring(0, getArchivo().length()-4));
					
					if(mensajeLogro.obtenido){
			        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			        toast.setDuration(Toast.LENGTH_LONG);
			        toast.setView(layout);
			        text.setText(mensajeLogro.mensaje);
		    		toast.show();
					}
					fatso.setFps(2);
					fatso.play(17,43,true);	
										
				}
			});
		}
	}

	/**
	 * Reproduce el audio, setAudio debio ser llamado antes
	 */
	public void playAudio() {
		mp.start();				
	}

	/**
	 * Pausa el audio, setAudio debio ser llamado antes
	 */
	public void pauseAudio() {
		mp.pause();		
	}

	/**
	 * Detiene el audio, setAudio debio ser llamado antes
	 */
	public void stopAudio() {		
		mp.stop();
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public AnimationObject3d getFatso() {
		return fatso;
	}

	public void setFatso(AnimationObject3d fatso) {
		this.fatso = fatso;
	}
	
	public void getLogros(){
		
	}		
}
