package rest.data;

import model.CSVData;
import model.beans.Organizacija;

public class OrganizacijaChange implements CSVData{

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
	
	public OrganizacijaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaOrganizacija);
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
		if (this.novaOrganizacija == null) return false;
		return this.novaOrganizacija.validData();
		
	}
	
}
