package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.Subtema;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SubtemaDao{
	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public SubtemaDao(Context context) {
		dbHelper = new BaseARSHelper(context);
	}

	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la base de datos");
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(Subtema model){
//		Log.v("SubtemaDao", "Subtema " + model.getNbSubtema());
		ContentValues values = new ContentValues();
		values.put("id_subtema", model.getIdSubtema());
		values.put("nb_subtema", model.getNbSubtema());
		values.put("ds_subtema", model.getDsSubtema());
		long id = database.insert("subtema", null, values);
		return id;
	}
	public void dropSubtema(){
		database.execSQL("DROP TABLE IF EXISTS subtema;");
		database.execSQL(BaseARSHelper.createSubtema);
	}
}
