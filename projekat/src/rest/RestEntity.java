package rest;

import com.google.gson.Gson;

import rest.beans.OperationResponse;

public interface RestEntity {
	
	public static Gson jsonConvertor = new Gson();
	public void init();
	
	public static String forbidden() {
		return jsonConvertor.toJson(new OperationResponse("PRISTUP NEDOZVOLJENOM RESURSU"));
	}
	
	public static String badRequest() {
		return jsonConvertor.toJson(new OperationResponse("NEVALIDNI PODACI"));
	}

}
