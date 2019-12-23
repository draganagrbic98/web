package rest;

import model.beans.Korisnik;

public class JSONKorisnikChange {
	
	private String staroIme;
	private Korisnik noviKorisnik;
	public String getStaroIme() {
		return staroIme;
	}
	public void setStaroIme(String staroIme) {
		this.staroIme = staroIme;
	}
	public Korisnik getNoviKorisnik() {
		return noviKorisnik;
	}
	public void setNoviKorisnik(Korisnik noviKorisnik) {
		this.noviKorisnik = noviKorisnik;
	}
	public JSONKorisnikChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.noviKorisnik);
	}

}
