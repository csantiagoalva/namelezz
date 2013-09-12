package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;

import com.mx.ipn.escom.ars.restClient.modelo.Recompensa;
import com.mx.ipn.escom.ars.restClient.modelo.RecompensaVista;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecompensaDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public RecompensaDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		
	}
	
	public void open(){
		database=dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla Recompensa");
	}
	public void close(){
		database.close();
	}
	public long insert(Recompensa model){
		ContentValues values = new ContentValues();
		values.put("id_recompensa", model.getIdRecompensa());
		values.put("id_logro", model.getIdLogro());
		values.put("nb_recompensa", model.getNbRecompensa());
		values.put("url_recompensa", model.getUrlRecompensa());
		values.put("url_textura", model.getUrlTextura());
		long id = database.insert("recompensa", null, values);
		return id;
	}
	
	public void dropRecompensa(){
		database.execSQL("DROP TABLE IF EXISTS recompensa;");
		database.execSQL(BaseARSHelper.createRecompensas);
	}
	
	
	
	/**
	 * Retonrna una lista de las recompensas obtenidos  del usuario.
	 * 
	 * @return ArrayList<RecompensaVista>
	 */
//	public ArrayList<RecompensaVista> getRecompensasVistaOb() {
//		final ArrayList<RecompensaVista> recompensas = new ArrayList<RecompensaVista>();		
//		database = dbHelper.getWritableDatabase();
//		Cursor c;
//
//		c=database.rawQuery("select * from recompensa where obtenido=1", null);
//		//Nos aseguramos de que existe al menos un registro
//		if (c.moveToFirst()) {
//			//Recorremos el cursor hasta que no haya m�s registros
//			do {    	
//				recompensas.add(new RecompensaVista(c.getString(c.getColumnIndexOrThrow("id_recompensa")),c.getString(c.getColumnIndexOrThrow("nb_recompensa")),c.getString(c.getColumnIndexOrThrow("nb_archivo_fisico"))));				    	          
//			} while(c.moveToNext());
//		}
//		c.close();
//		database.close();             
//		return recompensas;
//	}
}
