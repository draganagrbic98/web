package rest;

import static spark.Spark.post;

import model.Main;
import model.beans.Korisnik;
import model.beans.User;
import rest.data.OpResponse;
import spark.Session;

public class UserRest implements RestEntity{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		
		
		post("rest/user/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			ss.invalidate();
			return jsonConvertor.toJson(new OpResponse("OK"));
		});
		
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
		
	}

}
