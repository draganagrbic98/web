package rest;

import model.beans.Kategorija;

public class JSONKategorijaChange {

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
	public JSONKategorijaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaKategorija);
	}
	
}
