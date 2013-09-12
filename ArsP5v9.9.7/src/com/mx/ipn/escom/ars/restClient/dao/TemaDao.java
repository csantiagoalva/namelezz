package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.Tema;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class TemaDao{

	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public TemaDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		// TODO Auto-generated constructor stub
	}
	
	public void open(){
		
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la base de datos");
		//dbHelper.onCreate(database);
		//dbHelper.onUpgrade(database, 1, 1);
		/*if(!checkDataBase()){
			Log.v("CLiente Rest", "No existe la base de datos, se va a crear");
			dbHelper.onCreate(database);
		}
		else{
			Log.v("CLiente Rest", "Ya existe la base de datos, se va a actualizar");
			dbHelper.onUpgrade(database, 1, 1);
		}*/				
	}
	
	private boolean checkDataBase() {
	    SQLiteDatabase checkDB = null;
	    try {
	        checkDB = SQLiteDatabase.openDatabase("/data/data/com.mx.ipn.escom.clientRest.activity/databases/ars", null,
	                SQLiteDatabase.OPEN_READONLY);
	        checkDB.close();
	    } catch (SQLiteException e) {
	        // database doesn't exist yet.
	    }
	    return checkDB != null ? true : false;
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(Tema model){
		ContentValues values = new ContentValues();
		values.put("id_tema", model.getIdTema());
		values.put("nb_tema", model.getNbTema());
		values.put("ds_tema", model.getDsTema());
		long id = database.insert("tema", null, values);
		Log.v("Id tema", ": " + id);
		return id;
	}
	public void dropTema(){
		database.execSQL("DROP TABLE IF EXISTS tema;");
		database.execSQL(BaseARSHelper.createTema);
	}
}
