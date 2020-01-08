package rest.data;

import model.beans.Kategorija;

public class KategorijaChange {

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
	
	public static boolean validData(KategorijaChange k) {
		
		if (k == null) return false;
		if (k.staroIme.equals("")) return false;
		return Kategorija.validData(k.novaKategorija);
		
	}
	
}
