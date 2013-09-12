package com.mx.ipn.escom.ars.recomendador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.util.Log;

public class Recomendador {
	private HashMap<String, Integer> subtemasRank;
	private HashMap<String, ArrayList<String>> temasSubtemas;
	private ArrayList<String> recomendados;
	private Integer[][] tabla;
	private Integer filas;
	private Integer columnas;
	private Object[] arraySubtemas;
	private Object[] arrayTemas;
	private List<Object> subtemas;
	private List<Object> temas;
	private Double umbral;
	public Recomendador() {
	}

	public Recomendador(HashMap<String, Integer> subtemasRank,
			HashMap<String, ArrayList<String>> temasSubtemas, Double umbral) {
		this.subtemasRank = subtemasRank;
		this.temasSubtemas = temasSubtemas;
		this.umbral = umbral;
		//calcularUmbral();
		Log.v("umbral alterado", "valor " + umbral);
		this.filas = this.temasSubtemas.size();
		this.columnas = this.subtemasRank.size();
		Log.v("Recomendador", "filas " + filas);
		Log.v("Recomendador", "columnas " + columnas);
		this.crearTabla();
	}

	public Recomendador(HashMap<String, Integer> subtemasRank,
			HashMap<String, ArrayList<String>> temasSubtemas) {
		this.subtemasRank = subtemasRank;
		this.temasSubtemas = temasSubtemas;
		this.filas = this.temasSubtemas.size();
		this.columnas = this.subtemasRank.size();
		this.crearTabla();
	}

	public HashMap<String, Integer> getSubtemasRank() {
		return subtemasRank;
	}

	public void setSubtemasRank(HashMap<String, Integer> subtemasRank) {
		this.subtemasRank = subtemasRank;
	}

	public HashMap<String, ArrayList<String>> getTemasSubtemas() {
		return temasSubtemas;
	}

	public void setTemasSubtemas(HashMap<String, ArrayList<String>> temasSubtemas) {
		this.temasSubtemas = temasSubtemas;
	}

	public ArrayList<String> getRecomendados() {
		return recomendados;
	}

	public void setRecomendados(ArrayList<String> recomendados) {
		this.recomendados = recomendados;
	}

	public Integer[][] getTabla() {
		return tabla;
	}

	public void setTabla(Integer[][] tabla) {
		this.tabla = tabla;
	}

	public Integer getFilas() {
		return filas;
	}

	public void setFilas(Integer filas) {
		this.filas = filas;
	}

	public Integer getColumnas() {
		return columnas;
	}

	public void setColumnas(Integer columnas) {
		this.columnas = columnas;
	}
	
	public Object[] getArraySubtemas() {
		return arraySubtemas;
	}

	public void setArraySubtemas(Object[] arraySubtemas) {
		this.arraySubtemas = arraySubtemas;
	}

	public Object[] getArrayTemas() {
		return arrayTemas;
	}

	public void setArrayTemas(Object[] arrayTemas) {
		this.arrayTemas = arrayTemas;
	}

	public List<Object> getSubtemas() {
		return subtemas;
	}

	public void setSubtemas(List<Object> subtemas) {
		this.subtemas = subtemas;
	}

	public List<Object> getTemas() {
		return temas;
	}

	public void setTemas(List<Object> temas) {
		this.temas = temas;
	}

	public Double getUmbral() {
		return umbral;
	}

	public void setUmbral(Double umbral) {
		this.umbral = umbral;
	}

	public void crearTabla(){
		this.calcularUmbral();
		System.out.println("nuevo umbral " + this.umbral);
		tabla=new Integer[columnas][filas];
		for(int j=0;j<filas;j++)
			for(int i=0; i<columnas; i++)
				tabla[i][j]=0;
		Set<String> llavesSubtemas=subtemasRank.keySet();
		arraySubtemas= llavesSubtemas.toArray();
		subtemas = Arrays.asList(arraySubtemas);
		//System.out.println("Here: " + arraySubtemas[0]);
		
		Set<String> llavesTemas=temasSubtemas.keySet();
		arrayTemas= llavesTemas.toArray();
		temas= Arrays.asList(arrayTemas);
				
		for(int i=0; i<arrayTemas.length; i++){
			ArrayList<String> tempList=temasSubtemas.get(arrayTemas[i]);
			for(String str: tempList)
				System.out.println("El tema : " + arrayTemas[i] + " esta asociado con " + str);
		}
		
		for(int posTema=0; posTema < arrayTemas.length; posTema++){
			String llaveTema= (String) arrayTemas[posTema];
			System.out.println("El tema " + llaveTema);
			ArrayList<String> listaSubtemas=temasSubtemas.get(llaveTema);
			for(String subtema : listaSubtemas){
				System.out.println("tiene el subtema " + subtema);
				Integer rankSubtema = subtemasRank.get(subtema);
				System.out.println("con la calificacion " + rankSubtema);
				int posSubtema=0;
				for(int i=0;i<arraySubtemas.length;i++){
					System.out.println("arraySubtemas " + arraySubtemas[i]);
					System.out.println("subtema " + subtema);
//					if(subtema.compareTo(arraySubtemas[i].toString())==0)
//						System.out.println("iguales");
					if(subtema.compareTo(arraySubtemas[i].toString())==0){
						posSubtema=i;
						System.out.println("El subtema " + arraySubtemas[i] + " tiene la posicion " + posSubtema);
						break;
					}
				}
				if(tabla[posSubtema][posTema]<=0)
					tabla[posSubtema][posTema]=rankSubtema;				
			}
		}
		Log.v("Temas", "size " + arrayTemas.length);
		Log.v("Subtemas", "size " + arraySubtemas.length);
		for(int i=0; i < filas; i++){
			for(int j=0; j < columnas; j++)
				System.out.println("El tema : " + arrayTemas[i] + " con el subtema " + arraySubtemas[j] + " tiene una calificacion de " + tabla[j][i]);
				//System.out.print(tabla[i][j]);
			//System.out.println();
		}
		for(int i=0; i < filas; i++){
			for(int j=0; j < columnas; j++)
				//System.out.println("El tema : " + arrayTemas[i] + " con el subtema " + arraySubtemas[j] + " tiene una calificacion de " + tabla[i][j]);
				System.out.print(tabla[j][i]);
			System.out.println();
		}
	}

	public ArrayList<Object> obtenerRecomendacion(){
		ArrayList<Object> listaRecomendaciones = new ArrayList<Object>();
		Double semejanza=0.0;
		for(int posSubtema=0; posSubtema < columnas; posSubtema++)
			for(int posComparar = posSubtema + 1; posComparar < columnas; posComparar++){
				semejanza = funcion(posSubtema, posComparar);
				System.out.println("La semejanza entre el subtema " + arraySubtemas[posSubtema] + " y el subtema " + arraySubtemas[posComparar] + " es de " + Math.acos(semejanza));
				if(Math.acos(semejanza)<umbral && Math.acos(semejanza) > 0){
					System.out.println("dentro " + semejanza);
					if(!listaRecomendaciones.contains(arraySubtemas[posSubtema])){
						System.out.println("con umbral " + this.umbral);
						listaRecomendaciones.add(arraySubtemas[posSubtema]);
					}
				}
			}
		for(Object recomendado: listaRecomendaciones)
			System.out.println("Elemento recomendado " + recomendado);
		return listaRecomendaciones;
	}
	
	public Double funcion(int indice1, int indice2) {
		Double res = 0.0;
		Double Rp = 0.0;
		int noRated=0;
		int posSubtema=indice1;
		int posComparar=indice2;
		Log.v("PosSubtema", "indice1 " + indice1);
		Log.v("PosComparar", "indice2 " + indice2);
		Double top=0.0;
		Double bottom=0.0;
		for(int posTema=0;posTema<filas;posTema++){
			if(tabla[posSubtema][posTema] > 0 )
				if(tabla[posComparar][posTema] > 0){
					for(int m=0; m < columnas; m++)
						if(tabla[m][posTema]>0){
							Rp+=tabla[m][posTema];
							noRated++;
						}
					Rp = Rp / noRated;
					System.out.println("Para el tema " + arrayTemas[posTema] + " se comparara el subtema " + arraySubtemas[posSubtema] + " con calificacion " + tabla[posSubtema][posTema] + " con el subtema " + arraySubtemas[posComparar] + " con calificacion " + tabla[posComparar][posTema]);
					System.out.println("Promedio " + Rp);
					top +=(tabla[posSubtema][posTema]-Rp)*(tabla[posComparar][posTema]-Rp);
					bottom += Math.sqrt(Math.pow(tabla[posSubtema][posTema]-Rp, 2.0) * Math.pow(tabla[posComparar][posTema]-Rp, 2.0));
					System.out.println(" top " + top + " y bottom " + bottom);
				}
		}
		res=top/bottom;
		return res;
	}
	
	public void calcularUmbral(){
		if(this.umbral==1 || this.umbral==0)
			this.umbral=90.0;
		else
			this.umbral = 90/(this.umbral + (this.umbral/9.0));
		//System.out.println("despues del calculo " + this.umbral);
	}
}
