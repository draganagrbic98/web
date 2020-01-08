package rest.data;

import model.CSVData;
import model.beans.VirtuelnaMasina;

public class MasinaChange implements CSVData{

	private String staroIme;
	private VirtuelnaMasina novaMasina;
	
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
	
	public MasinaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaMasina);
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
		if (this.novaMasina == null) return false;
		return this.novaMasina.validData();
		
	}
	
}
