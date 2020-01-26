package rest.data;

import model.beans.Korisnik;
import model.support.ValidData;

public class KorisnikChange implements ValidData {
	
	private String staroIme;
	private Korisnik noviKorisnik;
	
	public KorisnikChange() {
		super();
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.staroIme == null || this.staroIme.equals("")) return false;
		if (this.noviKorisnik == null) return false;
		return this.noviKorisnik.validData();
		
	}
	
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

}
