package com.mx.ipn.escom.ars.logros;

import com.ArsMovil.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class LogrosMuseoTabsActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
 
        Bundle datos = this.getIntent().getExtras();
        final String museo = datos.getString("Museo");
        final String museoID=datos.getString("MuseoID");
        
        TabHost tabHost = getTabHost();
 
        TabSpec tabObtenidos = tabHost.newTabSpec("Obtenidos");
        // setting Title and Icon for the Tab
        tabObtenidos.setIndicator("Obtenidos", getResources().getDrawable(R.drawable.logro));
        Intent intentObtenidos=new Intent(this,LogrosObtenidosTab.class);				
        intentObtenidos.putExtra("MuseoID", museoID);        
        tabObtenidos.setContent(intentObtenidos);
 
        TabSpec tabPorObtener = tabHost.newTabSpec("Por obtener");
        tabPorObtener.setIndicator("Por obtener", getResources().getDrawable(R.drawable.logro2));
        Intent intentPorObtener=new Intent(this,LogrosObtenerTab.class);
        intentPorObtener.putExtra("MuseoID", museoID);
        tabPorObtener.setContent(intentPorObtener);
         
        tabHost.addTab(tabObtenidos);
        tabHost.addTab(tabPorObtener);  
    }
}