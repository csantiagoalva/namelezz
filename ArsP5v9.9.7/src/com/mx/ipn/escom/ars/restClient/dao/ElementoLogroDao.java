package com.mx.ipn.escom.ars.restClient.dao;


import com.mx.ipn.escom.ars.restClient.modelo.ElementoLogro;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ElementoLogroDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public ElementoLogroDao(Context context) {
		dbHelper = new BaseARSHelper(context);
	}
	public void open(){
		database=dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla ElementoLogro");
	}
	public void close(){
		database.close();
	}
	public long insert(ElementoLogro model){
		ContentValues values = new ContentValues();
		values.put("id_elemento", model.getIdElemento());
		values.put("id_logro", model.getIdLogro());
		long id = database.insert("elementoLogro", null, values);
		return id;
	}
	public void dropElementoLogro(){
		database.execSQL("DROP TABLE IF EXISTS elementologro;");
		database.execSQL(BaseARSHelper.createElementoLogro);
	}
}
