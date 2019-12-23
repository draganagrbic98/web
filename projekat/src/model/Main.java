package model;

import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;
import rest.DiskoviRest;
import rest.KategorijeRest;
import rest.KorisniciRest;
import rest.MasineRest;
import rest.OrganizacijeRest;
import rest.UserRest;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

public class Main {

	public static Korisnici korisnici = new Korisnici();
	public static Organizacije organizacije = new Organizacije();
	public static Masine masine = new Masine();
	public static Kategorije kategorije = new Kategorije();
	public static Diskovi diskovi = new Diskovi();


	public static void loadData() throws Exception {
		kategorije.load();
		organizacije.load();
		masine.load();
		diskovi.load();
		korisnici.load();
	}

	
	
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		loadData();
		System.out.println(kategorije);
		System.out.println(masine);
		System.out.println(diskovi);
		System.out.println(organizacije);
		System.out.println(korisnici);


		port(8080);
		staticFiles.externalLocation(new File("static").getCanonicalPath());

		new KategorijeRest().init();
		new DiskoviRest().init();
		new OrganizacijeRest().init();
		new MasineRest().init();
		new KorisniciRest().init();
		new UserRest().init();

		
		
	}

}
