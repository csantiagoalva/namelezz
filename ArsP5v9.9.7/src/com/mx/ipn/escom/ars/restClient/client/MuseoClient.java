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
import com.mx.ipn.escom.ars.restClient.dao.MuseoDao;
import com.mx.ipn.escom.ars.restClient.modelo.Museo;
import com.mx.ipn.escom.ars.util.Server;

public class MuseoClient implements Server{
	private Context context;
	private MuseoDao museoDao;
	
	public MuseoClient() {
	}

	public MuseoClient(Context context) {
		this.context = context;
	}

	public List<Museo> getMuseos(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"museos");
		getData.setHeader("content-type", "application/json");
		List<Museo> museos = new ArrayList<Museo>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("museo");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idMuseo = obj.getInt("idMuseo");
				String nbMuseo = obj.getString("nombre");
				String descMuseo = obj.getString("descripcion");
				String dirCalle = obj.getString("calle");
				String dirNumero = obj.getString("numero");
				String dirColonia = obj.getString("colonia");
				String dirDelegacion = obj.getString("delegacion");
				String dirEstado = obj.getString("estado");
				Integer dirCP = obj.getInt("codigo");
				String contacto = obj.getString("contacto");
				String edoMuseo = obj.getString("edoMuseo");
				Museo museo = new Museo(idMuseo, nbMuseo, descMuseo, dirCalle, dirNumero, dirColonia, dirDelegacion, dirEstado, dirCP, contacto, edoMuseo);				
				
				museos.add(museo);
			}
			museoDao = new MuseoDao(context);
			museoDao.open();
			for(Museo museo : museos){
				if(museoDao.insert(museo)<0)
					Log.v("Error al insertar", "No se pudo insertar el museo");
			}
			museoDao.close();
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("Servicio Rest", "Error!", e);
		}
		return museos;
	}
	public void dropMuseo(){
		museoDao = new MuseoDao(context);
		museoDao.open();
		museoDao.dropMuseo();
		museoDao.close();
	}
}
