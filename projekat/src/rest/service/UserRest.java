package rest.service;

import static spark.Spark.get;
import static spark.Spark.post;

import model.beans.Korisnik;
import model.beans.User;
import rest.Main;
import rest.RestEntity;
import rest.data.OpResponse;

public class UserRest implements RestEntity {

	@Override
	public void init() {
		
		post("/rest/user/login", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k != null) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Vec ste prijavljeni. Prvo se odlogujte. "));
			}
			try {
				User u = jsonConvertor.fromJson(req.body(), User.class);
				if (u == null || !u.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Bad Request"));
				}
				k = Main.korisnici.login(u);
				if (k == null) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Unet korisnik ne postoji. "));
				}
				req.session(true).attribute("korisnik", k);
				return jsonConvertor.toJson(k);
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Bad Request"));
			}
		});
		
		get("rest/user/logout", (req, res) -> {
			res.type("application/json");
			req.session(true).invalidate();
			return jsonConvertor.toJson(new OpResponse("OK"));
		});
		
		get("rest/user/profil", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k);
		});
		
		
		
		get("rest/user/uloga", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(new OpResponse(k.getUloga() + ""));
		});
		
	}

}
