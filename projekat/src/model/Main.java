package model;

import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

import com.google.gson.Gson;

public class Main {
	
	public static Korisnici korisnici = new Korisnici();
	public static Organizacije organizacije = new Organizacije();
	public static Masine masine = new Masine();
	public static Kategorije kategorije = new Kategorije();
	public static Diskovi diskovi = new Diskovi();
	
	public static Gson g = new Gson();
	
	public static void loadData() throws Exception {
		kategorije.load();
		organizacije.load();
		masine.load();
		diskovi.load();
		korisnici.load();
	}
	
	public static void storeData() throws Exception {
		kategorije.store();
		masine.store();
		diskovi.store();
		organizacije.store();
		korisnici.store();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		loadData();
		System.out.println(kategorije);
		System.out.println(masine);
		System.out.println(diskovi);
		System.out.println(organizacije);
		System.out.println(korisnici);
		//storeData();

		port(8080);
		staticFiles.externalLocation(new File("static").getCanonicalPath());
		get("rest/kategorije/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(kategorije.getKategorije());
		});
		get("rest/organizacije/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(organizacije.getOrganizacije());
		});
		
		get("rest/korisnici/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(korisnici.getKorisnici());
		});
		
		get("rest/masine/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(masine.getMasine());
		});
		
		get("rest/diskovi/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(diskovi.getDiskovi());
		});
		//spreci da kada korisnik ukuca ove urlove dobije jsone na podatke, treba da mu sepokaze 404 erorr
		
	
	}

}
