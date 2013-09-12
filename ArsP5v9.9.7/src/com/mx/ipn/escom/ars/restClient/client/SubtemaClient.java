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
import com.mx.ipn.escom.ars.restClient.dao.SubtemaDao;
import com.mx.ipn.escom.ars.restClient.modelo.Subtema;
import com.mx.ipn.escom.ars.util.Server;

public class SubtemaClient implements Server{
	private Context context;
	private SubtemaDao subtemaDao;
	
	public SubtemaClient() {
	}

	public SubtemaClient(Context context) {
		this.context = context;
	}

	public List<Subtema> getSubtemas(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"subtemas");			
		getData.setHeader("content-type", "application/json");
		List<Subtema> subtemas = new ArrayList<Subtema>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("subtema");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer id = obj.getInt("idSubtema");
				String nbName = obj.getString("nombre");
				String dsName = obj.getString("descripcion");
				Subtema subtema = new Subtema(id, nbName, dsName);
				subtemas.add(subtema);
			}
			subtemaDao = new SubtemaDao(context);
			subtemaDao.open();

			for(Subtema subtema : subtemas)
				if(subtemaDao.insert(subtema)<0)
					Log.v("Error al insertar", "No se pudo insertar el subtema");

			Log.v("Subtema", "Apunto de cerrar bd");
			subtemaDao.close();
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("Servicio Rest", "Error!", e);
		}
		return subtemas;
	}
	public void dropSubtema(){
		subtemaDao = new SubtemaDao(context);
		subtemaDao.open();
		subtemaDao.dropSubtema();
		subtemaDao.close();
	}
}
