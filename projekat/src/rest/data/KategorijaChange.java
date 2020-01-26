package rest.data;

import model.beans.Kategorija;
import model.support.CSVData;

public class KategorijaChange implements CSVData{

	private String staroIme;
	private Kategorija novaKategorija;
	
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
	
	public KategorijaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaKategorija);
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
		if (this.novaKategorija == null) return false;
		return this.novaKategorija.validData();
		
	}
	
}
