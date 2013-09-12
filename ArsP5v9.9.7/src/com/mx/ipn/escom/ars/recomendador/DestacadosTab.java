package com.mx.ipn.escom.ars.recomendador;

import java.util.ArrayList;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.recomendador.RecomendacionTab.SimpleDynamics;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionDao;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionVista;
import com.mx.ipn.escom.ars.util.Dynamics;
import com.mx.ipn.escom.ars.util.MyListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class DestacadosTab extends Activity {
	
	private ExposicionDao exposicionDao;
	private Cursor cursorExposicionDestacados;
	/** The list view */
	private MyListView mListView;
	private ArrayList<ExposicionVista> tmp;
	/** Id for the toggle rotation menu item */
	private static final int TOGGLE_ROTATION_MENU_ITEM = 0;

	/** Id for the toggle lighting menu item */
	private static final int TOGGLE_LIGHTING_MENU_ITEM = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uidestacados);
				
		exposicionDao = new ExposicionDao(this);
		exposicionDao.open();
		// Aqui esta la lista, ya la implementas tu
		final ArrayList<ExposicionVista> tmp = exposicionDao.getExposicionesDestacadasVista();
		System.out.println("tamanio tmp " + tmp.size());

		cursorExposicionDestacados = exposicionDao.getExposicionesDestacadas();
		Log.v("Destacados Recomendacion", "count " + cursorExposicionDestacados.getCount());
		
		
		//se puede comentar desde aqui
		String[] recomendacionDestacados = new String[cursorExposicionDestacados.getCount()];
		int i=0;
		if(cursorExposicionDestacados.moveToFirst()){
			while(!cursorExposicionDestacados.isAfterLast()){
				recomendacionDestacados[i] = cursorExposicionDestacados.getString(cursorExposicionDestacados.getColumnIndex("nb_exposicion"));
				i++;
				cursorExposicionDestacados.moveToNext();
			}
		}
		
		// hasta aqui puedes comentar
		
		exposicionDao.close();
		
		final MyAdapter adapter = new MyAdapter(this, tmp);

		mListView = (MyListView)findViewById(R.id.my_list);
		mListView.setAdapter(adapter);

		mListView.setDynamics(new SimpleDynamics(0.9f, 0.6f));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(final AdapterView<?> parent, final View view,
					final int position, final long id) {								
				Intent myIntent=new Intent(view.getContext(),InfoExpoActivity.class);	
				Bundle bundle = new Bundle();
				
				bundle.putString("nb_exposicion", tmp.get(position).expoNombre);
				bundle.putString("desc_exposicion", tmp.get(position).expoDesc);
				bundle.putString("fh_inicio", tmp.get(position).fechaInicio);
				bundle.putString("fh_fin", tmp.get(position).fechaFin);
				bundle.putString("nb_museo", tmp.get(position).museoNombre);				
				myIntent.putExtras(bundle);
				startActivity(myIntent);
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(final AdapterView<?> parent, final View view,
					final int position, final long id) {
				//final String message = "OnLongClick: " + museos.get(position).mName;
				//Toast.makeText(Logros.this, message, Toast.LENGTH_SHORT).show();


				return true;
			}
		});
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(final Menu menu) {
//		menu.add(Menu.NONE, TOGGLE_ROTATION_MENU_ITEM, 0, "Desactivar Rotaci�n");
//		menu.add(Menu.NONE, TOGGLE_LIGHTING_MENU_ITEM, 1, "Desactivar Iluminaci�n");
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(final MenuItem item) {
//		switch (item.getItemId()) {
//		case TOGGLE_ROTATION_MENU_ITEM:
//			mListView.enableRotation(!mListView.isRotationEnabled());
//			if(mListView.isRotationEnabled())	
//				item.setTitle("Desactivar Rotaci�n");
//			else
//				item.setTitle("Activar Rotaci�n");
//			return true;
//
//		case TOGGLE_LIGHTING_MENU_ITEM:
//			mListView.enableLight(!mListView.isLightEnabled());
//			if(mListView.isLightEnabled())	
//				item.setTitle("Desactivar Iluminaci�n");
//			else
//				item.setTitle("Activar Iluminaci�n");
//			return true;
//
//		default:
//			return false;
//		}
//	}


	/**
	 * Adapter class to use for the list
	 */
	private static class MyAdapter extends ArrayAdapter<ExposicionVista> {

		/** Re-usable contact image drawable */
		private final Drawable contactImage;

		/**
		 * Constructor
		 * 
		 * @param context The context
		 * @param contacts The list of contacts
		 */
		public MyAdapter(final Context context, final ArrayList<ExposicionVista> contacts) {
			super(context, 0, contacts);
			contactImage = context.getResources().getDrawable(R.drawable.contact_image);
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
			}

			final TextView name = (TextView)view.findViewById(R.id.contact_name);
			name.setText(getItem(position).expoNombre);

			final TextView number = (TextView)view.findViewById(R.id.contact_number);
			number.setTextSize(1);
			number.setText("");

			final ImageView photo = (ImageView)view.findViewById(R.id.contact_photo);
			photo.setImageDrawable(contactImage);

			return view;
		}

//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return Long.valueOf(getItem(position).expoId);
//		}	
	}

	/**
	 * A very simple dynamics implementation with spring-like behavior
	 */
	class SimpleDynamics extends Dynamics {

		/** The friction factor */
		private float mFrictionFactor;

		/** The snap to factor */
		private float mSnapToFactor;

		/**
		 * Creates a SimpleDynamics object
		 * 
		 * @param frictionFactor The friction factor. Should be between 0 and 1.
		 *            A higher number means a slower dissipating speed.
		 * @param snapToFactor The snap to factor. Should be between 0 and 1. A
		 *            higher number means a stronger snap.
		 */
		public SimpleDynamics(final float frictionFactor, final float snapToFactor) {
			mFrictionFactor = frictionFactor;
			mSnapToFactor = snapToFactor;
		}

		@Override
		protected void onUpdate(final int dt) {
			// update the velocity based on how far we are from the snap point
			mVelocity += getDistanceToLimit() * mSnapToFactor;

			// then update the position based on the current velocity
			mPosition += mVelocity * dt / 1000;

			// and finally, apply some friction to slow it down
			mVelocity *= mFrictionFactor;
		}
	}
}
