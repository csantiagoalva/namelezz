package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.Logro;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LogroDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public LogroDao(Context context) {
		dbHelper = new BaseARSHelper(context);
	}
	public void open(){
		database=dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla Logro");
	}
	public void close(){
		database.close();
	}
	public long insert(Logro model){
		ContentValues values = new ContentValues();
		values.put("id_logro", model.getIdLogro());
		values.put("nb_logro", model.getNbLogro());
		values.put("ds_logro", model.getDsLogro());
		long id = database.insert("logro", null, values);
		return id;
	}
	public void dropLogro(){
		database.execSQL("DROP TABLE IF EXISTS logro;");
		database.execSQL(BaseARSHelper.createLogro);
	}
}