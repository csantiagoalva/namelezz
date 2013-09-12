package com.mx.ipn.escom.ars.restClient.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.dao.TemaDao;
import com.mx.ipn.escom.ars.restClient.modelo.Tema;
import com.mx.ipn.escom.ars.util.Server;

public class TemaClient implements Server{
	private Context context;
	private TemaDao temaDao;

	public TemaClient() {
	}

	public TemaClient(Context context) {
		this.context = context;
	}

	public List<Tema> getTemas() {
		System.out.println("entrando a Tema");
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"temas");
		getData.setHeader("content-type", "application/json");
		List<Tema> temas = new ArrayList<Tema>();
		try {
			HttpResponse resp = httpClient.execute(getData);			
			StatusLine statusLine = resp.getStatusLine();			
			String respStr = EntityUtils.toString(resp.getEntity());			
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));			
			JSONArray data = jsonResponse.getJSONArray("tema");
			for (int i = 0; i < data.length(); i++) {
				JSONObject obj = data.getJSONObject(i);
				Integer id = obj.getInt("idTema");
				String nbName = obj.getString("nombre");
				String dsName = obj.getString("descripcion");
				Tema tema = new Tema(id, nbName, dsName);
				temas.add(tema);
			}

			temaDao = new TemaDao(context);
			temaDao.open();
			for (Tema tema : temas)
				if (temaDao.insert(tema) < 0)
					Log.v("Error al insertar", "No se pudo insertar el tema");
			temaDao.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Servicio Rest", "Error!", e);
		}
		for (Tema tema : temas)
			System.out.println(tema.getIdTema() + " " + tema.getNbTema() + " "
					+ tema.getDsTema());
		return temas;
	}
	public void dropTema(){
		temaDao = new TemaDao(context);
		temaDao.open();
		temaDao.dropTema();
		temaDao.close();
	}
}
