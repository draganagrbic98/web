package rest.service;

import com.google.gson.Gson;

public interface RestEntity {
	
	public static Gson jsonConvertor = new Gson();
	public void init();

}
