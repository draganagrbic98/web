package model;

import model.beans.Korisnik;
import model.beans.User;
import model.beans.VirtuelnaMasina;
import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;
import model.dmanipulation.JKorisnikChange;
import model.dmanipulation.JMasinaChange;
import rest.DiskoviRest;
import rest.KategorijeRest;
import rest.OpResponse;
import rest.OrganizacijeRest;
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

	// kad menjam organizaciju nesto mi se ne promeni kod masine...
	public static Gson g = new Gson();

	public static void loadData() throws Exception {
		kategorije.load();
		organizacije.load();
		masine.load();
		diskovi.load();
		korisnici.load();
	}




	
	public static void userManipulation() {
		
		
		
		
		get("rest/user/uloga", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			return g.toJson(new OpResponse((k != null) ? (k.getUloga() + "") : null));
		});
		
	}
	



	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		loadData();
		System.out.println(kategorije);
		System.out.println(masine);
		System.out.println(diskovi);
		System.out.println(organizacije);
		System.out.println(korisnici);


		port(8080);
		staticFiles.externalLocation(new File("static").getCanonicalPath());

		new KategorijeRest().init();
		new DiskoviRest().init();
		new OrganizacijeRest().init();

		userManipulation();
		
		//GARBAGE :D
		
		post("rest/user/login", (req, res) -> {
			res.type("application/json");
			User u = g.fromJson(req.body(), User.class);
			Korisnik k = korisnici.login(u);
			Session ss = req.session(true);

			if (k != null && ss.attribute("korisnik") == null)
				ss.attribute("korisnik", k);
			return g.toJson(k);
		});

		post("rest/user/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			ss.invalidate();
			return g.toJson(new OpResponse("true"));

		});

		get("rest/user/profil", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			return g.toJson((Korisnik)ss.attribute("korisnik"));
		});
		
		post("rest/user/izmena", (req, res) -> {
			res.type("application/json");
			JKorisnikChange jkc = g.fromJson(req.body(), JKorisnikChange.class);
			return g.toJson(new OpResponse(korisnici.izmeniKorisnika(jkc) + ""));
		});
		
		post("rest/masine/izmena", (req, res) -> {
			res.type("application/json");
			JMasinaChange m = g.fromJson(req.body(), JMasinaChange.class);
			return g.toJson(new OpResponse(masine.izmeniMasinu(m) + ""));
		});

		

		post("rest/masine/brisanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResponse(masine.obrisiMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});

		
		

		post("rest/masine/dodavanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResponse(masine.dodajMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});

		

		
		



//
//		get("rest/kategorije/unos/pregled", (req, res) -> {
//			res.type("application/json");
//			return g.toJson(kategorije.getKategorije());
//		});

		
		
		
		
		get("rest/masine/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			ArrayList<VirtuelnaMasina> masine = (k != null) ? k.getMojeMasine() : null;
			return g.toJson(masine);
		});
		
		

		
		
	}

}
