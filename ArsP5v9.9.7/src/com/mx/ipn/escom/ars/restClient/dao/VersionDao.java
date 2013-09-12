package com.mx.ipn.escom.ars.restClient.dao;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mx.ipn.escom.ars.restClient.modelo.Elemento;
import com.mx.ipn.escom.ars.restClient.modelo.Version;

public class VersionDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	private static final String selectVersion = "select * from version;";
	public VersionDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		// TODO Auto-generated constructor stub
	}
	
	public void open(){
		database=dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la base de datos");
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(Version model){
		ContentValues values = new ContentValues();
		values.put("id", model.getId());
		values.put("tabla", model.getTabla());
		values.put("version", model.getVersion());
		long id = database.insert("version", null, values);
		return id;
	}
	public HashMap<String, Integer> getVersionesLocal(){
		Cursor cursorVersionesLocal;
		cursorVersionesLocal = database.rawQuery(selectVersion, null);
		HashMap<String, Integer> versiones = new HashMap<String, Integer>();
		if(cursorVersionesLocal.moveToFirst()){
			while(!cursorVersionesLocal.isAfterLast()){
				String tabla = cursorVersionesLocal.getString(cursorVersionesLocal.getColumnIndex("tabla"));
				Integer version = cursorVersionesLocal.getInt(cursorVersionesLocal.getColumnIndex("version"));
				versiones.put(tabla, version);
				cursorVersionesLocal.moveToNext();
			}
		}
		return versiones;
	}
	
	public void dropVersion(){
		database.execSQL("DROP TABLE IF EXISTS version;");
		database.execSQL(BaseARSHelper.createVersion);
	}
}