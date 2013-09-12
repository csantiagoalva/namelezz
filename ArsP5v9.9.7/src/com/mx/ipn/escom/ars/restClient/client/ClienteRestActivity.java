package com.mx.ipn.escom.ars.restClient.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.modelo.Elemento;
import com.mx.ipn.escom.ars.restClient.modelo.ElementoLogro;
import com.mx.ipn.escom.ars.restClient.modelo.Exposicion;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionSubtema;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.restClient.modelo.Museo;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoTema;
import com.mx.ipn.escom.ars.restClient.modelo.Recompensa;
import com.mx.ipn.escom.ars.restClient.modelo.Recurso;
import com.mx.ipn.escom.ars.restClient.modelo.Subtema;
import com.mx.ipn.escom.ars.restClient.modelo.Tema;
import com.mx.ipn.escom.ars.restClient.modelo.Version;
import com.mx.ipn.escom.ars.util.Server;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClienteRestActivity extends Activity implements Server {
	Context context = this;
	ProgressDialog dialog = null;
	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	public static final int DIALOG_DECOMPRESS_PROGRESS = 1;
	private ProgressDialog mProgressDialog;	
	String url = url2+"downloadResource.action";
	String zipFile = Environment.getExternalStorageDirectory()+ "/Ars/contenido.zip";
	String unzipLocation = Environment.getExternalStorageDirectory()+ "/Ars/config/";
	File current = new File(unzipLocation);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio);
		
		File aux=new File(Environment.getExternalStorageDirectory()+ "/Ars");
		if(!aux.exists())
			aux.mkdir();
		
		Button btnCarga = (Button) findViewById(R.id.btnCargar);
		btnCarga.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					
					try{
				        URL myUrl = new URL(url2);
				        URLConnection connection = myUrl.openConnection();
				        connection.setConnectTimeout(5000);
				        connection.connect();				        
				        new UpdateTask().execute();
				        
					} catch (Exception e) {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setMessage("No se pudo conectar con el servidor")
								.setTitle("No se pudo actualizar")
								.setCancelable(false)
								.setNeutralButton("Aceptar",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int id) {
												dialog.cancel();
												Intent myIntent = new Intent(context,MenuPrincipalActivity.class);
												startActivityForResult(myIntent, 0);
												ClienteRestActivity.this.finish();
											}
										});
						AlertDialog alert = builder.create();
						alert.show();
					}
					
					
													
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Conexión no disponible")
							.setTitle("Sin actualizaciones")
							.setCancelable(false)
							.setNeutralButton("Aceptar",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int id) {
											dialog.cancel();
											Intent myIntent = new Intent(context,MenuPrincipalActivity.class);
											startActivityForResult(myIntent, 0);
											ClienteRestActivity.this.finish();
										}
									});
					AlertDialog alert = builder.create();
					alert.show();				
				}

			}
		});
	}

	private class UpdateTask extends AsyncTask {
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ClienteRestActivity.this, "",
					"Actualizando la base de datos...", true);
		}

		@Override
		protected Object doInBackground(Object... params) {
			try{
			Log.v("prueba", "intentando enviar");
			ExposicionChecadaClient ecc=new ExposicionChecadaClient(context);
			try {
				ecc.send();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}catch(Exception e){
				System.out.println("no hay base, no se puede mandar");
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
//			TextView text = (TextView) findViewById(R.id.textView1);
			Log.v("CLiente Rest", "Antes de entrar");

			BaseARSHelper baseARS = new BaseARSHelper(context);
			SQLiteDatabase db = baseARS.getWritableDatabase();
			baseARS.onCreate(db);
			baseARS.crearAux(db);
			db.close();
			
			
			VersionClient versionClient = new VersionClient(context);
			List<Version> version = versionClient.getVersiones();
			
			if (versionClient.getMapaVersionLocal().size() < 1) {
				System.out.println("no tiene nada la base local");
				TemaClient temaClient = new TemaClient(context);
				List<Tema> temas = temaClient.getTemas();

				SubtemaClient subtemaClient = new SubtemaClient(context);
				List<Subtema> subtemas = subtemaClient.getSubtemas();

				ExposicionClient exposicionClient = new ExposicionClient(
						context);
				List<Exposicion> exposiciones = exposicionClient
						.getExposiciones();

				ExposicionSubtemaClient exposicionSubtemaClient = new ExposicionSubtemaClient(
						context);
				List<ExposicionSubtema> exposicionesSubtemas = exposicionSubtemaClient
						.getExposicionesSubtemas();

				MuseoClient museoClient = new MuseoClient(context);
				List<Museo> museos = museoClient.getMuseos();

				MuseoTemaClient museoTemaClient = new MuseoTemaClient(context);
				List<MuseoTema> museosTemas = museoTemaClient.getMuseosTemas();

				ElementoClient elementoClient = new ElementoClient(context);
				List<Elemento> elementos = elementoClient.getElementos();

				LogroClient logroClient = new LogroClient(context);
				List<Logro> logros = logroClient.getLogros();

				ElementoLogroClient elementoLogroClient = new ElementoLogroClient(
						context);
				List<ElementoLogro> elementosLogros = elementoLogroClient
						.getElementosLogros();

				RecompensaClient recompensaClient = new RecompensaClient(
						context);
				List<Recompensa> recompensas = recompensaClient
						.getRecompensas();
			} else {
				HashMap<String, Integer> mapaVersionLocal = versionClient
						.getMapaVersionLocal();
				HashMap<String, Integer> mapaVersion = versionClient
						.getMapaVersion();
				if (mapaVersionLocal.containsKey("Tema")) {
					if (mapaVersionLocal.get("Tema") < mapaVersion.get("Tema")) {
						TemaClient temaClient = new TemaClient(context);
						temaClient.dropTema();
						List<Tema> temas = temaClient.getTemas();
						System.out.println("Se actualizo Tema");
					}
				} else {
					TemaClient temaClient = new TemaClient(context);
					temaClient.dropTema();
					List<Tema> temas = temaClient.getTemas();
					System.out.println("Se actualizo Tema");
				}
				if (mapaVersionLocal.containsKey("Subtema")) {
					if (mapaVersionLocal.get("Subtema") < mapaVersion
							.get("Subtema")) {
						SubtemaClient subtemaClient = new SubtemaClient(context);
						subtemaClient.dropSubtema();
						List<Subtema> subtemas = subtemaClient.getSubtemas();
						System.out.println("Se actualizo Subtema");
					}
				} else {
					SubtemaClient subtemaClient = new SubtemaClient(context);
					subtemaClient.dropSubtema();
					List<Subtema> subtemas = subtemaClient.getSubtemas();
					System.out.println("Se actualizo Subtema");
				}
				if (mapaVersionLocal.containsKey("Exposicion")) {
					if (mapaVersionLocal.get("Exposicion") < mapaVersion
							.get("Exposicion")) {
						ExposicionClient exposicionClient = new ExposicionClient(
								context);
						exposicionClient.dropExposicion();
						List<Exposicion> exposiciones = exposicionClient
								.getExposiciones();
						System.out.println("Se actualizo Exposicion");
					}
				} else {
					ExposicionClient exposicionClient = new ExposicionClient(
							context);
					exposicionClient.dropExposicion();
					List<Exposicion> exposiciones = exposicionClient
							.getExposiciones();
					System.out.println("Se actualizo Exposicion");
				}
				if (mapaVersionLocal.containsKey("ExposicionSubtema")) {
					if (mapaVersionLocal.get("ExposicionSubtema") < mapaVersion
							.get("ExposicionSubtema")) {
						ExposicionSubtemaClient exposicionSubtemaClient = new ExposicionSubtemaClient(
								context);
						exposicionSubtemaClient.dropExposicionSubtema();
						List<ExposicionSubtema> exposicionesSubtemas = exposicionSubtemaClient
								.getExposicionesSubtemas();
						System.out.println("Se actualizo ExposicionSubtema");
					}
				} else {
					ExposicionSubtemaClient exposicionSubtemaClient = new ExposicionSubtemaClient(
							context);
					exposicionSubtemaClient.dropExposicionSubtema();
					List<ExposicionSubtema> exposicionesSubtemas = exposicionSubtemaClient
							.getExposicionesSubtemas();
					System.out.println("Se actualizo ExposicionSubtema");
				}
				if (mapaVersionLocal.containsKey("Museo")) {
					if (mapaVersionLocal.get("Museo") < mapaVersion
							.get("Museo")) {
						MuseoClient museoClient = new MuseoClient(context);
						museoClient.dropMuseo();
						List<Museo> museos = museoClient.getMuseos();
						System.out.println("Se actualizo Museo");
					}
				} else {
					MuseoClient museoClient = new MuseoClient(context);
					museoClient.dropMuseo();
					List<Museo> museos = museoClient.getMuseos();
					System.out.println("Se actualizo Museo");
				}
				if (mapaVersionLocal.containsKey("MuseoTema")) {
					if (mapaVersionLocal.get("MuseoTema") < mapaVersion
							.get("MuseoTema")) {
						MuseoTemaClient museoTemaClient = new MuseoTemaClient(
								context);
						museoTemaClient.dropMuseoTema();
						List<MuseoTema> museosTemas = museoTemaClient
								.getMuseosTemas();
						System.out.println("Se actualizo MuseoTema");
					}
				} else {
					MuseoTemaClient museoTemaClient = new MuseoTemaClient(
							context);
					museoTemaClient.dropMuseoTema();
					List<MuseoTema> museosTemas = museoTemaClient
							.getMuseosTemas();
					System.out.println("Se actualizo MuseoTema");
				}
				if (mapaVersionLocal.containsKey("Elemento")) {
					if (mapaVersionLocal.get("Elemento") < mapaVersion
							.get("Elemento")) {
						ElementoClient elementoClient = new ElementoClient(
								context);
						elementoClient.dropElemento();
						List<Elemento> elementos = elementoClient
								.getElementos();
						System.out.println("Se actualizo Elemento");
					}
				} else {
					ElementoClient elementoClient = new ElementoClient(context);
					elementoClient.dropElemento();
					List<Elemento> elementos = elementoClient.getElementos();
					System.out.println("Se actualizo Elemento");
				}
				if (mapaVersionLocal.containsKey("Logro")) {
					if (mapaVersionLocal.get("Logro") < mapaVersion
							.get("Logro")) {
						LogroClient logroClient = new LogroClient(context);
						logroClient.dropLogro();
						List<Logro> logros = logroClient.getLogros();
						System.out.println("Se actualizo Logro");
					}
				} else {
					LogroClient logroClient = new LogroClient(context);
					logroClient.dropLogro();
					List<Logro> logros = logroClient.getLogros();
					System.out.println("Se actualizo Logro");
				}
				if (mapaVersionLocal.containsKey("ElementoLogro")) {
					if (mapaVersionLocal.get("ElementoLogro") < mapaVersion
							.get("ElementoLogro")) {
						ElementoLogroClient elementoLogroClient = new ElementoLogroClient(
								context);
						elementoLogroClient.dropElementoLogro();
						List<ElementoLogro> elementosLogros = elementoLogroClient
								.getElementosLogros();
						System.out.println("Se actualizo ElementoLogro");
					}
				} else {
					ElementoLogroClient elementoLogroClient = new ElementoLogroClient(
							context);
					elementoLogroClient.dropElementoLogro();
					List<ElementoLogro> elementosLogros = elementoLogroClient
							.getElementosLogros();
					System.out.println("Se actualizo ElementoLogro");
				}
				if (mapaVersionLocal.containsKey("Recompensa")) {
					if (mapaVersionLocal.get("Recompensa") < mapaVersion
							.get("Recompensa")) {
						RecompensaClient recompensaClient = new RecompensaClient(
								context);
						recompensaClient.dropRecompensa();
						List<Recompensa> recompensas = recompensaClient
								.getRecompensas();
						System.out.println("Se actualizo Recompensa");
					}
				} else {
					RecompensaClient recompensaClient = new RecompensaClient(
							context);
					recompensaClient.dropRecompensa();
					List<Recompensa> recompensas = recompensaClient
							.getRecompensas();
					System.out.println("Se actualizo Recompensa");
				}
			}

			Log.v("CLiente Rest", "Despues de entrar");
			String str = "";
			return null;
		}

		protected void onPostExecute(Object result) {
			ClienteRestActivity.this.dialog.dismiss();
			startDownload();
		}
	}

	private void startDownload() {
		String zipFile = Environment.getExternalStorageDirectory()+ "/Ars/config";
		File current = new File(zipFile);
		if(current.exists() && dirSize(current)>0){																										
			deleteFiles(current);
			current.delete();
		}		
		current.delete();				
		
		Log.v("url", url);
		new DownloadFileAsync().execute(url);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Descargando contenido...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		case DIALOG_DECOMPRESS_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Descomprimiendo archivos...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	class DownloadFileAsync extends AsyncTask<String, Integer, String> {
		int progress;

		@Override
		protected void onPreExecute() {
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				System.out.println(conexion.getContentLength());

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(zipFile);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress((int) (total * 100 / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
			}
			return null;

		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			mProgressDialog.setProgress(progress[0]);

		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			new DecompressFileAsync(zipFile, unzipLocation).execute();
		}
	}

	// ///////////////unzip
	class DecompressFileAsync extends AsyncTask<String, Integer, String> {
		int progress;

		@Override
		protected void onPreExecute() {
			showDialog(DIALOG_DECOMPRESS_PROGRESS);
		}

		private String _zipFile;
		private String _location;

		public DecompressFileAsync(String zipFile, String location) {
			_zipFile = zipFile;
			_location = location;
			_dirChecker("");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				int c = 0;
				FileInputStream fin = new FileInputStream(_zipFile);
				ZipInputStream zin = new ZipInputStream(fin);
				ZipEntry ze = null;
				File file = new File(_zipFile);
				int lenghtOfFile = (int) file.length();
				long total = 0;
				while ((ze = zin.getNextEntry()) != null) {
					Log.v("Decompress", "Unzipping " + ze.getName());

					if (ze.isDirectory()) {
						_dirChecker(ze.getName());
					} else {
						FileOutputStream fout = new FileOutputStream(_location
								+ ze.getName());

						byte data[] = new byte[1024];
						while ((c = zin.read(data)) != -1) {

							total += c;
							publishProgress((int) (total * 100 / lenghtOfFile));

							fout.write(data, 0, c);
						}

						zin.closeEntry();
						fout.close();
					}

				}
				zin.close();
			} catch (Exception e) {
				Log.e("Decompress", "unzip", e);
			}

			return null;

		}

		private void _dirChecker(String dir) {
			File f = new File(_location + dir);

			if (!f.isDirectory()) {
				f.mkdirs();
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			mProgressDialog.setProgress(progress[0]);

		}

		@Override
		protected void onPostExecute(String unused) {
			String zipFile = Environment.getExternalStorageDirectory()+ "/Ars/contenido.zip";
			File file = new File(zipFile);
			file.delete();

			mProgressDialog.cancel();
			mProgressDialog.dismiss();			
			// dismissDialog(DIALOG_DECOMPRESS_PROGRESS);

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Se ha actualizado la base de datos")
					.setTitle("Actualizacion exitosa")
					.setCancelable(false)
					.setNeutralButton("Aceptar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									dialog.dismiss();									
									Intent myIntent = new Intent(context,MenuPrincipalActivity.class);
									startActivityForResult(myIntent, 0);
									ClienteRestActivity.this.finish();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}

	}
	
	private void deleteFiles(File dir) {	    
	    Stack<File> dirlist= new Stack<File>();
	    dirlist.clear();
	    dirlist.push(dir);

	    while(!dirlist.isEmpty())
	    {
	        File dirCurrent = dirlist.pop();
	        File[] fileList = dirCurrent.listFiles();
	        for (int i = 0; i < fileList.length; i++) {
	            if(fileList[i].isDirectory())
	                deleteFiles(fileList[i]);
	            else	            	
	                fileList[i].delete();
	        }
	    }	    
	}
	
	private long dirSize(File dir) {
	    long result = 0;

	    Stack<File> dirlist= new Stack<File>();
	    dirlist.clear();

	    dirlist.push(dir);

	    while(!dirlist.isEmpty())
	    {
	        File dirCurrent = dirlist.pop();

	        File[] fileList = dirCurrent.listFiles();
	        for (int i = 0; i < fileList.length; i++) {

	            if(fileList[i].isDirectory())
	                dirlist.push(fileList[i]);
	            else
	                result += fileList[i].length();
	        }
	    }
	    return result;
	}
}