package model.beans;

public class Kategorija implements CSVData{
	
	private String ime;
	private int brojJezgara;
	private int RAM;
	private int GPUjezgra;
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
	public void setRAM(int rAM) {
		RAM = rAM;
	}
	public int getGPUjezgra() {
		return GPUjezgra;
	}
	public void setGPUjezgra(int gPUjezgra) {
		GPUjezgra = gPUjezgra;
	}
	public Kategorija() {
		super();
	}
	public Kategorija(String ime, int brojJezgara, int rAM, int gPUjezgra) {
		this();
		this.ime = ime;
		this.brojJezgara = brojJezgara;
		RAM = rAM;
		GPUjezgra = gPUjezgra;
	}
	public Kategorija(String ime) {
		super();
		this.ime = ime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Ime: %s, broj jezgara: %s, RAM: %s, broj GPU jezgara: %s", this.ime, this.brojJezgara, this.RAM, this.GPUjezgra);
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

}
