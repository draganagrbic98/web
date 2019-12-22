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
import model.collections.OrganizacijaManipulation;
import model.collections.Organizacije;
import model.dmanipulation.DiskManipulation;
import model.dmanipulation.JDiskChange;
import model.dmanipulation.JKategorijaChange;
import model.dmanipulation.JKorisnikChange;
import model.dmanipulation.JMasinaChange;
import model.dmanipulation.JOrganizacijaChange;
import model.dmanipulation.KategorijaManipulation;
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
	
	public static void kategorijeManipulation() {
		
		get("rest/kategorije/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			return g.toJson(kategorije.getKategorije());
		});
		
		post("rest/kategorije/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			};
			KategorijaManipulation result = kategorije.dodajKategoriju(g.fromJson(req.body(), Kategorija.class));
			if (result != KategorijaManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
			
		});
		
		post("rest/kategorije/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			};
			KategorijaManipulation result = kategorije.izmeniKategoriju(g.fromJson(req.body(), JKategorijaChange.class));
			if (result != KategorijaManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
		});
		
		post("rest/kategorije/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			};
			KategorijaManipulation result = kategorije.obrisiKategoriju(g.fromJson(req.body(), Kategorija.class));
			if (result != KategorijaManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
		});
		
	}
	
	public static void diskoviManipulation() {
		
		get("rest/diskovi/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			return g.toJson(k.getMojiDiskovi());
		});
		
		get("rest/diskovi/unos/tipovi", (req, res) -> {
			res.type("application/json");
			return g.toJson(TipDiska.values());
		});
		
		
		
		post("rest/diskovi/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			DiskManipulation result = diskovi.obrisiDisk(g.fromJson(req.body(), Disk.class));
			if (result != DiskManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
		});
		
		post("rest/diskovi/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			DiskManipulation result = diskovi.izmeniDisk(g.fromJson(req.body(), JDiskChange.class));
			if (result != DiskManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
		});
		
		post("rest/diskovi/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			DiskManipulation result = diskovi.dodajDisk(g.fromJson(req.body(), Disk.class));
			if (result != DiskManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
		});
		
		
		
		
		
	}
	
	public static void userManipulation() {
		
		
		
		
		get("rest/user/uloga", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			return g.toJson(new OpResult((k != null) ? (k.getUloga() + "") : null));
		});
		
	}
	
	public static void organizacijeManipulation() {
		
		
		post("rest/organizacije/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			OrganizacijaManipulation result = organizacije.izmeniOrganizaciju(g.fromJson(req.body(), JOrganizacijaChange.class));
			if (result != OrganizacijaManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
			
		});
		
		post("rest/organizacije/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			OrganizacijaManipulation result = organizacije.dodajOrganizaciju(g.fromJson(req.body(), Organizacija.class));
			if (result != OrganizacijaManipulation.OK) res.status(400);
			return g.toJson(new OpResult(result + ""));
			
		});
		
		get("rest/organizacije/pregled", (req, res) -> {
			
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return g.toJson(new OpResult("Forbidden"));
			}
			return g.toJson(k.getMojeOrganizacije());
			
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
		// storeData();

		port(8080);
		staticFiles.externalLocation(new File("static").getCanonicalPath());

		
		kategorijeManipulation();
		diskoviManipulation();
		userManipulation();
		organizacijeManipulation();
		
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

		

		post("rest/masine/brisanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(masine.obrisiMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
		});

		
		

		post("rest/masine/dodavanje", (req, res) -> {
			res.type("application/json");
			return g.toJson(new OpResult(masine.dodajMasinu(g.fromJson(req.body(), VirtuelnaMasina.class)) + ""));
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
