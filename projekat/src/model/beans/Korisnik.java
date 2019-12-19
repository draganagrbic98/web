package model.beans;

import model.Main;

public class Korisnik implements CSVData{
	
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private String ime;
	private String prezime;
	private Uloga uloga;
	private String organizacija;
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
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
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
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
		this.korisnickoIme = korisnickoIme;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Korisnicko ime: %s, lozinka: %s, email: %s, ime: %s, prezime: %s, uloga: %s, organizaicja: %s", this.korisnickoIme, this.lozinka, this.email, this.ime, this.prezime, this.uloga, this.organizacija);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Korisnik)) return false;
		return ((Korisnik) obj).korisnickoIme.equals(this.korisnickoIme) || ((Korisnik) obj).email.equals(this.email);
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
		return this.korisnickoIme + ";" + this.lozinka + ";" + this.email + ";" + this.ime + ";" + this.prezime + ";" + this.uloga.ordinal() + ";" + this.organizacija;
	}
	
	public Organizacija getOrganizacija() {
		return Main.organizacije.nadjiOrganizaciju(this.organizacija);
	}

}
