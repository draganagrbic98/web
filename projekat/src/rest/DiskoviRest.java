package rest;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Main;
import model.beans.Disk;
import model.beans.Korisnik;
import model.beans.Uloga;
import rest.data.JSONDiskChange;
import rest.data.OpResponse;
import rest.data.OpResult.DiskResult;

public class DiskoviRest implements RestEntity{

	@Override
	public void init() {

		get("rest/diskovi/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojiDiskovi());
		});
		
		post("rest/diskovi/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			DiskResult result = Main.diskovi.dodajDisk(jsonConvertor.fromJson(req.body(), Disk.class));
			if (result != DiskResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/diskovi/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			DiskResult result = Main.diskovi.izmeniDisk(jsonConvertor.fromJson(req.body(), JSONDiskChange.class));
			if (result != DiskResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
		post("rest/diskovi/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			DiskResult result = Main.diskovi.obrisiDisk(jsonConvertor.fromJson(req.body(), Disk.class));
			if (result != DiskResult.OK) res.status(400);
			return jsonConvertor.toJson(new OpResponse(result + ""));
		});
		
	}

}
