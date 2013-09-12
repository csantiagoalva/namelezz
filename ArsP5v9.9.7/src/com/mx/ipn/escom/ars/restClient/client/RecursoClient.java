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
import com.mx.ipn.escom.ars.restClient.dao.RecursoDao;
import com.mx.ipn.escom.ars.restClient.modelo.Recurso;
import com.mx.ipn.escom.ars.util.Server;

public class RecursoClient implements Server{
	private Context context;
	private RecursoDao recursoDao;
	
	public RecursoClient() {
	}

	public RecursoClient(Context context) {
		this.context = context;
	}

	public List<Recurso> getRecursos(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"recursos");
		getData.setHeader("content-type", "application/json");
		List<Recurso> recursos = new ArrayList<Recurso>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("recurso");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idRecurso = obj.getInt("idRecurso");
				Integer idElemento = obj.getInt("idElemento");
				String nbElemento = obj.getString("nbElemento");
				String descElemento = obj.getString("descElemento");
				String nbArchivoFisico = obj.getString("nbArchivoFisico");
				String urlArchivo = obj.getString("urlArchivo");
				Recurso recurso = new Recurso(idRecurso, idElemento, nbElemento, descElemento, nbArchivoFisico, urlArchivo);
				recursos.add(recurso);
			}
			recursoDao = new RecursoDao(context);
			recursoDao.open();
			for(Recurso recurso : recursos){
				if(recursoDao.insert(recurso)<0)
					Log.v("Error al insertar", "No se pudo insertar el recurso");
			}
			recursoDao.close();
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("Servicio Rest", "Error!", e);
		}
		return recursos;
	}
	public void dropRecurso(){
		recursoDao = new RecursoDao(context);
		recursoDao.open();
		recursoDao.dropRecurso();
		recursoDao.close();
	}
}
