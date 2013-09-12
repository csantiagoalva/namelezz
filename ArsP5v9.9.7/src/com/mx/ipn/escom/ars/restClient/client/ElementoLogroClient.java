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
import com.mx.ipn.escom.ars.restClient.dao.ElementoLogroDao;
import com.mx.ipn.escom.ars.restClient.modelo.ElementoLogro;
import com.mx.ipn.escom.ars.util.Server;

import android.content.Context;
import android.util.Log;

public class ElementoLogroClient implements Server{
	private Context context;
	private ElementoLogroDao elementoLogroDao;
	
	public ElementoLogroClient() {
	}

	public ElementoLogroClient(Context context) {
		this.context = context;
	}
	
	public List<ElementoLogro> getElementosLogros(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"elementosLogros");
		getData.setHeader("content-type", "application/json");
		List<ElementoLogro> elementosLogros = new ArrayList<ElementoLogro>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("elementoLogro");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idElemento = obj.getInt("idElemento");
				Integer idLogro = obj.getInt("idLogro");
				ElementoLogro elemento = new ElementoLogro(idLogro, idElemento);
				elementosLogros.add(elemento);
			}
			elementoLogroDao = new ElementoLogroDao(context);
			elementoLogroDao.open();
			for(ElementoLogro elementoLogro: elementosLogros)
				if(elementoLogroDao.insert(elementoLogro)<0)
					Log.v("Error", "No se pudo insertar el elementologro");
			elementoLogroDao.close();
		}catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
		}
		return elementosLogros;
	}
	public void dropElementoLogro(){
		elementoLogroDao = new ElementoLogroDao(context);
		elementoLogroDao.open();
		elementoLogroDao.dropElementoLogro();
		elementoLogroDao.close();
	}
}
