package com.mx.ipn.escom.ars.recorrido;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.logros.LogrosListadoActivity;
import com.mx.ipn.escom.ars.logros.LogrosMuseoTabsActivity;
import com.mx.ipn.escom.ars.logros.RecompensasActivity;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.ElementoVistoDao;
import com.mx.ipn.escom.ars.restClient.dao.LogroDao;
import com.mx.ipn.escom.ars.restClient.dao.MuseoDao;
import com.mx.ipn.escom.ars.util.Server;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuMuseoActivity extends Activity implements Server {
	Button logros;
	Button exposiciones;
	Button recompensas;

	public static final String PREFS_NAME = "Configuracion";
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	private MuseoDao museoDao;
	private Context context = this;

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	public static final int DIALOG_DECOMPRESS_PROGRESS = 1;

	private ProgressDialog mProgressDialog;
	String museo = "";

	String zipFile = Environment.getExternalStorageDirectory()
			+ "/Ars/museo.zip";
	String unzipLocation = Environment.getExternalStorageDirectory()
			+ "/Ars/current/";
	String url;
	File current = new File(unzipLocation);

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences(PREFS_NAME, 0);
		
		System.out.println("EN ONCREATE");

		Bundle datos = this.getIntent().getExtras();
		museo = datos.getString("MuseoID");
		url = url2 + "downloadResource.action?idMuseoSel=" + museo;
		setContentView(R.layout.menumuseo);

		logros = (Button) findViewById(R.id.logros);
		exposiciones = (Button) findViewById(R.id.exposiciones);
		recompensas = (Button) findViewById(R.id.recompensas);

		if (datos.getString("Action").equals("Descarga")) {
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {

				try {
					URL myUrl = new URL(url);
					URLConnection connection = myUrl.openConnection();
					connection.setConnectTimeout(5000);
					connection.connect();
				
					if (!current.exists()) {
						System.out.println("no existe la carpeta current");
						
							questionDownload();
						
					} else {
						if (dirSize(current) == 0) {
							System.out.println("la carpeta esta vacia");

							questionDownload();

						} else {
							if (!settings.getString("museoDownloaded", "0")
									.equals(museo)) {
								System.out
										.println("los museos descargados son diferentes");
								questionDownload();
							} else {								
								confirmNoDownload();

							}
						}
					}					
											

				} catch (Exception e) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setMessage("No se pudo conectar con el servidor")
							.setTitle("No se pudo actualizar")
							.setCancelable(false)
							.setNeutralButton("Aceptar",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
											dialog.dismiss();											
											MenuMuseoActivity.this.finish();
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
				}

			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Conexión no disponible")
						.setTitle("No se puede descargar el contenido")
						.setCancelable(false)
						.setNeutralButton("Aceptar",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										dialog.dismiss();
										MenuMuseoActivity.this.finish();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
	}


		exposiciones.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(v.getContext(), ExposicionesListadoActivity.class);
				myIntent.putExtra("MuseoID", museo);
				startActivityForResult(myIntent, 0);
			}
		});

		logros.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String nombre;
				museoDao = new MuseoDao(context);
				museoDao.open();
				nombre = museoDao.findById(Integer.parseInt(museo));
				museoDao.close();

				Intent myIntent = new Intent(v.getContext(),
						LogrosMuseoTabsActivity.class);
				myIntent.putExtra("Museo", nombre);
				myIntent.putExtra("MuseoID", museo);
				startActivityForResult(myIntent, 0);
			}
		});

		recompensas.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(v.getContext(), RecompensasActivity.class);
				myIntent.putExtra("MuseoID", museo);
				startActivityForResult(myIntent, 0);
			}
		});

	}
		

	/**
	 * Pregunta si quiere descargar
	 * 
	 * @throws IOException
	 */
	private void questionDownload() throws IOException {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		museoDao = new MuseoDao(this);
		builder.setMessage(
				"Bienvenido a " + museoDao.findById(Integer.parseInt(museo))+ ". ¿Deseas descargar el contenido?").setCancelable(false)
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (current.exists()) {
							deleteFiles(current);
							current.delete();
						}
						startDownload();
						dialog.cancel();
						dialog.dismiss();

					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						dialog.cancel();
						MenuMuseoActivity.this.finish();						
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void confirmNoDownload() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("No se necesita actualizar el contenido")
				.setCancelable(false)
				.setNeutralButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								dialog.dismiss();								
							}
						});
	
		AlertDialog alert = builder.create();
		alert.show();			
	}
	
	

	/**
	 * 
	 * Verifica el tamaÃ±o de una carpeta
	 * 
	 * @return Regresa el tamaÃ±o en bytes
	 */
	private long dirSize(File dir) {
		long result = 0;

		Stack<File> dirlist = new Stack<File>();
		dirlist.clear();

		dirlist.push(dir);

		while (!dirlist.isEmpty()) {
			File dirCurrent = dirlist.pop();

			File[] fileList = dirCurrent.listFiles();
			for (int i = 0; i < fileList.length; i++) {

				if (fileList[i].isDirectory())
					dirlist.push(fileList[i]);
				else
					result += fileList[i].length();
			}
		}
		return result;
	}

	private void deleteFiles(File dir) {
		Stack<File> dirlist = new Stack<File>();
		dirlist.clear();
		dirlist.push(dir);

		while (!dirlist.isEmpty()) {
			File dirCurrent = dirlist.pop();
			File[] fileList = dirCurrent.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory())
					deleteFiles(fileList[i]);
				else
					fileList[i].delete();
			}
		}
	}

	private void startDownload() {
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
			mProgressDialog.cancel();
			mProgressDialog.dismiss();
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
			mProgressDialog.cancel();
			mProgressDialog.dismiss();
			String zipFile = Environment.getExternalStorageDirectory()
					+ "/Ars/museo.zip";
			File file = new File(zipFile);
			file.delete();
			editor = settings.edit();
			editor.putString("museoDownloaded", museo);
			editor.commit();
		}
	}
}