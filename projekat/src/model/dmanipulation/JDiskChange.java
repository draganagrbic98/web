package model.dmanipulation;

import model.beans.Disk;

public class JDiskChange {

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

	public JDiskChange() {
		super();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.noviDisk);
	}

}