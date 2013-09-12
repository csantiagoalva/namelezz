package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.Elemento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ElementoDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public ElementoDao(Context context) {
		dbHelper = new BaseARSHelper(context);
	}
	
	public void open(){
		database=dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla Elemento");
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(Elemento model){
		ContentValues values = new ContentValues();
		values.put("id_elemento", model.getIdElemento());
		values.put("nb_elemento", model.getNbElemento());
		values.put("id_exposicion", model.getIdExposicion());
		values.put("ds_elemento", model.getDsElemento());
		long id = database.insert("elemento", null, values);
		return id;
	}	
	
	
	public void dropElemento(){
		database.execSQL("DROP TABLE IF EXISTS elemento;");
		database.execSQL(BaseARSHelper.createElemento);
	}
}
