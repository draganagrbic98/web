package model.beans;

import model.Main;

public class Disk implements CSVData, ReferenceManager {

	private String ime;
	private TipDiska tip;
	private int kapacitet;
	private String masina;

	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.doUpdate(ime);
		this.ime = ime;
	}
	
	public TipDiska getTip() {
		return tip;
	}
	
	public void setTip(TipDiska tip) {
		this.tip = tip;
	}
	
	public int getKapacitet() {
		return kapacitet;
	}
	
	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}
	
	public String getMasinaID() {
		return masina;
	}
	
	public void setMasina(String masina) {
		this.masina = masina;
	}

	public Disk() {
		super();
	}
	
	public Disk(String ime) {
		this();
		this.ime = ime;
	}
	
	public Disk(String ime, TipDiska tip, int kapacitet, String masina) {
		this();
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.masina = masina;
		if (this.getMasina() != null)
			this.getMasina().dodajDisk(this);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Ime: %s, tip: %s, kapacitet: %s, masina: %s", this.ime, this.tip, this.kapacitet, this.masina);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Disk)) return false;
		return ((Disk) obj).ime.equals(this.ime);
	}

	public static Disk parse(String line) {
		String[] array = line.split(";");
		String ime = array[0].trim();
		TipDiska tip = TipDiska.values()[Integer.parseInt(array[1].trim())];
		int kapacitet = Integer.parseInt(array[2].trim());
		String masina = array[3].trim();
		return new Disk(ime, tip, kapacitet, masina);
	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.ime + ";" + this.tip.ordinal() + ";" + this.kapacitet + ";" + this.masina;
	}

	public VirtuelnaMasina getMasina() {
		return Main.masine.nadjiMasinu(this.masina);
	}
	
	

	@Override
	public void updateReference(String className, String oldId, String newId) {
		// TODO Auto-generated method stub
		if (this.masina.equals(oldId))
			this.masina = newId;
	}

	@Override
	public void doUpdate(String newId) {
		// TODO Auto-generated method stub
		for (VirtuelnaMasina m: Main.masine.getMasine())
			m.updateReference(this.getClass().getSimpleName(), this.ime, newId);

	}

	@Override
	public void removeReference(String className, String id) {
		// TODO Auto-generated method stub
		if (this.masina.equals(id))
			this.masina = null;
	}

	@Override
	public void notifyRemoval() {
		// TODO Auto-generated method stub
		for (VirtuelnaMasina m: Main.masine.getMasine())
			m.removeReference(this.getClass().getSimpleName(), this.ime);
	}

	

}
