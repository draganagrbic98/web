package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Korisnik;
import model.beans.Uloga;
import model.beans.VirtuelnaMasina;
import rest.data.JSONMasinaChange;
import rest.data.OpResponse;
import rest.data.OpResult.MasinaResult;

public class MasineRest implements RestEntity{

	@Override
	public void init() {

		get("rest/masine/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojeMasine());
		});
		
		post("rest/masine/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			MasinaResult result = Main.masine.dodajMasinu(jsonConvertor.fromJson(req.body(), VirtuelnaMasina.class));
			if (result != MasinaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/masine/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			MasinaResult result = Main.masine.izmeniMasinu(jsonConvertor.fromJson(req.body(), JSONMasinaChange.class));
			if (result != MasinaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/masine/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			MasinaResult result = Main.masine.obrisiMasinu(jsonConvertor.fromJson(req.body(), VirtuelnaMasina.class));
			if (result != MasinaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/masine/status", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			
			VirtuelnaMasina masina = Main.masine.nadjiMasinu(jsonConvertor.fromJson(req.body(), String.class));
			return jsonConvertor.toJson(masina.upaljena());
		});
		
		post("rest/masine/promeni_status", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			MasinaResult result = Main.masine.promeniStatusMasine(jsonConvertor.fromJson(req.body(), JSONMasinaChange.class));
			if (result != MasinaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
	}

}
