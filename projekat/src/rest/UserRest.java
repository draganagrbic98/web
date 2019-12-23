package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Korisnik;
import model.beans.User;
import rest.data.JSONKorisnikChange;
import rest.data.OpResponse;
import rest.data.OpResult.KorisnikResult;
import spark.Session;

public class UserRest implements RestEntity{

	@Override
	public void init() {
		
		post("rest/user/login", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			if (ss.attribute("korisnik") != null) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Vec ste prijavljeni. Prvo se odlogujte. "));
			}
			User u = jsonConvertor.fromJson(req.body(), User.class);
			Korisnik k = Main.korisnici.login(u);
			if (k == null) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Unet korisnik ne postoji. "));
			}
			ss.attribute("korisnik", k);
			return jsonConvertor.toJson(k);
		});
		
		get("rest/user/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			ss.invalidate();
			return jsonConvertor.toJson(new OpResponse("OK"));
		});
		
		get("rest/user/profil", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			if (ss.attribute("korisnik") == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson((Korisnik) ss.attribute("korisnik"));
		});
		
		post("rest/user/izmena", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			if (ss.attribute("korisnik") == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			Korisnik k = (Korisnik) ss.attribute("korisnik");
			KorisnikResult result = Main.korisnici.izmeniKorisnika(jsonConvertor.fromJson(req.body(), JSONKorisnikChange.class), k);
			if (result != KorisnikResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		get("rest/user/uloga", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			if (ss.attribute("korisnik") == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(new OpResponse((((Korisnik) ss.attribute("korisnik")).getUloga() + "")));
		});
		
	}

}
