package com.mx.ipn.escom.ars.restClient.client;
import com.ArsMovil.R;

import com.mx.ipn.escom.ars.logros.LogrosListadoActivity;
import com.mx.ipn.escom.ars.logros.RecompensasActivity;
import com.mx.ipn.escom.ars.recomendador.RecomendadorTabsActivity;
import com.mx.ipn.escom.ars.recorrido.MenuMuseoActivity;
import com.mx.ipn.escom.ars.recorrido.ReconocerActivity;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.ElementoVistoDao;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionChecadaDao;
import com.mx.ipn.escom.ars.restClient.dao.MuseoDao;
import com.mx.ipn.escom.ars.restClient.modelo.LogroVista;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuPrincipalActivity extends Activity {
	Button recomendacion;
	Button logros;
	Button recorrido;
	Button personaje;
	Button elementos;
	Button continuar;
	private SQLiteDatabase database; 
	private BaseARSHelper dbHelper;
	
	ElementoVistoDao elementoVistoDao;
	public static final String PREFS_NAME = "Configuracion";
	SharedPreferences settings;
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
        settings = getSharedPreferences(PREFS_NAME, 0);
        
        MuseoDao museoDao=new MuseoDao(this);
        museoDao.findAll();
        
        dbHelper = new BaseARSHelper(this);
        database = dbHelper.getReadableDatabase();
                
        
        Cursor c;
		c=database.rawQuery("select name from sqlite_master where type = 'table';", null);
		
		if (c.moveToFirst()) {
			do {    					
				Log.v("consulta", c.getString(0));    	          
			} while(c.moveToNext());
		}
		c.close();
		database.close();				
		
        recomendacion=(Button)findViewById(R.id.recomendacion);
        logros=(Button)findViewById(R.id.logros);
        recorrido=(Button)findViewById(R.id.recorrido);
        personaje=(Button)findViewById(R.id.personaje);
        continuar=(Button)findViewById(R.id.continuar);
        
        recomendacion.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				Intent myIntent=new Intent(v.getContext(),RecomendadorTabsActivity.class);							
				startActivityForResult(myIntent, 0);				
			}
		});
        
        logros.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				Intent myIntent=new Intent(v.getContext(),LogrosListadoActivity.class);							
				startActivityForResult(myIntent, 0);				
			}
		});
        
        recorrido.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				Intent myIntent=new Intent(v.getContext(),ReconocerActivity.class);							
				startActivity(myIntent);				
			}
		});
        
        personaje.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				Intent myIntent=new Intent(v.getContext(),RecompensasActivity.class);							
				startActivityForResult(myIntent, 0);				
			}
		});
        
        continuar.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
															
				if(!settings.getString("museoDownloaded", "0").equals("0")){
					Intent intent = new Intent(v.getContext(),MenuMuseoActivity.class);
					intent.putExtra("MuseoID", settings.getString("museoDownloaded", "0"));
					intent.putExtra("Action", "NoDescarga");										
					startActivity(intent);
				}else{
					Toast.makeText(MenuPrincipalActivity.this, "No has iniciado ningún recorrido", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
    }
}
