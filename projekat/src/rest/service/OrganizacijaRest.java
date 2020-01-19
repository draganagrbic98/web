package rest.service;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Uloga;
import model.beans.Korisnik;
import model.beans.Organizacija;
import rest.data.OrganizacijaChange;
import rest.Main;
import rest.RestEntity;
import rest.data.OpResponse;
import rest.data.OpResult.OrganizacijaResponse;

public class OrganizacijaRest implements RestEntity{

	@Override
	public void init() {

		get("/rest/organizacije/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			else if (k.getUloga().equals(Uloga.ADMIN)) {
				return jsonConvertor.toJson(k.getMojeOrganizacije());
			}
			return jsonConvertor.toJson(k.getMojeOrganizacije());
		});
		
		post("/rest/organizacije/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || !k.getUloga().equals(Uloga.SUPER_ADMIN)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				Organizacija o = jsonConvertor.fromJson(req.body(), Organizacija.class);
				if (o == null || !o.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				OrganizacijaResponse result = Main.organizacije.dodajOrganizaciju(o);
				if (result != OrganizacijaResponse.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}			
		});

		post("/rest/organizacije/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				OrganizacijaChange o = jsonConvertor.fromJson(req.body(), OrganizacijaChange.class);
				if (o == null || !o.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				OrganizacijaResponse result = Main.organizacije.izmeniOrganizaciju(o);
				if (result != OrganizacijaResponse.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));	
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}			
		});
		
	}

}
