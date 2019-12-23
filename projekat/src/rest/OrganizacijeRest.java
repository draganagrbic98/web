package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Korisnik;
import model.beans.Organizacija;
import model.beans.Uloga;
import rest.data.JSONOrganizacijaChange;
import rest.data.OpResponse;
import rest.data.OpResult.OrganizacijaResponse;

public class OrganizacijeRest implements RestEntity{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		post("rest/organizacije/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			OrganizacijaResponse result = Main.organizacije.izmeniOrganizaciju(jsonConvertor.fromJson(req.body(), JSONOrganizacijaChange.class));
			if (result != OrganizacijaResponse.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
			
		});
		
		post("rest/organizacije/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			OrganizacijaResponse result = Main.organizacije.dodajOrganizaciju(jsonConvertor.fromJson(req.body(), Organizacija.class));
			if (result != OrganizacijaResponse.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
			
		});
		
		get("rest/organizacije/pregled", (req, res) -> {
			
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojeOrganizacije());
			
		});
	}

}
