package model.beans;

import model.CSVData;
import model.ValidData;
import rest.Main;

public class Kategorija implements CSVData, ValidData {
	
	private String ime;
	private int brojJezgara;
	private int RAM;
	private int GPUjezgra;
	
	public Kategorija() {
		super();
	}
	
	public Kategorija(String ime) {
		this();
		this.ime = ime;
	}
	
	public Kategorija(String ime, int brojJezgara, int RAM, int GPUjezgra) {
		this();
		this.ime = ime;
		this.brojJezgara = brojJezgara;
		this.RAM = RAM;
		this.GPUjezgra = GPUjezgra;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Kategorija)) return false;
		return ((Kategorija) obj).ime.equals(this.ime);
	}
	
	public static Kategorija parse(String line) {
		String[] array = line.split(";");
		String ime = array[0].trim();
		int brojJezgara = Integer.parseInt(array[1].trim());
		int RAM = Integer.parseInt(array[2].trim());
		int GPUjezgra = Integer.parseInt(array[3].trim());
		return new Kategorija(ime, brojJezgara, RAM, GPUjezgra);
	}
	
	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.ime + ";" + this.brojJezgara + ";" + this.RAM + ";" + this.GPUjezgra;
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.ime == null || this.ime.equals("")) return false;
		if (this.brojJezgara <= 0) return false;
		if (this.RAM <= 0) return false;
		if (this.GPUjezgra < 0) return false;
		return true;
		
	}
	
	public boolean hasMasina() {
		
		for (VirtuelnaMasina m: Main.masine.getMasine()) {
			if (m.getKategorija().equals(this)) return true;
		}
		return false;
		
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
		for (VirtuelnaMasina m: Main.masine.getMasine()) {
			if (m.getKategorija().equals(this)) {
				m.setBrojJezgara(this.brojJezgara);
				m.setRAM(this.RAM);
				m.setGPUjezgra(this.GPUjezgra);
			}
		}
		
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public int getBrojJezgara() {
		return brojJezgara;
	}
	
	public void setBrojJezgara(int brojJezgara) {
		this.brojJezgara = brojJezgara;
	}
	
	public int getRAM() {
		return RAM;
	}
	
	public void setRAM(int RAM) {
		this.RAM = RAM;
	}
	
	public int getGPUjezgra() {
		return GPUjezgra;
	}
	
	public void setGPUjezgra(int GPUjezgra) {
		this.GPUjezgra = GPUjezgra;
	}

}
