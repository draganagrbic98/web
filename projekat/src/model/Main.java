package model;

import model.collections.Diskovi;
import model.collections.Kategorije;
import model.collections.Korisnici;
import model.collections.Masine;
import model.collections.Organizacije;

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
	
	public static void storeData() throws Exception {
		kategorije.store();
		masine.store();
		diskovi.store();
		organizacije.store();
		korisnici.store();
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		loadData();
		System.out.println(kategorije);
		System.out.println(masine);
		System.out.println(diskovi);
		System.out.println(organizacije);
		System.out.println(korisnici);
		storeData();

	}

}
