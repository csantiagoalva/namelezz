package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;

import com.mx.ipn.escom.ars.restClient.modelo.Museo;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoVista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MuseoDao{
	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public MuseoDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		// TODO Auto-generated constructor stub
	}
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla Museo");
	}
	
	public void close(){
		database.close();
	}
	public long insert(Museo model){
		ContentValues values = new ContentValues();
		values.put("id_museo", model.getIdMuseo());
		values.put("desc_museo", model.getDescMuseo());
		values.put("dir_calle", model.getDirCalle());
		values.put("dir_numero", model.getDirNumero());
		values.put("dir_colonia", model.getDirColonia());
		values.put("dir_delegacion", model.getDirDelegacion());
		values.put("dir_estado", model.getDirEstado());
		values.put("dir_cp", model.getDirCP());
		values.put("contacto", model.getContacto());
		values.put("edo_museo", model.getEdoMuseo());
		values.put("nb_museo", model.getNbMuseo());
		long id = database.insert("museo", null, values);
		return id;
	}
	
	public String findById(Integer id){
		open();
		String nombre = null;
        Cursor c;
		c=database.rawQuery("select nb_museo from museo where id_museo="+id+";", null);
		
		if (c.moveToFirst()) {
			nombre=c.getString(0);			
		}
		c.close();		
		close();
		return nombre;
	}
	
	public void findAll(){
		open();
		Cursor c;
		c=database.rawQuery("select nb_museo from museo", null);
		if (c.moveToFirst()) {
			do {    					
				Log.v("consulta museo", c.getString(0));    	          
			} while(c.moveToNext());
		}		
		c.close();
		close();
	}
	
	/**
	 * Trae todos los museos de la base de datos
	 * @return ArrayList<MuseoVista>
	 */
	public ArrayList<MuseoVista> getMuseosVista() {
        final ArrayList<MuseoVista> contacts = new ArrayList<MuseoVista>();        
        database = dbHelper.getReadableDatabase();
        Cursor c;
        
        c=database.rawQuery("select nb_museo, id_museo from museo;", null);
      //Nos aseguramos de que existe al menos un registro
    	if (c.moveToFirst()) {
    	     //Recorremos el cursor hasta que no haya m�s registros
    	     do {    	
    	         contacts.add(new MuseoVista(c.getString(c.getColumnIndexOrThrow("nb_museo")),c.getString(c.getColumnIndexOrThrow("id_museo"))));
    	    	 //values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
    	     } while(c.moveToNext());
    	}
    	c.close();
    	database.close();             
        return contacts;
    }
	public void dropMuseo(){
		database.execSQL("DROP TABLE IF EXISTS museo;");
		database.execSQL(BaseARSHelper.createMuseo);
	}
}
