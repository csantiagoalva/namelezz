package com.mx.ipn.escom.ars.recomendador;

import java.util.List;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.logros.LogrosObtenidosTab;
import com.mx.ipn.escom.ars.logros.LogrosObtenerTab;
import com.mx.ipn.escom.ars.restClient.client.ElementoClient;
import com.mx.ipn.escom.ars.restClient.client.ExposicionClient;
import com.mx.ipn.escom.ars.restClient.client.ExposicionSubtemaClient;
import com.mx.ipn.escom.ars.restClient.client.MuseoClient;
import com.mx.ipn.escom.ars.restClient.client.MuseoTemaClient;
import com.mx.ipn.escom.ars.restClient.client.RecursoClient;
import com.mx.ipn.escom.ars.restClient.client.SubtemaClient;
import com.mx.ipn.escom.ars.restClient.client.TemaClient;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.modelo.Elemento;
import com.mx.ipn.escom.ars.restClient.modelo.Exposicion;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionSubtema;
import com.mx.ipn.escom.ars.restClient.modelo.Museo;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoTema;
import com.mx.ipn.escom.ars.restClient.modelo.Recurso;
import com.mx.ipn.escom.ars.restClient.modelo.Subtema;
import com.mx.ipn.escom.ars.restClient.modelo.Tema;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class RecomendadorTabsActivity extends TabActivity {
	Context context = this;

	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tabs);
		        
	        
	        TabHost tabHost = getTabHost();
	 
	        TabSpec tabRecomendacion = tabHost.newTabSpec("Recomendación");
	        // setting Title and Icon for the Tab
	        tabRecomendacion.setIndicator("Recomendación", getResources().getDrawable(R.drawable.recomendacion));
	        Intent intentRecomendacion=new Intent(this,RecomendacionTab.class);				        
	        tabRecomendacion.setContent(intentRecomendacion);
	 
	        TabSpec tabDestacados= tabHost.newTabSpec("Destacados");
	        tabDestacados.setIndicator("Destacados", getResources().getDrawable(R.drawable.destacados));
	        Intent intentDestacados=new Intent(this,DestacadosTab.class);
	        tabDestacados.setContent(intentDestacados);
	        
	        TabSpec tabAleatorio= tabHost.newTabSpec("Aleatorio");
	        tabAleatorio.setIndicator("Aleatorio", getResources().getDrawable(R.drawable.random));
	        Intent intentAleatorio=new Intent(this,RandomTab.class);
	        tabAleatorio.setContent(intentAleatorio);
	         
	        tabHost.addTab(tabRecomendacion);
	        tabHost.addTab(tabDestacados);
	        tabHost.addTab(tabAleatorio); 
	    }
	
}