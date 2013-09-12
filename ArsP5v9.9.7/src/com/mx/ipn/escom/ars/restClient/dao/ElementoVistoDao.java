package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.modelo.Elemento;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.restClient.modelo.Recompensa;
import com.mx.ipn.escom.ars.restClient.modelo.RecompensaVista;

public class ElementoVistoDao {
	ElementoDao elementoDao;
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	
	public ElementoVistoDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		
	}
	
	public void open(){
		database=dbHelper.getWritableDatabase();		
	}
	public void close(){
		database.close();
	}
	public long insert(Elemento model){					
		Cursor c;
		c=database.rawQuery("select * from elementovisto where id_elemento="+model.getIdElemento(), null);						
		if (!c.moveToFirst()){
			ContentValues values = new ContentValues();
			values.put("id_elemento", model.getIdElemento());
			values.put("visto", 0);
			long id = database.insert("elementovisto", null, values);
			c.close();		
		return id;
		}
		c.close();
		return 0;
	}
	
	public Double vistos(Integer idExpo){
		Log.d("ElementoVistoDao", "en vistos");
		Integer total=0;
		Integer vistos = 0;
		Double porcentaje;
		open();
		Cursor c;
		c=database.rawQuery("select count(*) from elemento where id_exposicion="+idExpo+";", null);
		if (c.moveToFirst()) {
			do {    					
				total=c.getInt(0);    	          
			} while(c.moveToNext());
		}
		
		c=database.rawQuery("select count(ev.id_elemento) from elementovisto ev, elemento e where ev.visto=1 and ev.id_elemento=e.id_elemento and e.id_exposicion="+idExpo+";", null);
		if (c.moveToFirst()) {
			do {    					
				vistos=c.getInt(0);    	          
			} while(c.moveToNext());
		}		
		c.close();				
		close();
		
		porcentaje=(double) ((vistos*100)/total);
		return porcentaje;
	}
	
	/**
	 * Retonrna una lista de las recompensas obtenidos  del usuario.
	 * 
	 * @return ArrayList<RecompensaVista>
	 */
	public ArrayList<RecompensaVista> getRecompensasVistaOb() {
		final ArrayList<RecompensaVista> recompensas = new ArrayList<RecompensaVista>();		
		database = dbHelper.getWritableDatabase();
		Cursor c;
		
		recompensas.add(new RecompensaVista("-1", "model2"));
		recompensas.add(new RecompensaVista("-1", "model3"));
		
		c=database.rawQuery("select r.id_recompensa, r.nb_recompensa, ro.obtenida from recompensa r, recompensaobtenida ro where ro.obtenida=1 and r.id_recompensa=ro.id_recompensa", null);
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				recompensas.add(new RecompensaVista(c.getString(c.getColumnIndexOrThrow("id_recompensa")),c.getString(c.getColumnIndexOrThrow("nb_recompensa"))));				    	          
			} while(c.moveToNext());
		}
		c.close();
		database.close();             
		return recompensas;
	}
}