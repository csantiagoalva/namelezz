package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mx.ipn.escom.ars.restClient.modelo.Exposicion;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionVista;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoVista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExposicionDao{

	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public ExposicionDao(Context context) {
		dbHelper = new BaseARSHelper(context);
	}
	
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la tabla Exposicion");
	}
	
	public void close(){
		database.close();
	}
	
	public long insert(Exposicion model){
		ContentValues values = new ContentValues();
		values.put("id_exposicion", model.getIdExposicion());
		values.put("id_museo", model.getIdMuseo());
		values.put("nb_exposicion", model.getNbExposicion());
		values.put("desc_exposicion", model.getDescExposicion());
		values.put("rk_exposicion", model.getRkExposcion());
		values.put("tp_exposicion", model.getRpExposicion());
		values.put("edo_exposicion", model.getEdoExposicion());
		values.put("fh_inicio", model.getFhInicio());
		values.put("fh_fin", model.getFhFin());
		values.put("no_calificaciones", model.getNoCalificaciones());
		long id = database.insert("exposicion", null, values);
		return id;
	}
	
	public void findAll(){
		open();
		Cursor c;
		c=database.rawQuery("select id_exposicion, rk_exposicion from exposicion", null);
		if (c.moveToFirst()) {
			do {    					
				Log.v("consulta exposicion", c.getString(0)+" "+c.getString(1));    	          
			} while(c.moveToNext());
		}		
		c.close();
		close();
	}
	
	
	public void dropExposicion(){
		database.execSQL("DROP TABLE IF EXISTS exposicion;");
		database.execSQL(BaseARSHelper.createExposicion);
	}
	
	
	/**
	 * Trae todas las exposiciones de un museo dado de la base de datos
	 * @return ArrayList<ExposicionVista>
	 */
	public ArrayList<ExposicionVista> getExposicionVista(String museoId) {
        final ArrayList<ExposicionVista> exposiciones = new ArrayList<ExposicionVista>();        
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c;
        
        c=db.rawQuery("select nb_exposicion, id_exposicion from exposicion where id_museo="+museoId, null);
      //Nos aseguramos de que existe al menos un registro
    	if (c.moveToFirst()) {
    	     //Recorremos el cursor hasta que no haya m�s registros
    	     do {    	
    	    	 exposiciones.add(new ExposicionVista(c.getString(c.getColumnIndexOrThrow("nb_exposicion")),c.getString(c.getColumnIndexOrThrow("id_exposicion"))));    	    	   	         
    	     } while(c.moveToNext());
    	}
    	c.close();
    	db.close();             
        return exposiciones;
    }
	
	public Cursor getExposicionChecada(){
		return database.rawQuery(BaseARSHelper.countExposicionVista, null);
	}

	public Cursor getSubtemasRanks(){
		return database.rawQuery(BaseARSHelper.selectSubtemasRanks, null);
	}

	public Cursor getTemasSubtemas(){
		return database.rawQuery(BaseARSHelper.selectTemasSubtemas, null);
	}
	
	public Cursor getExposicionesRecomendadas(String[] subtemas){
		String selectExposicionesRecomendadasTmp = BaseARSHelper.selectExposicionesRecomendadas;
		for(int i=0; i<subtemas.length; i++){
			if(subtemas.length == 1){
				selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + subtemas[i] + "');";
				Log.v("en el primero", "no hay mas registros");
			}else{
				if(i==0){
					selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + subtemas[i] + "'";
					Log.v("en el primero", "hay mas registros");
				}else if(i==subtemas.length-1){
					selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + " or s.nb_subtema = '" + subtemas[i] + "');";
					Log.v("en el ultimo", "ya no hay mas registros");
				}else{
					selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + " or s.nb_subtema = '" + subtemas[i] + "'";
					Log.v("en el cursor", "hay mas registros");
				}
			}			
		}
		Log.v("String de sentencia", selectExposicionesRecomendadasTmp);
		System.out.println("Sentencia 1" + selectExposicionesRecomendadasTmp.substring(0, selectExposicionesRecomendadasTmp.length()/2));
		System.out.println("Sentencia 2" + selectExposicionesRecomendadasTmp.substring(selectExposicionesRecomendadasTmp.length()/2, selectExposicionesRecomendadasTmp.length()));
		return database.rawQuery(selectExposicionesRecomendadasTmp, null);
	}
	
	public ArrayList<ExposicionVista> getExposicionesRecomendadasVista(String[] subtemas){
		
		ArrayList<ExposicionVista> exposicionesRecomendadasVistas = new ArrayList<ExposicionVista>(); 
		String selectExposicionesRecomendadasTmp = BaseARSHelper.selectExposicionesRecomendadas;
		for(int i=0; i<subtemas.length; i++){
			if(subtemas.length == 1){
				selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + subtemas[i] + "');";
				Log.v("en el primero", "no hay mas registros");
			}else{
				if(i==0){
					selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + subtemas[i] + "'";
					Log.v("en el primero", "hay mas registros");
				}else if(i==subtemas.length-1){
					selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + " or s.nb_subtema = '" + subtemas[i] + "');";
					Log.v("en el ultimo", "ya no hay mas registros");
				}else{
					selectExposicionesRecomendadasTmp = selectExposicionesRecomendadasTmp + " or s.nb_subtema = '" + subtemas[i] + "'";
					Log.v("en el cursor", "hay mas registros");
				}
			}			
		}
		Log.v("String de sentencia", selectExposicionesRecomendadasTmp);
		System.out.println("Sentencia 1" + selectExposicionesRecomendadasTmp.substring(0, selectExposicionesRecomendadasTmp.length()/2));
		System.out.println("Sentencia 2" + selectExposicionesRecomendadasTmp.substring(selectExposicionesRecomendadasTmp.length()/2, selectExposicionesRecomendadasTmp.length()));
		Cursor cursorTmp = database.rawQuery(selectExposicionesRecomendadasTmp, null);
		if(cursorTmp.moveToFirst()){
			while(!cursorTmp.isAfterLast()){
				String expoNombre = cursorTmp.getString(cursorTmp.getColumnIndex("nb_exposicion"));
				String expoDesc = cursorTmp.getString(cursorTmp.getColumnIndex("desc_exposicion"));
				String fechaInicio = cursorTmp.getString(cursorTmp.getColumnIndex("fh_inicio"));
				String fechaFin = cursorTmp.getString(cursorTmp.getColumnIndex("fh_fin"));
				String museoNombre = cursorTmp.getString(cursorTmp.getColumnIndex("nb_museo"));
				exposicionesRecomendadasVistas.add(new ExposicionVista(expoNombre, expoDesc, fechaInicio, fechaFin, museoNombre));
				cursorTmp.moveToNext();
			}
		}else{
			System.out.println("No hay registros.");
		}
		
		return exposicionesRecomendadasVistas;
	}
	
	public Cursor getExposicionesDestacadas(){
		return database.rawQuery(BaseARSHelper.selectExposicionesDestacadas, null);
	}
	
	public ArrayList<ExposicionVista> getExposicionesDestacadasVista(){		
		
		ArrayList<ExposicionVista> exposicionesDestacadasVistas = new ArrayList<ExposicionVista>(); 
		Cursor cursorTmp = database.rawQuery(BaseARSHelper.selectExposicionesDestacadas, null);		
		if(cursorTmp.moveToFirst()){
			while(!cursorTmp.isAfterLast()){
				String expoNombre = cursorTmp.getString(cursorTmp.getColumnIndex("nb_exposicion"));
				String expoDesc = cursorTmp.getString(cursorTmp.getColumnIndex("desc_exposicion"));
				String fechaInicio = cursorTmp.getString(cursorTmp.getColumnIndex("fh_inicio"));
				String fechaFin = cursorTmp.getString(cursorTmp.getColumnIndex("fh_fin"));
				String museoNombre = cursorTmp.getString(cursorTmp.getColumnIndex("nb_museo"));
				exposicionesDestacadasVistas.add(new ExposicionVista(expoNombre, expoDesc, fechaInicio, fechaFin, museoNombre));
				cursorTmp.moveToNext();
			}
		}else{
			System.out.println("No hay registros.");
		}
		
		return exposicionesDestacadasVistas;
	}
	
	public Cursor getExposicionRandom(){
		String selectExposicionRandomTmp = BaseARSHelper.selectExposicionRandom;
		Random rnd = new Random(System.currentTimeMillis());
		Integer number = 0;
		do{
			number = rnd.nextInt(11);
		}while(number <4);
		selectExposicionRandomTmp = selectExposicionRandomTmp + number + ";";
		return database.rawQuery(selectExposicionRandomTmp, null);
	}
	
	public ArrayList<ExposicionVista> getExposicionRandomVista(){
		ArrayList<ExposicionVista> exposicionesRandomVistas = new ArrayList<ExposicionVista>(); 
		String selectExposicionRandomTmp = BaseARSHelper.selectExposicionRandom;
		
		
		Cursor cursorTmp = database.rawQuery(selectExposicionRandomTmp, null);
		if(cursorTmp.moveToFirst()){
			while(!cursorTmp.isAfterLast()){
				String expoId = cursorTmp.getString(cursorTmp.getColumnIndex("id_exposicion"));
				String expoNombre = cursorTmp.getString(cursorTmp.getColumnIndex("nb_exposicion"));
				String expoDesc = cursorTmp.getString(cursorTmp.getColumnIndex("desc_exposicion"));
				String fechaInicio = cursorTmp.getString(cursorTmp.getColumnIndex("fh_inicio"));
				String fechaFin = cursorTmp.getString(cursorTmp.getColumnIndex("fh_fin"));
				String museoNombre = cursorTmp.getString(cursorTmp.getColumnIndex("nb_museo"));
				exposicionesRandomVistas.add(new ExposicionVista(expoNombre, expoDesc, fechaInicio, fechaFin, museoNombre));
				cursorTmp.moveToNext();
			}
		}else{
			System.out.println("No hay registros.");
		}		
		return exposicionesRandomVistas;
	}
}
