package rest;

import com.google.gson.Gson;

import rest.beans.OpResponse;

public interface RestEntity {
	
	public static Gson jsonConvertor = new Gson();
	public void init();
	
	public static String forbidden() {
		
		return jsonConvertor.toJson(new OpResponse("Forbidden"));
		
	}
	
	public static String badRequest() {
		
		return jsonConvertor.toJson(new OpResponse("Bad Request"));
		
	}
	

}
