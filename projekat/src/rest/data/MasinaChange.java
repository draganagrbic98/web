package rest.data;

import model.beans.VirtuelnaMasina;

public class MasinaChange {

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
	
	public static boolean validData(MasinaChange m) {
		if (m == null) return false;
		if (m.staroIme.equals("")) return false;
		return VirtuelnaMasina.validData(m.novaMasina);
	}
	
}
