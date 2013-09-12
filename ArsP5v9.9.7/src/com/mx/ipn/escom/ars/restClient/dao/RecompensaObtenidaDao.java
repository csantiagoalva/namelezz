package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ArsMovil.R;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.restClient.modelo.Recompensa;
import com.mx.ipn.escom.ars.restClient.modelo.RecompensaVista;

public class RecompensaObtenidaDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	
	public RecompensaObtenidaDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		
	}
	
	public void open(){
		database=dbHelper.getWritableDatabase();		
	}
	public void close(){
		database.close();
	}
	public long insert(Recompensa model){					
		Cursor c;
		c=database.rawQuery("select * from recompensaobtenida where id_recompensa="+model.getIdRecompensa(), null);						
		if (!c.moveToFirst()){
			ContentValues values = new ContentValues();
			values.put("id_recompensa", model.getIdRecompensa());
			values.put("obtenida", 0);
			long id = database.insert("recompensaobtenida", null, values);
			c.close();		
		return id;
		}
		c.close();
		return 0;
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
		
		recompensas.add(new RecompensaVista("-1", "Carl"));		
		
		c=database.rawQuery("select r.id_recompensa, r.nb_recompensa, ro.obtenida from recompensa r, recompensaobtenida ro where ro.obtenida=1 and r.id_recompensa=ro.id_recompensa", null);
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {
				String modelName=c.getString(c.getColumnIndexOrThrow("nb_recompensa"));
				recompensas.add(new RecompensaVista(c.getString(c.getColumnIndexOrThrow("id_recompensa")),modelName.substring(0, modelName.length()-4)));				    	          
			} while(c.moveToNext());
		}
		c.close();
		database.close();             
		return recompensas;
	}
}