package rest.service;

import static spark.Spark.get;
import static spark.Spark.post;

import model.beans.Korisnik;
import model.beans.VirtuelnaMasina;
import model.support.Uloga;
import rest.Main;
import rest.RestEntity;
import rest.data.MasinaChange;
import rest.data.OpResponse;
import rest.data.OpResult.MasinaResult;

public class MasinaRest implements RestEntity{

	@Override
	public void init() {

		//SREDI!!!!!!!!!!!!
		get("/rest/masine/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojeMasine());
		});
		
		post("/rest/masine/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				VirtuelnaMasina m = jsonConvertor.fromJson(req.body(), VirtuelnaMasina.class);
				if (m == null || !m.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				MasinaResult result = Main.masine.dodajMasinu(m);
				if (result != MasinaResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("/rest/masine/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				MasinaChange m = jsonConvertor.fromJson(req.body(), MasinaChange.class);
				if (m == null || !m.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				MasinaResult result = Main.masine.izmeniMasinu(m);
				if (result != MasinaResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("/rest/masine/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				VirtuelnaMasina m = jsonConvertor.fromJson(req.body(), VirtuelnaMasina.class);
				if (m == null || !m.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				MasinaResult result = Main.masine.obrisiMasinu(m);
				if (result != MasinaResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		
		
		
		//i ovo sredi kasnije...
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
			MasinaResult result = Main.masine.promeniStatusMasine(jsonConvertor.fromJson(req.body(), MasinaChange.class));
			if (result != MasinaResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
	}

}
