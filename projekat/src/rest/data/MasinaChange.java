package rest.data;

import model.beans.VirtuelnaMasina;
import model.support.ValidData;

public class MasinaChange implements ValidData {

	private String staroIme;
	private VirtuelnaMasina novaMasina;
	
	public MasinaChange() {
		super();
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.staroIme == null || this.staroIme.equals("")) return false;
		if (this.novaMasina == null) return false;
		return this.novaMasina.validData();
		
	}
	
	public String getStaroIme() {
		return staroIme;
	}
	
	public void setStaroIme(String staroIme) {
		this.staroIme = staroIme;
	}
	
	public VirtuelnaMasina getNovaMasina() {
		return novaMasina;
	}
	
	public void setNovaMasina(VirtuelnaMasina novaMasina) {
		this.novaMasina = novaMasina;
	}
	
}
