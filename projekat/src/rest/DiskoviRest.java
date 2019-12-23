package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Disk;
import model.beans.Korisnik;
import model.beans.TipDiska;
import rest.OpResult.DiskResult;

public class DiskoviRest implements RestEntity{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		get("rest/diskovi/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojiDiskovi());
		});
		
		get("rest/diskovi/unos/tipovi", (req, res) -> {
			res.type("application/json");
			return jsonConvertor.toJson(TipDiska.values());
		});
		
		
		
		post("rest/diskovi/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			DiskResult result = Main.diskovi.obrisiDisk(jsonConvertor.fromJson(req.body(), Disk.class));
			if (result != DiskResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/diskovi/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			DiskResult result = Main.diskovi.izmeniDisk(jsonConvertor.fromJson(req.body(), JSONDiskChange.class));
			if (result != DiskResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/diskovi/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			DiskResult result = Main.diskovi.dodajDisk(jsonConvertor.fromJson(req.body(), Disk.class));
			if (result != DiskResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		get("rest/kategorije/unos/pregled", (req, res) -> {
			res.type("application/json");
			return jsonConvertor.toJson(Main.kategorije.getKategorije());
		});
		
		
	}

}
