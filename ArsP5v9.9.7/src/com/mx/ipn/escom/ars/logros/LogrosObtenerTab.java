package com.mx.ipn.escom.ars.logros;

import java.util.ArrayList;

import com.mx.ipn.escom.ars.logros.LogrosObtenidosTab.SimpleDynamics;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.LogroObtenidoDao;
import com.mx.ipn.escom.ars.restClient.modelo.LogroVista;
import com.mx.ipn.escom.ars.util.Dynamics;
import com.mx.ipn.escom.ars.util.MyListView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.ArsMovil.R;

public class LogrosObtenerTab extends Activity {
	LogroObtenidoDao LODao;
	String museoId;
	/** Id for the toggle rotation menu item */
	private static final int TOGGLE_ROTATION_MENU_ITEM = 0;

	/** Id for the toggle lighting menu item */
	private static final int TOGGLE_LIGHTING_MENU_ITEM = 1;

	/** The list view */
	private MyListView mListView;

	/**
	 * Small class that represents a contact
	 */
	

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logros_obtenidos);
		LODao=new LogroObtenidoDao(this);
		Bundle datos = this.getIntent().getExtras();
		museoId=datos.getString("MuseoID");

		final ArrayList<LogroVista> logros = LODao.getLogrosVistaPorOb(museoId);
		final MyAdapter adapter = new MyAdapter(this, logros);

		mListView = (MyListView)findViewById(R.id.my_list);
		mListView.setAdapter(adapter);

		mListView.setDynamics(new SimpleDynamics(0.9f, 0.6f));


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
	private static class MyAdapter extends ArrayAdapter<LogroVista> {

		/** Re-usable contact image drawable */
		private final Drawable contactImage;

		/**
		 * Constructor
		 * 
		 * @param context The context
		 * @param logros The list of contacts
		 */
		public MyAdapter(final Context context, final ArrayList<LogroVista> logros) {
			super(context, 0, logros);
			if(logros.size()>0){
				contactImage = context.getResources().getDrawable(R.drawable.logro2);
			}
			else{
				contactImage = context.getResources().getDrawable(R.drawable.logro);
				logros.add(new LogroVista("Sin logros por obtener", "-1", "No hay logros por obtener en este museo."));
			}
		}

		@Override
		public View getView(final int position, final View convertView, final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
			}

			final TextView name = (TextView)view.findViewById(R.id.contact_name);

			name.setText(getItem(position).logroNombre);

			final TextView number = (TextView)view.findViewById(R.id.contact_number);
			number.setText(getItem(position).logroDesc);
			number.setTextSize(12);


			final ImageView photo = (ImageView)view.findViewById(R.id.contact_photo);
			photo.setImageDrawable(contactImage);

			return view;
		}

		@Override
		public long getItemId(int position) {		
			return Long.valueOf(getItem(position).logroId);

		}	

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





