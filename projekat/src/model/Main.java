package model;

import model.beans.Korisnik;
import model.beans.Uloga;
import model.beans.User;
import model.beans.VirtuelnaMasina;
import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;
import model.dmanipulation.JMasinaChange;
import model.dmanipulation.JOrganizacijaChange;
import model.dmanipulation.OpResult;
import spark.Session;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Main {

	public static Korisnici korisnici = new Korisnici();
	public static Organizacije organizacije = new Organizacije();
	public static Masine masine = new Masine();
	public static Kategorije kategorije = new Kategorije();
	public static Diskovi diskovi = new Diskovi();

	//kad menjam organizaciju nesto mi se ne promeni kod masine...
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
		// storeData();

		port(8080);
		staticFiles.externalLocation(new File("static2").getCanonicalPath());
//		get("rest/kategorije/pregled", (req, res) -> {
//			res.type("application/json");
//			return g.toJson(kategorije.getKategorije());
//		});
//		get("rest/organizacije/pregled", (req, res) -> {
//			res.type("application/json");
//			return g.toJson(organizacije.getOrganizacije());
//		});
//
//		get("rest/korisnici/pregled", (req, res) -> {
//			res.type("application/json");
//			return g.toJson(korisnici.getKorisnici());
//		});
//
////		get("rest/masine/pregled", (req, res) -> {
////			res.type("application/json");
////			return g.toJson(masine.getMasine());
////		});
//
//		get("rest/diskovi/pregled", (req, res) -> {
//			res.type("application/json");
//			return g.toJson(diskovi.getDiskovi());
//		});
//		
//		
		

		// spreci da kada korisnik ukuca ove urlove dobije jsone na podatke, treba da mu
		// sepokaze 404 erorr

//		post("rest/user/login", (req, res) -> {
//			res.type("application/json");
//			User u = g.fromJson(req.body(), User.class);
//			Korisnik k = korisnici.login(u);
//			Session ss = req.session(true);
//			if (k != null && ss.attribute("user") == null)
//				ss.attribute("user", k);
//			return g.toJson(k);
//		});
//		
//		
		post("rest/user/login", (req, res) -> {
			res.type("application/json");
			User u = g.fromJson(req.body(), User.class);
			Korisnik k = korisnici.login(u);
			Session ss = req.session(true);
			if (k != null && ss.attribute("korisnik") == null)
				ss.attribute("korisnik", k);
			return g.toJson(k);
			
		});

		post("rest/masine/izmena", (req, res) -> {
			res.type("application/json");
			JMasinaChange m = g.fromJson(req.body(), JMasinaChange.class);
			
			return g.toJson(new OpResult(masine.izmeniMasinu(m) + ""));
			
		});
		
		post("rest/organizacije/izmena", (req, res) -> {
			res.type("application/json");
			JOrganizacijaChange o = g.fromJson(req.body(), JOrganizacijaChange.class);
			return g.toJson(new OpResult(organizacije.izmeniOrganizaciju(o) + ""));

		});
		
		post("rest/masine/brisanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(masine.obrisiMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});
		
		post("rest/masine/dodavanje", (req, res) -> {
			res.type("application/json");
			g.fromJson(req.body(), VirtuelnaMasina.class);
			System.out.println("PROSLA");
			return g.toJson(new OpResult(masine.dodajMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});
		
		
		get("rest/user/uloga", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			return g.toJson(new OpResult((k != null) ? (k.getUloga() + "") : null));
		});
		
		get("rest/organizacije/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			if (k != null && k.getUloga().equals(Uloga.KORISNIK)) res.status(403);
			return g.toJson((k != null) ? k.getMojeOrganizacije() : null);
			
		});
		
		get("rest/organizacije/unos/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(organizacije.getOrganizacije());
		});
		
		get("rest/kategorije/unos/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(kategorije.getKategorije());
		});
		
		get("rest/masine/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			ArrayList<VirtuelnaMasina> masine = (k != null) ? k.getMojeMasine() : null;
			return g.toJson(masine);
		});
		
		

	}

}
