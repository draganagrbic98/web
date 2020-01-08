package rest.data;

import model.beans.Korisnik;

public class KorisnikChange {
	
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
	
	public static boolean validData(KorisnikChange k) {
		if (k == null) return false;
		if (k.staroIme.equals("")) return false;
		return Korisnik.validData(k.noviKorisnik);
	}

}
