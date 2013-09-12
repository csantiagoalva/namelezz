package com.mx.ipn.escom.ars.restClient.dao;

import java.util.ArrayList;
import java.util.Random;

import com.mx.ipn.escom.ars.restClient.modelo.ExposicionChecada;
import com.mx.ipn.escom.ars.restClient.modelo.ExposicionVista;
import com.mx.ipn.escom.ars.restClient.modelo.LogroVista;
import com.mx.ipn.escom.ars.restClient.modelo.MuseoVista;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseARSHelper extends SQLiteOpenHelper {

	protected static final int version=1;
	protected static final String name="ars.db";
	//cambie esta
	protected static final String createElemento = "CREATE TABLE IF NOT EXISTS elemento(id_elemento int(10) NOT NULL, nb_elemento varchar(50) NOT NULL, id_exposicion int(10) NOT NULL,  ds_elemento varchar(255) default NULL);";
	protected static final String createExposicion = "CREATE TABLE IF NOT EXISTS exposicion ( id_exposicion int(10) NOT NULL, id_museo int(10) NOT NULL, nb_exposicion varchar(50) NOT NULL, desc_exposicion varchar(255) NOT NULL, rk_exposicion int(10) default NULL, tp_exposicion varchar(255) NOT NULL, edo_exposicion varchar(255) NOT NULL, fh_inicio varchar(10) default NULL, fh_fin varchar(10) default NULL, no_calificaciones int(10) default NULL);";
	protected static final String createExposicionSubtema = "CREATE TABLE IF NOT EXISTS exposicionsubtema ( id_exposicion int(10) NOT NULL, id_subtema int(10) NOT NULL);";
	protected static final String createMuseo = "CREATE TABLE IF NOT EXISTS museo ( id_museo int(10) NOT NULL, nb_museo varchar(50) NOT NULL, desc_museo varchar(255) NOT NULL, dir_calle varchar(40) NOT NULL, dir_numero varchar(10) NOT NULL, dir_colonia varchar(40) NOT NULL, dir_delegacion varchar(40) NOT NULL, dir_estado varchar(40) NOT NULL, dir_cp int(5) NOT NULL, contacto varchar(255) NOT NULL, edo_museo varchar(255) NOT NULL);";
	protected static final String createMuseoTema = "CREATE TABLE IF NOT EXISTS museotema ( id_museo int(10) NOT NULL, id_tema int(10) NOT NULL);";
	protected static final String createRecurso = "CREATE TABLE IF NOT EXISTS recurso ( id_recurso int(10) NOT NULL, id_elemento int(10) NOT NULL, nb_recurso varchar(50) NOT NULL, desc_recurso varchar(255) NOT NULL, nb_archivo_fisico varchar(30) NOT NULL, url_archivo varchar(255) NOT NULL);";
	protected static final String createSubtema = "CREATE TABLE IF NOT EXISTS subtema ( id_subtema int(10) NOT NULL, nb_subtema varchar(50) NOT NULL, ds_subtema varchar(255) default NULL);";
	protected static final String createTema="CREATE TABLE IF NOT EXISTS tema ( id_tema int(10) NOT NULL, nb_tema varchar(50) NOT NULL, ds_tema varchar(255) default NULL);";
	protected static final String createElementoLogro="CREATE TABLE IF NOT EXISTS elementologro ( id_elemento int(10) NOT NULL, id_logro int(10) NOT NULL);";
	protected static final String createLogro="CREATE TABLE IF NOT EXISTS logro (id_logro int(10) NOT NULL, nb_logro varchar(50) NOT NULL, ds_logro varchar(255) NOT NULL);";
	protected static final String createRecompensas="CREATE TABLE IF NOT EXISTS recompensa (id_recompensa int(10) NOT NULL, id_logro int(10) NOT NULL, nb_recompensa varchar(50) NOT NULL, url_recompensa varchar(255) NOT NULL, url_textura varchar(255) NOT NULL);";
	protected static final String createVersion="CREATE TABLE IF NOT EXISTS version (id int(10) NOT NULL, tabla varchar(100) NOT NULL, version int(10) NOT NULL);";
	protected static final String createExposicionChecada="CREATE TABLE IF NOT EXISTS exposicionchecada (id_exposicion int(10) NOT NULL, rk_exposicion int(10) default NULL, enviada tinyint default NULL);";
	
	protected static final String createLogroObtenido="CREATE TABLE IF NOT EXISTS logroobtenido (id_logro int(10) NOT NULL, obtenido tinyint NOT NULL);";
	protected static final String createRecompensaObtenida="CREATE TABLE IF NOT EXISTS recompensaobtenida (id_recompensa int(10) NOT NULL, obtenida tinyint NOT NULL);";
	protected static final String createElementoVisto="CREATE TABLE IF NOT EXISTS elementovisto (id_elemento int(10) NOT NULL, visto tinyint NOT NULL);";

	protected static final String selectSubtemasRanks="select s.nb_subtema, avg(e.rk_exposicion) as promedio from subtema s, exposicionsubtema es, exposicion e where s.id_subtema=es.id_subtema and es.id_exposicion=e.id_exposicion group by s.id_subtema;";
	protected static final String selectTemasSubtemas="select distinct t.nb_tema, s.nb_subtema from tema t, museotema mt, museo m, exposicion e, exposicionsubtema es, subtema s where t.id_tema=mt.id_tema and mt.id_museo=m.id_museo and m.id_museo=e.id_museo and e.id_exposicion=es.id_exposicion and es.id_subtema=s.id_subtema;";
	protected static final String selectExposicionesDestacadas = "select e.*, m.nb_museo from exposicion e, museo m where e.id_museo = m.id_museo and e.rk_exposicion > 7 order by e.no_calificaciones desc,e.rk_exposicion desc limit 10;";
	protected static final String selectExposicionRandom = "select e.*, m.nb_museo from exposicion e, museo m order by random() limit 10;";
	protected static final String selectExposicionesRecomendadas = "select distinct e.id_exposicion, e.nb_exposicion, e.desc_exposicion, e.fh_inicio, e.fh_fin, m.nb_museo from exposicion e, museo m, exposicionsubtema es, subtema s where e.rk_exposicion > 8 and e.id_museo = m.id_museo and es.id_exposicion = e.id_exposicion and es.id_subtema = s.id_subtema and (s.nb_subtema = '";
	protected static final String countExposicionVista = "select count(*) from exposicionchecada;";
		
	
	
	public BaseARSHelper(Context context) {
		super(context, name, null, version);
		
	}	
	
	public void crearAux(SQLiteDatabase database){
		System.out.println("crea auxiliares");		
		database.execSQL(createLogroObtenido);
		database.execSQL(createRecompensaObtenida);
		database.execSQL(createElementoVisto);
		database.execSQL(createExposicionChecada);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		// crea las tablas
		database.execSQL(createElemento);
		database.execSQL(createExposicion);
		database.execSQL(createExposicionSubtema);
		database.execSQL(createMuseo);
		database.execSQL(createMuseoTema);
		database.execSQL(createRecurso);
		database.execSQL(createSubtema);
		database.execSQL(createTema);
		database.execSQL(createLogro);
		database.execSQL(createElementoLogro);
		database.execSQL(createRecompensas);
		database.execSQL(createVersion);
				
//		database.execSQL(createRecompensaObtenida);

/*
		for(int i=1;i<6;i++){
			database.execSQL("insert into logros values ("+i+",'l"+i+"',\"decripcion de prueba del logro para meter texto y hacer espacio en la lista\",0)");
		}
		database.execSQL("insert into logros values (6,\"Patea A Carlos\",\"Patea a carlos 35 veces. \n debes ver los elementos 4 y 5\",0)");		
		database.execSQL("insert into museo  values( 1, 'Cera', 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 2, 'MIDE', 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 3, \"Templo Mayor\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 4, \"Las Interbenciones\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 5, \"San Carlos\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 6, \"Inquisici�n\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 7, \"Antropolog�a\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 8, \"Historia\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 9, \"Ripley\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 10, \"Polit�cnico\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");
		database.execSQL("insert into museo  values( 11, \"Polic�a\", 'sadasdas', 'asdasddasdsa', 'ASDASD', 'dir_coloniavarchar(40)', 'dir_delegacion', 'dir_estado', 'dir_cp', 'contacto', 'edo_museo');");

		database.execSQL("insert into exposicion values (1,1, 'CeraExpo1', 'desc_exposicion',3, 'tp_exposicion', 'edo_exposicion', '2012-09-01', '2012-09-02');");
		database.execSQL("insert into exposicion values ( 2,1, 'CeraExpo2', 'desc_exposicion', 3, 'tp_exposicion', 'edo_exposicion', '2012-09-01', '2012-09-02');");
		database.execSQL("insert into exposicion values ( 3,2, 'CeraExpo3', 'desc_exposicion', 3, 'tp_exposicion', 'edo_exposicion', '2012-09-01', '2012-09-02');");

		database.execSQL("insert into elemento values(1, 'elemento1', 1, 'asdasdas',0);");
		database.execSQL("insert into elemento values(2, 'elemento2', 1, 'asdasdas',0);");
		database.execSQL("insert into elemento values(3, 'elemento3', 3, 'asdasdas',0);");
		database.execSQL("insert into elemento values(4, 'elemento4', 2, 'asdasdas',0);");
		database.execSQL("insert into elemento values(5, 'elemento5', 2, 'asdasdas',0);");

		database.execSQL("insert into logroselemento values(1,1);");
		database.execSQL("insert into logroselemento values(1,2);");		
		database.execSQL("insert into logroselemento values(2,3);");
		database.execSQL("insert into logroselemento values(6,4);");
		database.execSQL("insert into logroselemento values(6,5);");
		database.execSQL("insert into logroselemento values(3,3);");
		database.execSQL("insert into logroselemento values(5,5);");

		database.execSQL("update logros set obtenido=1 where id_logro=4 or id_logro=5;");
		
		database.execSQL("insert into recompensa values(1, 3, 'recompensa1','rec1',0);");
		database.execSQL("insert into recompensa values(2, 3, 'CaracolaMagica','rec2',0);");
		
		
		*/
		
//		database.execSQL("insert into recompensa values(1, 3,'fatso','asd','asd')");
//		database.execSQL("insert into recompensa values(2, 3,'revenant','asd','asd')");
//		database.execSQL("insert into recompensa values(3, 3,'model2','asd','asd')");				
//		
//		database.execSQL("insert into recompensaobtenida values(1, 1);");
//		database.execSQL("insert into recompensaobtenida values(2, 1);");
//		database.execSQL("insert into recompensaobtenida values(3, 1);");

	}	
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// borra las tabla
		database.execSQL("DROP TABLE IF EXISTS elemento");
		database.execSQL("DROP TABLE IF EXISTS exposicion");
		database.execSQL("DROP TABLE IF EXISTS exposicionsubtema");
		database.execSQL("DROP TABLE IF EXISTS museo");
		database.execSQL("DROP TABLE IF EXISTS museotema");
		database.execSQL("DROP TABLE IF EXISTS recurso");
		database.execSQL("DROP TABLE IF EXISTS subtema");
		database.execSQL("DROP TABLE IF EXISTS tema");
		database.execSQL("DROP TABLE IF EXISTS logro");
		database.execSQL("DROP TABLE IF EXISTS elementologro");
		database.execSQL("DROP TABLE IF EXISTS recompensa");
		database.execSQL("DROP TABLE IF EXISTS version");		
		// crea de nuevo las tablas
		database.execSQL(createElemento);
		database.execSQL(createExposicion);
		database.execSQL(createExposicionSubtema);
		database.execSQL(createMuseo);
		database.execSQL(createMuseoTema);
		database.execSQL(createRecurso);
		database.execSQL(createSubtema);
		database.execSQL(createTema);
		database.execSQL(createLogro);
		database.execSQL(createElementoLogro);
		database.execSQL(createRecompensas);
		database.execSQL(createVersion);
	
	}
		

}
