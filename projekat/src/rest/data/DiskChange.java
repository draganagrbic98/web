package rest.data;

import model.beans.Disk;
import model.support.ValidData;

public class DiskChange implements ValidData {

	private String staroIme;
	private Disk noviDisk;
	private String staraMasina;

	public DiskChange() {
		super();
	}

	@Override
	public boolean validData() {
		if (this.staroIme == null || this.staroIme.equals("")) return false;
		if (this.noviDisk == null) return false;
		return this.noviDisk.validData();
	}
	
	public String getStaroIme() {
		return staroIme;
	}

	public void setStaroIme(String staroIme) {
		this.staroIme = staroIme;
	}

	public Disk getNoviDisk() {
		return noviDisk;
	}

	public void setNoviDisk(Disk noviDisk) {
		this.noviDisk = noviDisk;
	}

	public String getStaraMasina() {
		return staraMasina;
	}

	public void setStaraMasina(String staraMasina) {
		this.staraMasina = staraMasina;
	}
	
}
