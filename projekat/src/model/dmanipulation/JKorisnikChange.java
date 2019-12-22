package model.dmanipulation;

import model.beans.Korisnik;

public class JKorisnikChange {

	private String korisnickoIme;
	private Korisnik noviKorisnik;

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public Korisnik getNoviKorisnik() {
		return noviKorisnik;
	}

	public void setNoviKorisnik(Korisnik noviKorisnik) {
		this.noviKorisnik = noviKorisnik;
	}

	public JKorisnikChange() {
		super();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.korisnickoIme, this.noviKorisnik);
	}

}
