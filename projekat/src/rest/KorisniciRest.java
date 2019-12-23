package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Korisnik;
import model.beans.Uloga;
import rest.OpResult.KorisnikResult;

public class KorisniciRest implements RestEntity{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		get("rest/korisnici/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojiKorisnici());
		});
		
		post("rest/korisnici/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			KorisnikResult result = Main.korisnici.dodajKorisnika(jsonConvertor.fromJson(req.body(), Korisnik.class));
			if (result != KorisnikResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/korisnici/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			KorisnikResult result = Main.korisnici.izmeniKorisnika(jsonConvertor.fromJson(req.body(), JSONKorisnikChange.class));
			if (result != KorisnikResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/korisnici/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			KorisnikResult result = Main.korisnici.obrisiKorisnika(jsonConvertor.fromJson(req.body(), Korisnik.class), k.getUser());
			if (result != KorisnikResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		get("rest/uloge/unos/pregled", (req, res) -> {
			res.type("application/json");
			return jsonConvertor.toJson(Uloga.values());
		});
		
	}

}
