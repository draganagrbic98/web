package rest;

import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;
import rest.service.DataRest;
import rest.service.DiskRest;
import rest.service.KategorijaRest;
import rest.service.KorisnikRest;
import rest.service.MasinaRest;
import rest.service.OrganizacijaRest;
import rest.service.UserRest;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

public class Main {

	public static Kategorije kategorije = new Kategorije();
	public static Organizacije organizacije = new Organizacije();
	public static Masine masine = new Masine();
	public static Diskovi diskovi = new Diskovi();
	public static Korisnici korisnici = new Korisnici();

	public static void loadData() throws Exception {
		
		kategorije.load();
		organizacije.load();
		masine.load();
		diskovi.load();
		korisnici.load();
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		File file = new File("files");
		if (!file.exists())
			file.mkdir();
		
		loadData();
		korisnici.addSuperAdmin();
		port(8080);
		staticFiles.externalLocation(new File("static").getCanonicalPath());

		new KategorijaRest().init();
		new OrganizacijaRest().init();
		new MasinaRest().init();
		new DiskRest().init();
		new KorisnikRest().init();
		new UserRest().init();
		new DataRest().init();

		
	}

}
