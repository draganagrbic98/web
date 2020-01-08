package rest.data;

import model.CSVData;
import model.beans.Disk;

public class DiskChange implements CSVData{

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

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.staroIme == null || this.staroIme.equals("")) return false;
		if (this.noviDisk == null) return false;
		return this.noviDisk.validData();
		
	}
	
}
