package com.mx.ipn.escom.ars.recomendador;

import java.util.HashMap;
import java.util.ArrayList;

public class Generador {
	private Recomendador recomendadorLocal;
	private Recomendador recomendadorGlobal;
	private HashMap<String, Integer> subtemasRankLocal;
	private HashMap<String, ArrayList<String>> temasSubtemasLocal;
	private HashMap<String, Integer> subtemasRankGlobal;
	private HashMap<String, ArrayList<String>> temasSubtemasGlobal;
	private ArrayList<Object> recomendadosLocal;
	private ArrayList<Object> recomendadosGlobal;
	
	public Generador(HashMap<String, Integer> subtemasRankLocal,
			HashMap<String, ArrayList<String>> temasSubtemasLocal, Double umbral) {
		this.subtemasRankLocal = subtemasRankLocal;
		this.temasSubtemasLocal = temasSubtemasLocal;
		
		recomendadorLocal=new Recomendador(subtemasRankLocal, temasSubtemasLocal, umbral);
	}

	public Generador(HashMap<String, Integer> subtemasRankLocal,
			HashMap<String, ArrayList<String>> temasSubtemasLocal,
			HashMap<String, Integer> subtemasRankGlobal,
			HashMap<String, ArrayList<String>> temasSubtemasGlobal) {
		this.subtemasRankLocal = subtemasRankLocal;
		this.temasSubtemasLocal = temasSubtemasLocal;
		this.subtemasRankGlobal = subtemasRankGlobal;
		this.temasSubtemasGlobal = temasSubtemasGlobal;
		
		recomendadorLocal=new Recomendador(subtemasRankLocal, temasSubtemasLocal);
		recomendadorGlobal=new Recomendador(subtemasRankGlobal, temasSubtemasGlobal);
	}
	
	public void obtenerRecomendacion(){
		recomendadosLocal=recomendadorLocal.obtenerRecomendacion();
		recomendadosGlobal=recomendadorGlobal.obtenerRecomendacion();
	}
	public void obtenerRecomendacionLocal(){
		recomendadosLocal=recomendadorLocal.obtenerRecomendacion();
	}
	public void obtenerRecomendacionGlobal(){
		recomendadosGlobal=recomendadorGlobal.obtenerRecomendacion();
	}
	public Recomendador getRecomendadorLocal() {
		return recomendadorLocal;
	}

	public void setRecomendadorLocal(Recomendador recomendadorLocal) {
		this.recomendadorLocal = recomendadorLocal;
	}

	public Recomendador getRecomendadorGlobal() {
		return recomendadorGlobal;
	}

	public void setRecomendadorGlobal(Recomendador recomendadorGlobal) {
		this.recomendadorGlobal = recomendadorGlobal;
	}

	public HashMap<String, Integer> getSubtemasRankLocal() {
		return subtemasRankLocal;
	}

	public void setSubtemasRankLocal(HashMap<String, Integer> subtemasRankLocal) {
		this.subtemasRankLocal = subtemasRankLocal;
	}

	public HashMap<String, ArrayList<String>> getTemasSubtemasLocal() {
		return temasSubtemasLocal;
	}

	public void setTemasSubtemasLocal(
			HashMap<String, ArrayList<String>> temasSubtemasLocal) {
		this.temasSubtemasLocal = temasSubtemasLocal;
	}

	public HashMap<String, Integer> getSubtemasRankGlobal() {
		return subtemasRankGlobal;
	}

	public void setSubtemasRankGlobal(HashMap<String, Integer> subtemasRankGlobal) {
		this.subtemasRankGlobal = subtemasRankGlobal;
	}

	public HashMap<String, ArrayList<String>> getTemasSubtemasGlobal() {
		return temasSubtemasGlobal;
	}

	public void setTemasSubtemasGlobal(
			HashMap<String, ArrayList<String>> temasSubtemasGlobal) {
		this.temasSubtemasGlobal = temasSubtemasGlobal;
	}

	public ArrayList<Object> getRecomendadosLocal() {
		return recomendadosLocal;
	}

	public void setRecomendadosLocal(ArrayList<Object> recomendadosLocal) {
		this.recomendadosLocal = recomendadosLocal;
	}

	public ArrayList<Object> getRecomendadosGlobal() {
		return recomendadosGlobal;
	}

	public void setRecomendadosGlobal(ArrayList<Object> recomendadosGlobal) {
		this.recomendadosGlobal = recomendadosGlobal;
	}
	
}
