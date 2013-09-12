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
import com.mx.ipn.escom.ars.restClient.dao.MuseoTemaDao;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoTema;
import com.mx.ipn.escom.ars.util.Server;

public class MuseoTemaClient implements Server{
	private MuseoTemaDao museoTemaDao;
	private Context context;
	public MuseoTemaClient() {
	}
	public MuseoTemaClient(Context context) {
		this.context = context;
	}
	public List<MuseoTema> getMuseosTemas(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"museosTemas");
		getData.setHeader("content-type", "application/json");
		List<MuseoTema> museosTemas = new ArrayList<MuseoTema>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("museoTema");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idTema = obj.getInt("idTema");
				Integer idMuseo = obj.getInt("idMuseo");
				MuseoTema museoTema = new MuseoTema(idMuseo, idTema);
				museosTemas.add(museoTema);
			}
			museoTemaDao=new MuseoTemaDao(context);
			museoTemaDao.open();
			for(MuseoTema museoTema : museosTemas){
				if(museoTemaDao.insert(museoTema)<0)
					Log.v("Error al insertar", "No se pudo insertar el museoTema");
			}
			museoTemaDao.close();
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("Servicio Rest", "Error!", e);
		}
		return museosTemas;
	}
	public void dropMuseoTema(){
		museoTemaDao = new MuseoTemaDao(context);
		museoTemaDao.open();
		museoTemaDao.dropMuseoTema();
		museoTemaDao.close();
	}
}
