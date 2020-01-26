package rest.data;

import model.beans.Kategorija;
import model.support.ValidData;

public class KategorijaChange implements ValidData {

	private String staroIme;
	private Kategorija novaKategorija;
	
	public KategorijaChange() {
		super();
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.staroIme == null || this.staroIme.equals("")) return false;
		if (this.novaKategorija == null) return false;
		return this.novaKategorija.validData();
		
	}
	
	public String getStaroIme() {
		return staroIme;
	}
	
	public void setStaroIme(String staroIme) {
		this.staroIme = staroIme;
	}
	
	public Kategorija getNovaKategorija() {
		return novaKategorija;
	}
	
	public void setNovaKategorija(Kategorija novaKategorija) {
		this.novaKategorija = novaKategorija;
	}
	
}
