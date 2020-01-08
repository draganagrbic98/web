package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Kategorija;
import model.beans.Korisnik;
import model.beans.Uloga;
import rest.data.KategorijaChange;
import rest.data.OpResponse;
import rest.data.OpResult.KategorijaResult;

public class KategorijaRest implements RestEntity{
	
	@Override
	public void init() {

		get("rest/kategorije/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(Main.kategorije.getKategorije());
		});
		
		post("rest/kategorije/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			};
			try {
				Kategorija kategorija = jsonConvertor.fromJson(req.body(), Kategorija.class);
				if (!Kategorija.validData(kategorija)) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				KategorijaResult result = Main.kategorije.dodajKategoriju(kategorija);
				if (result != KategorijaResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));				
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("rest/kategorije/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			};
			try {
				KategorijaChange kategorija = jsonConvertor.fromJson(req.body(), KategorijaChange.class);
				if (!KategorijaChange.validData(kategorija)) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				KategorijaResult result = Main.kategorije.izmeniKategoriju(kategorija);
				if (result != KategorijaResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("rest/kategorije/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			};
			try {
				Kategorija kategorija = jsonConvertor.fromJson(req.body(), Kategorija.class);
				if (!Kategorija.validData(kategorija)) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				KategorijaResult result = Main.kategorije.obrisiKategoriju(kategorija);
				if (result != KategorijaResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));				
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
	}

}
