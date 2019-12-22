package model;

import model.beans.Kategorija;
import model.beans.Korisnik;
import model.beans.TipDiska;
import model.beans.Organizacija;
import model.beans.Uloga;
import model.beans.User;
import model.beans.Disk;
import model.beans.VirtuelnaMasina;
import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;
import model.dmanipulation.JDiskChange;
import model.dmanipulation.JKategorijaChange;
import model.dmanipulation.JKorisnikChange;
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

	// kad menjam organizaciju nesto mi se ne promeni kod masine...
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
		staticFiles.externalLocation(new File("static").getCanonicalPath());

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
			return g.toJson(new OpResult("true"));

		});

		get("rest/user/profil", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			return g.toJson((Korisnik)ss.attribute("korisnik"));
		});
		
		post("rest/user/izmena", (req, res) -> {
			res.type("application/json");
			JKorisnikChange jkc = g.fromJson(req.body(), JKorisnikChange.class);
			return g.toJson(new OpResult(korisnici.izmeniKorisnika(jkc) + ""));
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

		post("rest/kategorije/izmena", (req, res) -> {
			res.type("application/json");
			JKategorijaChange k = g.fromJson(req.body(), JKategorijaChange.class);
			return g.toJson(new OpResult(kategorije.izmeniKategoriju(k) + ""));
		});

		post("rest/masine/brisanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(masine.obrisiMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});

		post("rest/kategorije/brisanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(kategorije.obrisiKategoriju(g.fromJson(req.body(), Kategorija.class)) + ""));
		});

		post("rest/organizacije/dodavanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(organizacije.dodajOrganizaciju(g.fromJson(req.body(), Organizacija.class)) + ""));
		});

		post("rest/masine/dodavanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(masine.dodajMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});

		post("rest/kategorije/dodavanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(kategorije.dodajKategoriju(g.fromJson(req.body(), Kategorija.class)) + ""));
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

		get("rest/diskovi/unos/tipovi", (req, res) -> {
			res.type("application/json");
			return g.toJson(TipDiska.values());
		});
		
		get("rest/organizacije/unos/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(organizacije.getOrganizacije());
		});

		get("rest/kategorije/unos/pregled", (req, res) -> {
			res.type("application/json");
			return g.toJson(kategorije.getKategorije());
		});

		get("rest/kategorije/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			return g.toJson((k != null) ? (k.getMojeKategorije()) : null);

		});
		get("rest/masine/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			ArrayList<VirtuelnaMasina> masine = (k != null) ? k.getMojeMasine() : null;
			return g.toJson(masine);
		});

		get("rest/diskovi/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			ArrayList<Disk> diskovi = (k != null) ? k.getMojiDiskovi() : null;
			return g.toJson(diskovi);
		});
		
		post("rest/diskovi/dodavanje", (req, res) -> {
			res.type("application/json");
			g.fromJson(req.body(), Disk.class);
			System.out.println("PROSLA");
			return g.toJson(new OpResult(diskovi.dodajDisk(g.fromJson(req.body(), Disk.class)) + ""));
		});
		
		post("rest/diskovi/izmena", (req, res) -> {
			res.type("application/json");
			JDiskChange d = g.fromJson(req.body(), JDiskChange.class);
			return g.toJson(new OpResult(diskovi.izmeniDisk(d) + ""));
		});
		
		post("rest/diskovi/brisanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(diskovi.obrisiDisk(g.fromJson(req.body(), Disk.class)) + ""));
		});
		
	}

}
