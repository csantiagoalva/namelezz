package com.mx.ipn.escom.ars.recomendador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mx.ipn.escom.ars.logros.PersonajeActivity;
import com.mx.ipn.escom.ars.restClient.dao.BaseARSHelper;
import com.mx.ipn.escom.ars.restClient.dao.ExposicionDao;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionVista;
import com.mx.ipn.escom.ars.restClient.modelo.RecompensaVista;
import com.mx.ipn.escom.ars.util.Dynamics;
import com.mx.ipn.escom.ars.util.MyListView;

import com.ArsMovil.R;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class RecomendacionTab extends Activity {

	private Generador miGenerador;
	private ExposicionDao exposicionDao;	
	private Cursor cursorSubtemasRanks;
	private Cursor cursorTemasSubtemas;
	private Cursor cursorExposicionesRecomendadas;
	private String[] exposicionesRecomendadas;
	private Cursor cursorExposicionVista;
	private HashMap<String, Integer> subtemaRank;
	private HashMap<String, ArrayList<String>> temaSubtema;
	private ListView miListView;
	private Double umbral;
	/** Id for the toggle rotation menu item */
	private static final int TOGGLE_ROTATION_MENU_ITEM = 0;

	/** Id for the toggle lighting menu item */
	private static final int TOGGLE_LIGHTING_MENU_ITEM = 1;

	/** The list view */
	private MyListView mListView;
	private ArrayList<ExposicionVista> tmp;
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uibuscador);
		//miListView=(ListView) findViewById(R.id.listResult);
		
		exposicionDao = new ExposicionDao(this);
		exposicionDao.open();
		cursorSubtemasRanks=exposicionDao.getSubtemasRanks();
		cursorTemasSubtemas=exposicionDao.getTemasSubtemas();
		cursorExposicionVista=exposicionDao.getExposicionChecada();
		if(cursorExposicionVista.moveToFirst()){
			umbral = (double) cursorExposicionVista.getInt(0);
		}
		Log.v("umbral", "valor " + umbral);
		
		createSubtemaRank();
		createTemaSubtema();
		
		miGenerador=new Generador(subtemaRank, temaSubtema, umbral);
		miGenerador.obtenerRecomendacionLocal();
		
		if(miGenerador.getRecomendadosLocal().size()>0){
			@SuppressWarnings("unchecked")
			//String[] recomendadosLocales = (String[]) miGenerador.getRecomendadosLocal().toArray();
			Object[] tmpArray = miGenerador.getRecomendadosLocal().toArray();
			String[] recomendadosLocales = new String[tmpArray.length];
			for (int i = 0 ; i < tmpArray.length; i++){
				recomendadosLocales[i] = tmpArray[i].toString();
			}
			
			// Aqui esta la lista, ya la implementas tu
			tmp = exposicionDao.getExposicionesRecomendadasVista(recomendadosLocales);
			
			exposicionDao.close();
			
		}else{
			ExposicionVista expo = new ExposicionVista("No hay resultados para mostrar", "0");
			tmp=new ArrayList<ExposicionVista>();
			tmp.add(expo);
//			new AlertDialog.Builder(this)
//		      .setMessage("No hay resultados para mostrar")
//		      .setTitle("Alerta")
//		      .setCancelable(true)
//		      .show();
		}
		
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


	
	public void createSubtemaRank(){
		subtemaRank=new HashMap<String, Integer>();
		if(cursorSubtemasRanks.moveToFirst()){
			while(!cursorSubtemasRanks.isAfterLast()){
				String subtema=cursorSubtemasRanks.getString(cursorSubtemasRanks.getColumnIndex("nb_subtema"));
				if(!subtemaRank.containsKey(subtema)){
					Integer rank=(int) cursorSubtemasRanks.getFloat(cursorSubtemasRanks.getColumnIndex("promedio"));
					subtemaRank.put(subtema, rank);
				}
				cursorSubtemasRanks.moveToNext();
			}
		}
	}
	
	public void createTemaSubtema(){
		temaSubtema=new HashMap<String, ArrayList<String>>();
		if(cursorTemasSubtemas.moveToFirst()){
			while(!cursorTemasSubtemas.isAfterLast()){
				ArrayList<String> subtemas;
				String tema=cursorTemasSubtemas.getString(cursorTemasSubtemas.getColumnIndex("nb_tema"));
				if(!temaSubtema.containsKey(tema)){
					subtemas=new ArrayList<String>();
					subtemas.add(cursorTemasSubtemas.getString(cursorTemasSubtemas.getColumnIndex("nb_subtema")));
					temaSubtema.put(tema, subtemas);
				}else{
					subtemas=temaSubtema.get(tema);
					subtemas.add(cursorTemasSubtemas.getString(cursorTemasSubtemas.getColumnIndex("nb_subtema")));
				}
				cursorTemasSubtemas.moveToNext();
			}
		}
	}
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
