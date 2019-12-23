package rest.data;

import model.beans.Organizacija;

public class JSONOrganizacijaChange {

	private String staroIme;
	private Organizacija novaOrganizacija;
	public String getStaroIme() {
		return staroIme;
	}
	public void setStaroIme(String staroIme) {
		this.staroIme = staroIme;
	}
	public Organizacija getNovaOrganizacija() {
		return novaOrganizacija;
	}
	public void setNovaOrganizacija(Organizacija novaOrganizacija) {
		this.novaOrganizacija = novaOrganizacija;
	}
	public JSONOrganizacijaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaOrganizacija);
	}
	
}
