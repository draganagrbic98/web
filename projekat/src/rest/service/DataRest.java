package rest.service;

import static spark.Spark.get;
import static spark.Spark.post;

import model.TipDiska;
import model.Uloga;
import model.beans.Korisnik;
import model.beans.Racun;
import rest.Main;
import rest.RestEntity;
import rest.data.JSONRacunZahtev;
import rest.data.OpResponse;

public class DataRest implements RestEntity{

	@Override
	public void init() {
		
		get("rest/uloge/unos/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(new Uloga[] {Uloga.ADMIN, Uloga.KORISNIK});
		});
		
		get("rest/diskovi/unos/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(TipDiska.values());
		});
		
		get("rest/kategorije/unos/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(Main.kategorije.getKategorije());
		});
		
		post("rest/masine/izracunajRacun", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			Racun racun = k.izracunajRacun(jsonConvertor.fromJson(req.body(), JSONRacunZahtev.class));
			return jsonConvertor.toJson(racun);
		});
	}
	
}
