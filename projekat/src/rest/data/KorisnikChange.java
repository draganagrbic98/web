package rest.data;

import model.beans.Korisnik;
import model.support.CSVData;

public class KorisnikChange implements CSVData{
	
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
	
	public KorisnikChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.noviKorisnik);
	}
	
	

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.staroIme == null || this.staroIme.equals("")) return false;
		if (this.noviKorisnik == null) return false;
		return this.noviKorisnik.validData();
		
	}

}
