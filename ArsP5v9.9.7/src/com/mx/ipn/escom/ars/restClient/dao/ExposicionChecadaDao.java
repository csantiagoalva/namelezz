package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mx.ipn.escom.ars.restClient.modelo.Exposicion;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionChecada;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoVista;

public class ExposicionChecadaDao {
	protected static final int version=1;
	protected static final String name="ars.db";
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public ExposicionChecadaDao(Context context) {
		dbHelper = new BaseARSHelper(context);
		
	}
	
	public void open(){
		database = dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se obtuvo la base de datos");
	}
	
	public void close(){
		database.close();
	}
	
//	public long insert(ExposicionChecada model){
//		ContentValues values = new ContentValues();
//		values.put("id_exposicion", model.getIdExposicion());
//		values.put("rk_exposicion", model.getRkExposicion());
//		values.put("enviada", 0);
//		long id = database.insert("exposicionchecada", null, values);
//		return id;
//	}
	
public long insert(Exposicion model){					
		Cursor c;
		c=database.rawQuery("select * from exposicionchecada where id_exposicion="+model.getIdExposicion(), null);
						
		if (!c.moveToFirst()){						
		ContentValues values = new ContentValues();
		values.put("id_exposicion", model.getIdExposicion());
		values.put("rk_exposicion", 0);
		values.put("enviada", 0);
		long id = database.insert("exposicionchecada", null, values);
		c.close();		
		return id;
		}
		c.close();
		return 0;
	}
	
	public Integer calificacion(Integer idExpo){
		Integer calificacion = 0;
		open();
		Cursor c;
		c=database.rawQuery("select rk_exposicion from exposicionchecada where id_exposicion="+idExpo+";", null);
		if (c.moveToFirst()){			
				calificacion=c.getInt(0);
				Log.v("calif", calificacion.toString());
		}
		c.close();
		close();
		return calificacion;
	}
	
	public void calificar(Integer calif,Integer idExpo){
		Log.v("calificando", calif+","+idExpo);
		open();				
		database.execSQL("update exposicionchecada set rk_exposicion="+calif+" where id_exposicion="+idExpo+";");				
		close();
	}
	
	public void dropExposicionChecada(){
		database.execSQL("DROP TABLE IF EXISTS exposicionchecada;");
		database.execSQL(BaseARSHelper.createExposicionChecada);
	}
	
	public ArrayList<ExposicionChecada> getExposiciones() {
        final ArrayList<ExposicionChecada> expos = new ArrayList<ExposicionChecada>();        
        open();
        Cursor c;
        
//        database.execSQL("insert into exposicionchecada values(27,10,0);");
        
        c=database.rawQuery("select * from exposicionchecada where enviada=0 and not rk_exposicion=0;", null);
    	if (c.moveToFirst()) {
    	     do {    	    	 
    	         expos.add(new ExposicionChecada(c.getInt(0),c.getInt(1),c.getInt(2)));    	          
    	     } while(c.moveToNext());
    	}else{
    		Log.v("error", "no hay registros");
    	}
    	c.close();
    	close();                	    	
        return expos;
    }
	
	public void setEnviada(Integer idExpo){
			Log.v("Enviada ok", ""+idExpo);
			open();				
			database.execSQL("update exposicionchecada set enviada=1 where id_exposicion="+idExpo+";");				
			close();
		
	}
}
