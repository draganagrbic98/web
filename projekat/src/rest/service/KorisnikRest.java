package rest.service;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Uloga;
import model.beans.Korisnik;
import rest.Main;
import rest.data.KorisnikChange;
import rest.data.OpResponse;
import rest.data.OpResult.KorisnikResult;

public class KorisnikRest implements RestEntity{

	@Override
	public void init() {
		
		get("/rest/korisnici/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojiKorisnici());
		});
		
		post("/rest/korisnici/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				Korisnik korisnik = jsonConvertor.fromJson(req.body(), Korisnik.class);
				if (korisnik == null || !korisnik.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				KorisnikResult result = Main.korisnici.dodajKorisnika(korisnik);
				if (result != KorisnikResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("/rest/korisnici/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				KorisnikChange korisnik = jsonConvertor.fromJson(req.body(), KorisnikChange.class);
				if (korisnik == null || !korisnik.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				KorisnikResult result = Main.korisnici.izmeniKorisnika(korisnik, k);
				if (result != KorisnikResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("rest/korisnici/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				Korisnik korisnik = jsonConvertor.fromJson(req.body(), Korisnik.class);
				if (korisnik == null || !korisnik.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				KorisnikResult result = Main.korisnici.obrisiKorisnika(korisnik, k);
				if (result != KorisnikResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
	}

}
