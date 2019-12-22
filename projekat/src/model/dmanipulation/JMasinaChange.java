package model.dmanipulation;

import model.beans.VirtuelnaMasina;

public class JMasinaChange {

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
	public JMasinaChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.novaMasina);
	}
	
}
