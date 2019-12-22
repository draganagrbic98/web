package rest;

import model.beans.Disk;

public class JSONDiskChange {

	private String staroIme;
	private Disk noviDisk;

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

	public JSONDiskChange() {
		super();
	}
	
}
