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
import com.mx.ipn.escom.ars.restClient.dao.ExposicionSubtemaDao;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionSubtema;
import com.mx.ipn.escom.ars.util.Server;

public class ExposicionSubtemaClient implements Server{
	private ExposicionSubtemaDao exposicionSubtemaDao;
	private Context context;
	
	public ExposicionSubtemaClient() {
	}
	
	public ExposicionSubtemaClient(Context context) {
		this.context = context;
	}
	
	public List<ExposicionSubtema> getExposicionesSubtemas(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"exposicionesSubtemas");
		getData.setHeader("content-type", "application/json");
		List<ExposicionSubtema> exposicionesSubtemas = new ArrayList<ExposicionSubtema>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("expoSubtema");
			for(int i=0;i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idExposicion = obj.getInt("idExposicion");
				Integer idSubtema = obj.getInt("idsubtema");
				ExposicionSubtema exposicionSubtema = new ExposicionSubtema(idExposicion, idSubtema);
				exposicionesSubtemas.add(exposicionSubtema);
			}
			exposicionSubtemaDao = new ExposicionSubtemaDao(context);
			exposicionSubtemaDao.open();
			for(ExposicionSubtema exposicionSubtema : exposicionesSubtemas)
				if(exposicionSubtemaDao.insert(exposicionSubtema)<0)
					Log.v("Error al insertar", "No se pudo insertar la exposicionSubtema");
			exposicionSubtemaDao.close();
		}catch(Exception e){
			Log.e("Servicio Rest", "Error!", e);
		}
		return exposicionesSubtemas;
	}
	public void dropExposicionSubtema(){
		exposicionSubtemaDao = new ExposicionSubtemaDao(context);
		exposicionSubtemaDao.open();
		exposicionSubtemaDao.dropExposicionSubtema();
		exposicionSubtemaDao.close();
	}
}
