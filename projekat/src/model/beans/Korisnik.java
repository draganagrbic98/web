package model.beans;

import java.util.ArrayList;

import model.Main;

public class Korisnik implements CSVData {

	private User user;
	private String email;
	private String ime;
	private String prezime;
	private Uloga uloga;
	private String organizacija;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getOrganizacijaID() {
		return organizacija;
	}

	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}

	public Korisnik() {
		super();
	}

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime, Uloga uloga,
			String organizacija) {
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

	public Korisnik(String korisnickoIme) {
		super();
		this.user = new User(korisnickoIme);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Korisnik: %s, email: %s, ime: %s, prezime: %s, uloga: %s, organizaicja: %s", this.user,
				this.email, this.ime, this.prezime, this.uloga, this.organizacija);
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
		return new Korisnik(korisnickoIme, lozinka, email, ime, prezime, uloga, organizacija);

	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.user.csvLine() + ";" + this.email + ";" + this.ime + ";" + this.prezime + ";" + this.uloga.ordinal()
				+ ";" + this.organizacija;
	}

	public Organizacija getOrganizacija() {
		return Main.organizacije.nadjiOrganizaciju(this.organizacija);
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
	
	public ArrayList<Organizacija> getMojeOrganizacije(){
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.organizacije.getOrganizacije();
		if (this.uloga.equals(Uloga.KORISNIK)) return null;
		ArrayList<Organizacija> organizacije = new ArrayList<Organizacija>();
		organizacije.add(this.getOrganizacija());
		return organizacije;
	}
	
	public ArrayList<Kategorija> getMojeKategorije(){
		
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.kategorije.getKategorije();
		return null;
		
	}

	public ArrayList<Disk> getMojiDiskovi(){
		if (this.uloga.equals(Uloga.SUPER_ADMIN)) return Main.diskovi.getDiskovi();
		
		ArrayList<Disk> diskovi = new ArrayList<Disk>();
		
		for (VirtuelnaMasina vm : this.getMojeMasine()) {
			diskovi.addAll(Main.diskovi.getDiskoviMasine(vm));
		}
		
		return diskovi;
	}
}
