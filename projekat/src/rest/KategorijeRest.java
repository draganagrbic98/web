package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Kategorija;
import model.beans.Korisnik;
import model.beans.Uloga;
import rest.data.JSONKategorijaChange;
import rest.data.OpResponse;
import rest.data.OpResult.KategorijaResult;

public class KategorijeRest implements RestEntity{

	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
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
			KategorijaResult result = Main.kategorije.dodajKategoriju(jsonConvertor.fromJson(req.body(), Kategorija.class));
			if (result != KategorijaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
			
		});
		
		post("rest/kategorije/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			};
			KategorijaResult result = Main.kategorije.izmeniKategoriju(jsonConvertor.fromJson(req.body(), JSONKategorijaChange.class));
			if (result != KategorijaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/kategorije/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			};
			KategorijaResult result = Main.kategorije.obrisiKategoriju(jsonConvertor.fromJson(req.body(), Kategorija.class));
			if (result != KategorijaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
	}

}
