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
import com.mx.ipn.escom.ars.restClient.dao.RecompensaDao;
import com.mx.ipn.escom.ars.restClient.dao.RecompensaObtenidaDao;
import com.mx.ipn.escom.ars.restClient.modelo.Recompensa;
import com.mx.ipn.escom.ars.util.Server;

public class RecompensaClient implements Server{
	Context context;
	RecompensaDao recompensaDao;
	RecompensaObtenidaDao recompensaObtenidaDao;
	public RecompensaClient() {
	}

	public RecompensaClient(Context context) {
		this.context = context;
	}
	
	public List<Recompensa> getRecompensas(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"recompensas");
		getData.setHeader("content-type", "application/json");
		List<Recompensa> recompensas = new ArrayList<Recompensa>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("recompensa");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idRecompensa = obj.getInt("idRecompensa");
				Integer idLogro = obj.getInt("idLogro");
				String nbRecompensa = obj.getString("nombre");
				String urlRecompensa = obj.getString("url");
				String urlTextura = obj.getString("urlText");
				Recompensa recompensa = new Recompensa(idRecompensa,idLogro, nbRecompensa, urlRecompensa, urlTextura);
				recompensas.add(recompensa);
			}
			recompensaDao = new RecompensaDao(context);
			recompensaDao.open();
			recompensaObtenidaDao=new RecompensaObtenidaDao(context);
			recompensaObtenidaDao.open();
						
			for(Recompensa recompensa: recompensas){
				if(recompensaDao.insert(recompensa)<0)
					Log.v("Error", "No se pudo insertar el recompensa");
				
				if (recompensaObtenidaDao.insert(recompensa) < 0)
					Log.v("Error", "No se pudo insertar la recompensa");
			}
			recompensaObtenidaDao.close();
			recompensaDao.close();

		}catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
		}
		return recompensas;
	}
	public void dropRecompensa(){
		recompensaDao = new RecompensaDao(context);
		recompensaDao.open();
		recompensaDao.dropRecompensa();
		recompensaDao.close();
	}
}
