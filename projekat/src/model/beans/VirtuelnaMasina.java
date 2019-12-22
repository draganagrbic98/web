package model.beans;

import java.util.ArrayList;

import model.Main;

public class VirtuelnaMasina implements CSVData{
	
	private String ime;
	private String organizacija;
	private Kategorija kategorija;
	private int brojJezgara;
	private int RAM;
	private int GPUjezgra;
	private ArrayList<Aktivnost> aktivnosti;
	private ArrayList<String> diskovi;
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		for (Disk d: Main.diskovi.getDiskovi()) {
			if (d.getMasinaID().equals(this.ime))
				d.setMasina(ime);
		}
		this.getOrganizacija().updateMasina(this.ime, ime);
		this.ime = ime;
	}
	public String getOrganizacijaID() {
		return organizacija;
	}
	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}
	public Kategorija getKategorija() {
		return kategorija;
	}
	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
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
	public ArrayList<Aktivnost> getAktivnosti() {
		return aktivnosti;
	}
	public void setAktivnosti(ArrayList<Aktivnost> aktivnosti) {
		this.aktivnosti = aktivnosti;
	}
	public ArrayList<String> getDiskoviID() {
		return diskovi;
	}
	public void setDiskovi(ArrayList<String> diskovi) {
		this.diskovi = diskovi;
	}
	
	public VirtuelnaMasina() {
		super();
		this.aktivnosti = new ArrayList<Aktivnost>();
		this.diskovi = new ArrayList<String>();
	}
	public VirtuelnaMasina(String ime, String organizacija, Kategorija kategorija) {
		this();
		this.ime = ime;
		this.organizacija = organizacija;
		this.kategorija = kategorija;
		this.brojJezgara = this.kategorija.getBrojJezgara();
		this.RAM = this.kategorija.getRAM();
		this.GPUjezgra = this.kategorija.getGPUjezgra();
		if (this.getOrganizacija() != null)
			this.getOrganizacija().dodajMasinu(this);
	}
	public VirtuelnaMasina(String ime) {
		this();
		this.ime = ime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = String.format("Ime: %s, organizacija: %s, kategorija: %s, broj jezgara: %s, RAM: %s, broj GPU jezgara: %s\n", this.ime, this.organizacija, this.kategorija, this.brojJezgara, this.RAM, this.GPUjezgra);
		suma += "AKTIVNOSTI: \n";
		for (Aktivnost a: this.aktivnosti)
			suma += a + "\n";
		suma += "DISKOVI: \n";
		for (String s: this.diskovi)
			suma += s + "\n";
		return suma;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof VirtuelnaMasina)) return false;
		return ((VirtuelnaMasina) obj).ime.equals(this.ime);
	}
	
	public static VirtuelnaMasina parse(String line) {
		String[] array = line.split(";");
		String ime = array[0].trim();
		String organizacija = array[1].trim();
		Kategorija kategorija = Main.kategorije.nadjiKategoriju(array[2].trim());
		return new VirtuelnaMasina(ime, organizacija, kategorija);
	}
	
	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.ime + ";" + this.organizacija + ";" + this.kategorija.getIme();
	}
	
	public Organizacija getOrganizacija() {
		return Main.organizacije.nadjiOrganizaciju(this.organizacija);
	}
	
	public void dodajAktivnost(Aktivnost a) {
		this.aktivnosti.add(a);
	}
	
	public void dodajDisk(Disk d) {
		this.diskovi.add(d.getIme());
	}
	public void removeDisk(Disk d) {
		
		this.diskovi.remove(d.getIme());
		
	}
	public void updateDisk(String oldIme, String newIme) {
		// TODO Auto-generated method stub
		
		int index = this.diskovi.indexOf(oldIme);
		this.diskovi.set(index, newIme);
		
	}
	public ArrayList<Disk> getDiskovi(){
	
		ArrayList<Disk> diskovi = new ArrayList<Disk>();
		for (String d: this.diskovi)
			diskovi.add(Main.diskovi.nadjiDisk(d));
		return diskovi;
		
	}
	
	

}
