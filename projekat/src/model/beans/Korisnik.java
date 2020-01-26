package model.beans;

import java.util.ArrayList;
import java.util.HashMap;

import model.support.CSVData;
import model.support.ReferenceManager;
import model.support.Uloga;
import model.support.ValidData;
import rest.Main;
import rest.data.RacunZahtev;

public class Korisnik implements CSVData, ValidData, ReferenceManager {

	private User user;
	private String email;
	private String ime;
	private String prezime;
	private Uloga uloga;
	private String organizacija;

	public Korisnik() {
		super();
	}
	
	public Korisnik(String korisnickoIme) {
		super();
		this.user = new User(korisnickoIme);
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime, Uloga uloga, String organizacija) {
		this();
		this.user = new User(korisnickoIme, lozinka);
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.uloga = uloga;
		this.organizacija = organizacija;
		if (this.getOrganizacija() != null)
			this.getOrganizacija().dodajKorisnika(this);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Korisnik))
			return false;
		return ((Korisnik) obj).user.equals(this.user) || ((Korisnik) obj).email.equals(this.email);
	}

	public static Korisnik parse(String line) {
		String[] array = line.split(";");
		String korisnickoIme = array[0].trim();
		String lozinka = array[1].trim();
		String email = array[2].trim();
		String ime = array[3].trim();
		String prezime = array[4].trim();
		Uloga uloga = Uloga.values()[(Integer.parseInt(array[5].trim()))];
		String organizacija = array[6].trim();
		if (organizacija.equals("null")) organizacija = null;
		return new Korisnik(korisnickoIme, lozinka, email, ime, prezime, uloga, organizacija);
	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.user.csvLine() + ";" + this.email + ";" + this.ime + ";" + this.prezime + ";" + this.uloga.ordinal() + ";" + this.organizacija;
	}
	
	@Override
	public void updateReference(String className, String oldId, String newId) {
		// TODO Auto-generated method stub
		if (this.organizacija != null && this.organizacija.equals(oldId))
			this.organizacija = newId;
	}

	@Override
	public void notifyUpdate(String newId) {
		// TODO Auto-generated method stub
		for (Organizacija o: Main.organizacije.getOrganizacije())
			o.updateReference(this.getClass().getSimpleName(), this.getKorisnickoIme(), newId);

	}

	@Override
	public void removeReference(String className, String id) {
		// TODO Auto-generated method stub
		if (this.organizacija != null && this.organizacija.equals(id))
			this.organizacija = null;
	}

	@Override
	public void notifyRemoval() {
		// TODO Auto-generated method stub
		for (Organizacija o: Main.organizacije.getOrganizacije())
			o.removeReference(this.getClass().getSimpleName(), this.ime);
	}
	
	

	public Racun izracunajRacun(RacunZahtev racunZahtev) {		
		HashMap<String, Double> racuniMasine = new HashMap<String, Double>();
		HashMap<String, Double> racuniDiskovi = new HashMap<String, Double>();
		double ukupniRacun = 0;
		
		for (VirtuelnaMasina vm: getMojeMasine()) {
			double racunMasine = vm.izracunajRacun(racunZahtev);
			racuniMasine.put(vm.getIme(), Math.round(racunMasine * 100.0) / 100.0);
			ukupniRacun += racunMasine;
		}
		
		for (Disk d: getMojiDiskovi()) {
			double racunDiska = d.izracunajRacun(racunZahtev);
			racuniDiskovi.put(d.getIme(), Math.round(racunDiska * 100.0) / 100.0);
			ukupniRacun += racunDiska;
		}
		
		return new Racun(racuniMasine, racuniDiskovi, Math.round(ukupniRacun * 100.0) / 100.0);
	}
	
	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.email == null || this.email.equals("")) return false;
		if (this.ime == null || this.ime.equals("")) return false;
		if (this.prezime == null || this.prezime.equals("")) return false;
		if (this.uloga == null) return false;
		if (this.uloga != Uloga.SUPER_ADMIN && (this.organizacija == null || this.organizacija.equals(""))) return false;
		return this.user.validData();
		
	}
	
	public String getKorisnickoIme() {
		return this.user.getKorisnickoIme();
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.notifyUpdate(korisnickoIme);
		this.user.setKorisnickoIme(korisnickoIme);
	}
	public String getLozinka() {
		return this.user.getLozinka();
	}
	public void setLozinka(String lozinka) {
		this.user.setLozinka(lozinka);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	public Organizacija getOrganizacija() {
		return Main.organizacije.nadjiOrganizaciju(this.organizacija);
	}
	public String getOrganizacijaID() {
		return organizacija;
	}
	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}
	
	public ArrayList<Organizacija> getMojeOrganizacije(){
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.organizacije.getOrganizacije();
		ArrayList<Organizacija> organizacije = new ArrayList<Organizacija>();
		organizacije.add(this.getOrganizacija());
		return organizacije;
	}

	public ArrayList<VirtuelnaMasina> getMojeMasine(){
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.masine.getMasine();
		ArrayList<VirtuelnaMasina> masine = new ArrayList<VirtuelnaMasina>();
		for (VirtuelnaMasina m: Main.masine.getMasine()) {
			if (m.getOrganizacijaID().equals(this.organizacija))
				masine.add(m);
		}
		return masine;
	}
	
	public ArrayList<Disk> getMojiDiskovi(){
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.diskovi.getDiskovi();
		ArrayList<Disk> diskovi = new ArrayList<Disk>();
		for (Disk d: Main.diskovi.getDiskovi()) {
			if (d.getOrganizacijaID().equals(this.organizacija))
				diskovi.add(d);
		}
		return diskovi;
	}
	
	public ArrayList<Korisnik> getMojiKorisnici(){
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.korisnici.getKorisnici();
		else if (this.uloga.equals(Uloga.ADMIN)) {
			ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
			for (Korisnik k: Main.korisnici.getKorisnici()) {
				if (k.getOrganizacijaID() != null && k.getOrganizacijaID().equals(this.organizacija))
					korisnici.add(k);
			}
			return korisnici;
		}
		ArrayList<Korisnik> lista = new ArrayList<Korisnik>();
		lista.add(this);
		return lista;
	}
	
}
