package model.beans;

import java.util.ArrayList;

import model.Main;

public class VirtuelnaMasina implements CSVData{
	
	private String ime;
	private String organizacija;
	private String kategorija;
	private int brojJezgara;
	private int RAM;
	private int GPUjezgra;
	private ArrayList<Aktivnost> aktivnosti;
	private ArrayList<String> diskovi;
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getOrganizacijaID() {
		return organizacija;
	}
	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}
	public String getKategorijaID() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
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
	public ArrayList<String> getDiskovi() {
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
	public VirtuelnaMasina(String ime, String organizacija, String kategorija) {
		this();
		this.ime = ime;
		this.organizacija = organizacija;
		this.kategorija = kategorija;
		this.brojJezgara = this.getKategorija().getBrojJezgara();
		this.RAM = this.getKategorija().getRAM();
		this.GPUjezgra = this.getKategorija().getGPUjezgra();
		if (this.getOrganizacija() != null)
			this.getOrganizacija().dodajMasinu(this);
	}
	public VirtuelnaMasina(String ime) {
		super();
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
		String kategorija = array[2].trim();
		return new VirtuelnaMasina(ime, organizacija, kategorija);
	}
	
	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.ime + ";" + this.organizacija + ";" + this.kategorija;
	}
	
	public Organizacija getOrganizacija() {
		return Main.organizacije.nadjiOrganizaciju(this.organizacija);
	}
	
	public Kategorija getKategorija() {
		return Main.kategorije.nadjiKategoriju(this.kategorija);
	}
	
	public void dodajAktivnost(Aktivnost a) {
		this.aktivnosti.add(a);
	}
	
	public void dodajDisk(Disk d) {
		this.diskovi.add(d.getIme());
	}

}
