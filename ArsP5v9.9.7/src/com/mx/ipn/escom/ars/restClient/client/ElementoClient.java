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

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.dao.ElementoDao;
import com.mx.ipn.escom.ars.restClient.dao.ElementoVistoDao;
import com.mx.ipn.escom.ars.restClient.modelo.Elemento;
import com.mx.ipn.escom.ars.util.Server;

import android.content.Context;
import android.util.Log;

public class ElementoClient implements Server{
	ElementoDao elementoDao;
	ElementoVistoDao elementoVistoDao;
	Context context;
	public ElementoClient() {
	}

	public ElementoClient(Context context) {
		this.context = context;
	}

	public List<Elemento> getElementos(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"elementos");			
		getData.setHeader("content-type", "application/json");
		List<Elemento> elementos = new ArrayList<Elemento>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));		
			JSONArray data = jsonResponse.getJSONArray("elemento");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idElemento = obj.getInt("idElemento");
				String nbElemento = obj.getString("nombre");
				Integer idExposicion = obj.getInt("idExposicion");
				String dsElemento = obj.getString("descripcion");
				Elemento elemento = new Elemento(idElemento, nbElemento, idExposicion, dsElemento);
				elementos.add(elemento);
			}
			elementoDao = new ElementoDao(context);
			elementoDao.open();
			elementoVistoDao= new ElementoVistoDao(context);
			elementoVistoDao.open();
			
			for(Elemento elemento: elementos){
				if(elementoDao.insert(elemento)<0)
					Log.v("Error", "No se pudo insertar el elemento");
				
				if(elementoVistoDao.insert(elemento)<0)
					Log.v("Error", "No se pudo insertar el elemento");				
			}
			elementoVistoDao.close();
			elementoDao.close();			
		}catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
		}
		return elementos;
	}
	public void dropElemento(){
		elementoDao = new ElementoDao(context);
		elementoDao.open();
		elementoDao.dropElemento();
		elementoDao.close();
	}
}
