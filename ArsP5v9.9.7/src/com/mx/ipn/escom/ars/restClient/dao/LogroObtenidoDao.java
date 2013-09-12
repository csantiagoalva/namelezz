package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;

import com.mx.ipn.escom.ars.recorrido.MensajeLogro;
import com.mx.ipn.escom.ars.restClient.modelo.Logro;
import com.mx.ipn.escom.ars.restClient.modelo.LogroVista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LogroObtenidoDao {
	private BaseARSHelper dbHelper;
	private SQLiteDatabase database;
	public LogroObtenidoDao(Context context) {
		dbHelper = new BaseARSHelper(context);
	}
	
	public void open(){
		database=dbHelper.getWritableDatabase();
		Log.v("CLiente Rest", "Se creo la tabla LogroObtenido");
	}
	public void close(){
		database.close();
	}
	public long insert(Logro model){			
		
		Cursor c;
		c=database.rawQuery("select * from logroobtenido where id_logro="+model.getIdLogro(), null);
						
		if (!c.moveToFirst()){						
		ContentValues values = new ContentValues();
		values.put("id_logro", model.getIdLogro());
		values.put("obtenido", 0);
		long id = database.insert("logroobtenido", null, values);
		c.close();		
		return id;
		}
		c.close();
		return 0;
	}
	
	public void findAll(){
		Cursor c;
		c=database.rawQuery("select * from logroobtenido", null);
		if (c.moveToFirst()) {
			do {    					
				Log.v("consulta logros", c.getString(0)+" "+c.getString(1));    	          
			} while(c.moveToNext());
		}		
		c.close();
	}
	
	/**
	 * Retonrna una lista de logro obtenidos  del museo dado.
	 * @param museoId id del museo del cual se obtendran los logro
	 * @return ArrayList<LogroVista>
	 */
	public ArrayList<LogroVista> getLogrosVistaOb(String museoId) {
		final ArrayList<LogroVista> contacts = new ArrayList<LogroVista>();		
		database = dbHelper.getReadableDatabase();
		Cursor c;

		c=database.rawQuery("select DISTINCT l.id_logro, l.nb_logro, l.ds_logro from logro l, elementologro le, elemento e, exposicion ex, logroobtenido lo where lo.obtenido=1 and lo.id_logro=l.id_logro and l.id_logro=le.id_logro and le.id_elemento=e.id_elemento and e.id_exposicion=ex.id_exposicion and ex.id_museo="+museoId, null);
		
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				contacts.add(new LogroVista(c.getString(c.getColumnIndexOrThrow("nb_logro")),c.getString(c.getColumnIndexOrThrow("id_logro")),c.getString(c.getColumnIndexOrThrow("ds_logro"))));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		Log.e("DEBUG","MuseoId:"+museoId);
		Log.e("DEBUG","logros:");
		c=database.rawQuery("select * from logro;",null);
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				Log.e("DEBUG","nombre:"+c.getString(c.getColumnIndexOrThrow("nb_logro"))+"id"+c.getString(c.getColumnIndexOrThrow("id_logro")));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		Log.e("DEBUG","logroObtenido:");
		c=database.rawQuery("select * from logroobtenido;",null);
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				Log.e("DEBUG","logroId:"+c.getString(c.getColumnIndexOrThrow("id_logro"))+"id"+c.getString(c.getColumnIndexOrThrow("obtenido")));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		
		Log.e("DEBUG","elementoLogro:");
		c=database.rawQuery("select * from elementologro;",null);
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				Log.e("DEBUG","logroId:"+c.getString(c.getColumnIndexOrThrow("id_logro"))+"elementoId"+c.getString(c.getColumnIndexOrThrow("id_elemento")));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		
		Log.e("DEBUG","elemento:");
		c=database.rawQuery("select * from elemento;",null);
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				Log.e("DEBUG","elementoId:"+c.getString(c.getColumnIndexOrThrow("id_elemento"))+"exposicionId"+c.getString(c.getColumnIndexOrThrow("id_exposicion")));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		
		Log.e("DEBUG","expo:");
		c=database.rawQuery("select * from exposicion;",null);
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				Log.e("DEBUG","exposicionId:"+c.getString(c.getColumnIndexOrThrow("id_exposicion"))+"museoId"+c.getString(c.getColumnIndexOrThrow("id_museo")));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		
		c.close();
		database.close();
		
		
		return contacts;
	}
	/**
	 * Retonrna una lista de logro por obtener  del museo dado.
	 * @param museoId id del museo del cual se obtendran los logro
	 * @return ArrayList<LogroVista>
	 */
	public ArrayList<LogroVista> getLogrosVistaPorOb(String museoId) {
		final ArrayList<LogroVista> contacts = new ArrayList<LogroVista>();		
		database = dbHelper.getReadableDatabase();
		Cursor c;

		c=database.rawQuery("select DISTINCT l.id_logro, l.nb_logro, l.ds_logro from logro l, elementologro le, elemento e, exposicion ex, logroobtenido lo where lo.obtenido=0 and lo.id_logro=l.id_logro and l.id_logro=le.id_logro and le.id_elemento=e.id_elemento and e.id_exposicion=ex.id_exposicion and ex.id_museo="+museoId, null);
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				contacts.add(new LogroVista(c.getString(c.getColumnIndexOrThrow("nb_logro")),c.getString(c.getColumnIndexOrThrow("id_logro")),c.getString(c.getColumnIndexOrThrow("ds_logro"))));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		c.close();
		database.close();             
		return contacts;
	}

	/**
	 * Recibe el id de un elemento, actualiza su campo visto=1, si al ver ese elemento se cumple algun logro
	 * ese logro se actualiza obtenido=1 si el logro tiene una recompensa asociada tanmbien se obtendra la recomensa
	 * 
	 * @param elementoId
	 * @return MensajeLogro: obtenido=true si se obtubo algun logro y en mensaje tendra un 
	 * string con los nombres de los logros obtenidos
	 */
	public MensajeLogro ObtenerLogro(String elementoId){
		
		database=dbHelper.getWritableDatabase();
		Cursor c;
		Log.e("DEGUB", "Elemento ID: " + elementoId);
		class Logro{
			public String logroId;
			public String logroNombre;
			public int obtenido;
			public Logro(String id, int o, String nb){
				logroId=id;obtenido=o;logroNombre=nb;
			}
		}
		
		//Lista de logros asociados al elemento
		ArrayList<Logro> logroLista=new ArrayList<Logro>();
		//lista de enteros que me dira si todos los elementos de un logro han sido vistos
		ArrayList<Integer>elementosVistos= new ArrayList<Integer>();
		boolean bandera;
		MensajeLogro mensajeL= new MensajeLogro("",false);		
		// actualizo la Base de datos y pongo el elemento como visto
		database.execSQL("update elementovisto set visto=1 where id_elemento="+elementoId);

		//obtengo los logro asociados al elemento visto
		c=database.rawQuery("select l.id_logro, l.nb_logro, lo.obtenido from logro l, elementologro le, logroobtenido lo where lo.id_logro=l.id_logro and l.id_logro=le.id_logro and le.id_elemento="+elementoId, null);
		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				logroLista.add(new Logro(c.getString(c.getColumnIndexOrThrow("id_logro")),c.getInt(c.getColumnIndexOrThrow("obtenido")),c.getString(c.getColumnIndexOrThrow("nb_logro"))));	    	    	    	          
			} while(c.moveToNext());	    	    
		}
		c.close();

		for(Logro aux:logroLista){
			Log.e("DEGUB", "En logro : "+aux.logroNombre);
			elementosVistos.clear();
			c=database.rawQuery("select ev.visto from elementovisto ev, elemento e, elementologro le where ev.id_elemento=e.id_elemento and e.id_elemento=le.id_elemento and le.id_logro= "+aux.logroId, null);			
			if (c.moveToFirst()) {//lleno la elementosVistos con el valor de "visto" de cada elemento asociado al logro
				//Recorremos el cursor hasta que no haya m�s registros
				do {    	
					elementosVistos.add(c.getInt(c.getColumnIndexOrThrow("visto")));
					Log.e("DEGUB", " Visto : "+c.getString(c.getColumnIndexOrThrow("visto")));
				} while(c.moveToNext());	    	    
			}
			c.close();
			bandera=true;//suponemos que todos los elementos han sido vistos
			for(Integer aux2:elementosVistos){

				if(aux2!=0){//el elemento no ha sido visto	    			
					Log.e("DEGUB", "Elemento visto : ");
				}
				else{
					Log.e("DEGUB", "Elemento NO visto : ");
					bandera=false;
				}

			}
			if(bandera){//todos los elementos han sido vistos
				if(aux.obtenido==0){
					aux.obtenido=1;
					//actualizo la base de datos y pongo el logro como obtenido
					database.execSQL("update logroobtenido set obtenido=1 where id_logro="+aux.logroId);
					Log.e("DEGUB", "Logro obtenido ID: " + aux.logroId);
					mensajeL.mensaje=mensajeL.mensaje+"\n Logro obtenido: "+aux.logroNombre;
					mensajeL.obtenido=true;//Se almenos un logro}
					
					c=database.rawQuery("select r.id_logro, r.id_recompensa, r.nb_recompensa, ro.obtenida from recompensa r, recompensaobtenida ro where ro.id_recompensa=r.id_recompensa and r.id_logro="+aux.logroId, null);			
					if (c.moveToFirst()) {//lleno la elementosVistos con el valor de "visto" de cada elemento asociado al logro
						//Recorremos el cursor hasta que no haya m�s registros
						do {    	
							
							if(Integer.valueOf(c.getString(c.getColumnIndexOrThrow("obtenida")))==0){
								database.execSQL("update recompensaobtenida set obtenida=1 where id_recompensa="+c.getString(c.getColumnIndexOrThrow("id_recompensa")));
								Log.e("DEGUB", "Recompensa obtenida ID: " + c.getString(c.getColumnIndexOrThrow("id_recompensa")));
								mensajeL.mensaje=mensajeL.mensaje+"\n Recompensa obtenida: "+c.getString(c.getColumnIndexOrThrow("r.nb_recompensa"));
							}
						} while(c.moveToNext());	    	    
					}
					c.close();
					
				}	
			}
		}
		
		// imprimo reocmpensaObtenida
		Log.e("DEBUG","logro o:");
		c=database.rawQuery("select * from recompensaobtenida;",null);
		if (c.moveToFirst()) {
			//Recorremos el cursor hasta que no haya m�s registros
			do {    	
				Log.e("DEBUG","recompensaidId: "+c.getString(c.getColumnIndexOrThrow("id_recompensa"))+"obtnida: "+c.getString(c.getColumnIndexOrThrow("obtenida")));
				//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
			} while(c.moveToNext());
		}
		//imprmio elementos vistos
		// imprimo reocmpensaObtenida
				Log.e("DEBUG","elemento v:");
				c=database.rawQuery("select * from elementovisto;",null);
				if (c.moveToFirst()) {
					//Recorremos el cursor hasta que no haya m�s registros
					do {    	
						Log.e("DEBUG","elementoId: "+c.getString(c.getColumnIndexOrThrow("id_elemento"))+"visto: "+c.getString(c.getColumnIndexOrThrow("visto")));
						//values.add(c.getString(c.getColumnIndexOrThrow("nb_logro")));    	          
					} while(c.moveToNext());
				}
		database.close(); 
		return mensajeL;
	}
	
}
