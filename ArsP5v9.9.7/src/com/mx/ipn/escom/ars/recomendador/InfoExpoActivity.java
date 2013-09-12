package com.mx.ipn.escom.ars.recomendador;

import com.ArsMovil.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoExpoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoexpo);
		
		Bundle bundle = this.getIntent().getExtras();
		
		TextView nameExposicion = (TextView) findViewById(R.id.lbNameValue);
		nameExposicion.setText(bundle.getString("nb_exposicion"));
		TextView descExposicion = (TextView) findViewById(R.id.lbDescValue);
		descExposicion.setText(bundle.getString("desc_exposicion"));
		TextView fechaInicioExposicion = (TextView) findViewById(R.id.lbFechaInicioValue);
		fechaInicioExposicion.setText("" + bundle.getString("fh_inicio"));
		TextView fechaFinExposicion = (TextView) findViewById(R.id.lbFechaFinValue);
		fechaFinExposicion.setText("" + bundle.getString("fh_fin"));
		TextView museoExposicion = (TextView) findViewById(R.id.lbMuseoValue);
		museoExposicion.setText("" + bundle.getString("nb_museo"));
	}

}
