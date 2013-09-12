package com.mx.ipn.escom.ars.restClient.dao;

import com.mx.ipn.escom.ars.restClient.modelo.Recurso;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecursoDao{
	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public RecursoDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		// TODO Auto-generated constructor stub
	}

	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la base de datos");
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(Recurso model){
		ContentValues values = new ContentValues();
		values.put("id_recurso", model.getIdRecurso());
		values.put("id_elemento", model.getIdElemento());
		values.put("nb_recurso", model.getNbElemento());
		values.put("desc_recurso", model.getDescElemento());
		values.put("nb_archivo_fisico", model.getNbArchivoFisico());
		values.put("url_archivo", model.getUrlArchivo());
		long id = database.insert("recurso", null, values);
		return id;
	}
	public void dropRecurso(){
		database.execSQL("DROP TABLE IF EXISTS recurso;");
		database.execSQL(BaseARSHelper.createRecurso);
	}
}
