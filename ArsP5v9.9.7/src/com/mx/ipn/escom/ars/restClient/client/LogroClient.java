package com.mx.ipn.escom.ars.restClient.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.dao.LogroDao;
import com.mx.ipn.escom.ars.restClient.dao.LogroObtenidoDao;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.util.Server;

public class LogroClient implements Server{
	private Context context;
	private LogroDao logroDao;
	private LogroObtenidoDao logroObtenidoDao;

	public LogroClient() {
	}

	public LogroClient(Context context) {
		this.context = context;
	}

	public List<Logro> getLogros() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url + "logros");
		getData.setHeader("content-type", "application/json");
		List<Logro> logros = new ArrayList<Logro>();
		try {
			HttpResponse resp = httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("logro");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);
				Integer idLogro = obj.getInt("idLogro");
				String nbLogro = obj.getString("nombre");
				String dsLogro = obj.getString("descripcion");
				Logro logro = new Logro(idLogro, nbLogro, dsLogro);
				logros.add(logro);
			}
			logroDao = new LogroDao(context);
			logroDao.open();
			logroObtenidoDao = new LogroObtenidoDao(context);
			logroObtenidoDao.open();
			for (Logro logro : logros) {
				if (logroDao.insert(logro) < 0)
					Log.v("Error", "No se pudo insertar el logro");
				
				if (logroObtenidoDao.insert(logro) < 0)
					Log.v("Error", "No se pudo insertar el logro");
				
			}			
			logroObtenidoDao.findAll();			
			logroDao.close();
			logroObtenidoDao.close();
		} catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
		}
		return logros;
	}
	public void dropLogro(){
		logroDao = new LogroDao(context);
		logroDao.open();
		logroDao.dropLogro();
		logroDao.close();
	}
}
