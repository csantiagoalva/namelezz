package com.mx.ipn.escom.ars.restClient.client;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.mx.ipn.escom.ars.restClient.dao.VersionDao;
import com.mx.ipn.escom.ars.restClient.modelo.Version;
import com.mx.ipn.escom.ars.util.Server;

public class VersionClient implements Server{
	Context context;
	VersionDao versionDao;
	HashMap<String, Integer> mapaVersionLocal;
	HashMap<String, Integer> mapaVersion;
	public VersionClient() {
	}

	public VersionClient(Context context) {
		this.context = context;
	}
	public void getVersionesLocal(){
		versionDao = new VersionDao(context);
		versionDao.open();
		mapaVersionLocal = versionDao.getVersionesLocal();
		System.out.println("tamanio mapa local " + mapaVersionLocal.size());
		versionDao.close();
	}
	public List<Version> getVersiones(){
		//this.getMapaVersionLocal();
		versionDao = new VersionDao(context);
		versionDao.open();
		mapaVersionLocal = versionDao.getVersionesLocal();
		System.out.println("tamanio mapa local " + mapaVersionLocal.size());
		versionDao.close();
		mapaVersion = new HashMap<String, Integer>();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"versiones");
		getData.setHeader("content-type", "application/json");
		List<Version> versiones = new ArrayList<Version>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("version");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer id = obj.getInt("id");
				String tabla = obj.getString("tabla");
				Integer version = obj.getInt("version");
				Version tmp = new Version(id, tabla, version);
				versiones.add(tmp);
				mapaVersion.put(tabla, version);
			}
			
			versionDao = new VersionDao(context);
			versionDao.open();
			versionDao.dropVersion();
			for(Version version: versiones)
				if(versionDao.insert(version)<0)
					Log.v("Error", "No se pudo insertar el version");
			versionDao.close();
		}catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
			
		}
		return versiones;
	}

	public HashMap<String, Integer> getMapaVersionLocal() {
		return mapaVersionLocal;
	}

	public void setMapaVersionLocal(HashMap<String, Integer> mapaVersionLocal) {
		this.mapaVersionLocal = mapaVersionLocal;
	}

	public HashMap<String, Integer> getMapaVersion() {
		return mapaVersion;
	}

	public void setMapaVersion(HashMap<String, Integer> mapaVersion) {
		this.mapaVersion = mapaVersion;
	}
	public void dropVersion(){
		versionDao = new VersionDao(context);
		versionDao.open();
		versionDao.dropVersion();
		versionDao.close();
	}
}
