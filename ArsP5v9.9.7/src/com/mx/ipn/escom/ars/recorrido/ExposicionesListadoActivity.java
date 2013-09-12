package com.mx.ipn.escom.ars.recorrido;


import java.util.ArrayList;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionChecadaDao;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionDao;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionVista;
import com.mx.ipn.escom.ars.util.Dynamics;
import com.mx.ipn.escom.ars.util.MyListView;
 


public class ExposicionesListadoActivity extends Activity {
//	BaseARSHelper baseARS;
	ExposicionDao exposicionDao;
	int requestCode;
	/** Id for the toggle rotation menu item */
	private static final int TOGGLE_ROTATION_MENU_ITEM = 0;

	/** Id for the toggle lighting menu item */
	private static final int TOGGLE_LIGHTING_MENU_ITEM = 1;

	/** The list view */
	private MyListView mListView;
	
	ExposicionChecadaDao exposicionChecadaDao;


	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exposiciones);
		//TODO: leer el id del museo
		Bundle datos = this.getIntent().getExtras();		
		final String museoId=datos.getString("MuseoID");
		
//		baseARS=new BaseARSHelper(this);
//		final ArrayList<ExposicionVista> expos = baseARS.getExposicionVista(museoId);
		exposicionDao=new ExposicionDao(this);
		final ArrayList<ExposicionVista> expos = exposicionDao.getExposicionVista(museoId);
		final MyAdapter adapter = new MyAdapter(this, expos);

		mListView = (MyListView)findViewById(R.id.my_list);
		mListView.setAdapter(adapter);

		mListView.setDynamics(new SimpleDynamics(0.9f, 0.6f));

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(final AdapterView<?> parent, final View view,
					final int position, final long id) {		
				Intent myIntent=new Intent(view.getContext(),ArsActivity.class);	
				myIntent.putExtra("ExpoID", expos.get(position).expoId);
				startActivityForResult(myIntent,requestCode);
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
	
	@Override
	protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
		Log.d("CheckStartActivity","onActivityResult and resultCode = "+resultCode);
		super.onActivityResult(requestCode, resultCode, data);
	
		exposicionChecadaDao=new ExposicionChecadaDao(this);
		if(resultCode!=0){
			Log.v("Exposiciones", ""+resultCode);
			if(exposicionChecadaDao.calificacion(resultCode)==0){
				Log.v("Exposiciones", "la expo no ha sido calificada");
			final Dialog rankDialog;
			final RatingBar ratingBar;
			rankDialog = new Dialog(ExposicionesListadoActivity.this);
			rankDialog.setContentView(R.layout.rating);
			rankDialog.setCancelable(true);
			ratingBar = (RatingBar)rankDialog.findViewById(R.id.ratingBar);			
			ratingBar.setRating(3);
    
			TextView text = (TextView) rankDialog.findViewById(R.id.lblRateMe);
			Button updateButton = (Button) rankDialog.findViewById(R.id.btnSubmit);
			updateButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	exposicionChecadaDao.calificar(Integer.valueOf((int) ratingBar.getRating())*2,resultCode);        	
            rankDialog.dismiss();                        
        }
    });
    rankDialog.show();
			}
		}	
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
		 * @param expos The list of contacts
		 */
		public MyAdapter(final Context context, final ArrayList<ExposicionVista> expos) {
			super(context, 0, expos);
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

		@Override
		public long getItemId(int position) {	
			return Long.valueOf(getItem(position).expoId);
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
