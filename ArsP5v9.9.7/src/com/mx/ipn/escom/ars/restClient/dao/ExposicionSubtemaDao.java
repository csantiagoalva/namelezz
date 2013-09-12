package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.ExposicionSubtema;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExposicionSubtemaDao{
	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public ExposicionSubtemaDao(Context context) {
		dbHelper = new BaseARSHelper(context);

	}
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla ExposicionSubtema");
	}
	
	public void close(){
		database.close();
	}
	public long insert(ExposicionSubtema model){
		ContentValues values = new ContentValues();
		values.put("id_exposicion", model.getIdExposicion());
		values.put("id_subtema", model.getIdSubtema());
		long id = database.insert("exposicionsubtema", null, values);
		return id;
	}
	public void dropExposicionSubtema(){
		database.execSQL("DROP TABLE IF EXISTS exposicionsubtema;");
		database.execSQL(BaseARSHelper.createExposicionSubtema);
	}
}
