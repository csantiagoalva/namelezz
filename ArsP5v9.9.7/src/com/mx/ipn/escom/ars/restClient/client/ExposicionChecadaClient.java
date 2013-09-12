package com.mx.ipn.escom.ars.restClient.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.mx.ipn.escom.ars.restClient.dao.ElementoDao;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionChecadaDao;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionChecada;
import com.mx.ipn.escom.ars.util.Server;

public class ExposicionChecadaClient implements Server{
	ExposicionChecadaDao exposicionChecadaDao;
	Context context;

	public ExposicionChecadaClient() {
	}

	public ExposicionChecadaClient(Context context) {
		this.context = context;
	}

	public void send() throws JSONException, ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url+"exposicionesPost");
		post.setHeader("content-type", "application/json");		
			JSONArray dato = new JSONArray();
			exposicionChecadaDao = new ExposicionChecadaDao(context);			
			ArrayList<ExposicionChecada> expos = exposicionChecadaDao.getExposiciones();			
						
			for (ExposicionChecada ec : expos) {				
				JSONObject exp = new JSONObject();
					exp.put("idExpo", ec.getIdExposicion());				
					exp.put("rank", ec.getRkExposicion());
					exp.put("env", ec.getEnviada());										
					dato.put(exp);	
					
					exposicionChecadaDao.setEnviada(ec.getIdExposicion());
				}			
			
			Log.v("datos", dato.toString());			
			StringEntity entity = new StringEntity(dato.toString());			
			post.setEntity(entity);
			HttpResponse resp = httpClient.execute(post);
			String respStr = EntityUtils.toString(resp.getEntity());			
			Log.v("envio", respStr);
	}

}
