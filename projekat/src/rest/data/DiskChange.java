package rest.data;

import model.beans.Disk;

public class DiskChange {

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

	public DiskChange() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.staroIme, this.noviDisk);
	}
	
	public static boolean validData(DiskChange d) {
		if (d == null) return false;
		if (d.staroIme.equals("")) return false;
		return Disk.validData(d.noviDisk);
	}
	
}
