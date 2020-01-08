package rest.service;

import static spark.Spark.get;
import static spark.Spark.post;

import model.Uloga;
import model.beans.Disk;
import model.beans.Korisnik;
import rest.Main;
import rest.data.DiskChange;
import rest.data.OpResponse;
import rest.data.OpResult.DiskResult;

public class DiskRest implements RestEntity{

	@Override
	public void init() {

		get("/rest/diskovi/pregled", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			return jsonConvertor.toJson(k.getMojiDiskovi());
		});
		
		post("/rest/diskovi/dodavanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				Disk d = jsonConvertor.fromJson(req.body(), Disk.class);
				if (d == null || !d.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				DiskResult result = Main.diskovi.dodajDisk(d);
				if (result != DiskResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("/rest/diskovi/izmena", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				DiskChange d = jsonConvertor.fromJson(req.body(), DiskChange.class);
				if (d == null || !d.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				DiskResult result = Main.diskovi.izmeniDisk(d);
				if (result != DiskResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
				
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
		post("/rest/diskovi/brisanje", (req, res) -> {
			res.type("application/json");
			Korisnik k = (Korisnik) req.session(true).attribute("korisnik");
			if (k == null || k.getUloga().equals(Uloga.KORISNIK)) {
				res.status(403);
				return jsonConvertor.toJson(new OpResponse("Forbidden"));
			}
			try {
				Disk d = jsonConvertor.fromJson(req.body(), Disk.class);
				if (d == null || !d.validData()) {
					res.status(400);
					return jsonConvertor.toJson(new OpResponse("Invalid data"));
				}
				DiskResult result = Main.diskovi.obrisiDisk(d);
				if (result != DiskResult.OK) res.status(400);
				return jsonConvertor.toJson(new OpResponse(result + ""));
			}
			catch(Exception e) {
				res.status(400);
				return jsonConvertor.toJson(new OpResponse("Invalid data"));
			}
		});
		
	}

}
