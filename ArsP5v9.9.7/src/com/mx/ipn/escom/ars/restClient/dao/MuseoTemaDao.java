package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.MuseoTema;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MuseoTemaDao{
	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public MuseoTemaDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		// TODO Auto-generated constructor stub
	}

	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla MuseoTema");
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(MuseoTema model){
		ContentValues values = new ContentValues();
		values.put("id_museo", model.getIdMuseo());
		values.put("id_tema", model.getIdTema());
		long id = database.insert("museotema", null, values);
		return id;
	}
	public void dropMuseoTema(){
		database.execSQL("DROP TABLE IF EXISTS museotema;");
		database.execSQL(BaseARSHelper.createMuseoTema);
	}
}
