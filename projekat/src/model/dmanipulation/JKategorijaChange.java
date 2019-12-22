package model.dmanipulation;

import model.beans.Kategorija;

public class JKategorijaChange {
	
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
	public JKategorijaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaKategorija);
	}

}
