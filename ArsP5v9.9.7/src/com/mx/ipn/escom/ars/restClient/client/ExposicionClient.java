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

import com.mx.ipn.escom.ars.restClient.dao.ExposicionChecadaDao;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionDao;
import com.mx.ipn.escom.ars.restClient.dao.LogroDao;
import com.mx.ipn.escom.ars.restClient.dao.LogroObtenidoDao;
import com.mx.ipn.escom.ars.restClient.modelo.Exposicion;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionChecada;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.util.Server;

public class ExposicionClient implements Server {
	private ExposicionDao exposicionDao;
	private ExposicionChecadaDao exposicionChecadaDao;
	private Context context;
	
	public ExposicionClient() {
	}

	public ExposicionClient(Context context) {
		this.context = context;
	}

	public List<Exposicion> getExposiciones(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getData = new HttpGet(url+"exposiciones");
		getData.setHeader("content-type", "application/json");
		List<Exposicion> exposiciones = new ArrayList<Exposicion>();
		List<ExposicionChecada> exposicionesChecadas = new ArrayList<ExposicionChecada>();
		try{
			HttpResponse resp= httpClient.execute(getData);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONObject jsonResponse = new JSONObject(new String(respStr.getBytes("ISO-8859-1"), "UTF-8"));
			JSONArray data = jsonResponse.getJSONArray("exposicion");
			for(int i=0; i<data.length();i++){
				JSONObject obj = data.getJSONObject(i);
				Integer idExposicion = obj.getInt("idExposicion");
				Integer idMuseo = obj.getInt("idMuseo");
				String nbExposicion = obj.getString("nombre");
				String descExposicion = obj.getString("descripcion");								
				String edoExposicion = obj.getString("edoExposicion");
				
				String rpExposicion = "";
				try{
					rpExposicion = obj.getString("tipo");
				}catch(Exception e){
					rpExposicion = "";
				}
				Double rkExposcion = 0.0;				
				try{
					rkExposcion = obj.getDouble("ranking");
				}catch (Exception e) {
					rkExposcion = 0.0;
				}
				Integer noCalificaciones = 0;				
				try{
					noCalificaciones = obj.getInt("numCalificaciones");
				}catch (Exception e) {
					noCalificaciones = 0;
				}
				String fhInicio = "";				
				try{
					fhInicio = obj.getString("fhInicio");
				}catch (Exception e) {
					fhInicio = "Sin fecha";
				}
				String fhFin = "";
				try{
					fhFin = obj.getString("fhFin");
				}catch (Exception e) {
					fhFin = "Sin fecha";
				}
				Exposicion exposicion = new Exposicion(idExposicion, idMuseo, nbExposicion, descExposicion, rkExposcion, rpExposicion, edoExposicion, fhInicio, fhFin, noCalificaciones);
				exposiciones.add(exposicion);
			}

			exposicionDao = new ExposicionDao(context);
			exposicionDao.open();
			exposicionChecadaDao = new ExposicionChecadaDao(context);
			exposicionChecadaDao.open();
			for (Exposicion exposicion : exposiciones) {
				if (exposicionDao.insert(exposicion) < 0)
					Log.v("Error", "No se pudo insertar el logro");
				
				if (exposicionChecadaDao.insert(exposicion) < 0)
					Log.v("Error", "No se pudo insertar el logro");
				
			}						
			exposicionDao.close();
			exposicionChecadaDao.close();
		}catch (Exception e) {
			Log.e("Servicio Rest", "Error!", e);
		}
		return exposiciones;
	}
	public void dropExposicion(){
		exposicionDao = new ExposicionDao(context);
		exposicionDao.open();
		exposicionDao.dropExposicion();
		exposicionDao.close();
	}
}
