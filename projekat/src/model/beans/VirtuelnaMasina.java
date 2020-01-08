package model.beans;

import java.util.ArrayList;
import java.util.Date;

import model.CSVData;
import model.ReferenceManager;
import model.StatusMasine;
import rest.Main;
import rest.data.JSONRacunZahtev;

public class VirtuelnaMasina implements CSVData, ReferenceManager {
	
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
		this.notifyUpdate(ime);
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

	public void setRAM(int RAM) {
		this.RAM = RAM;
	}

	public int getGPUjezgra() {
		return GPUjezgra;
	}

	public void setGPUjezgra(int GPUjezgra) {
		this.GPUjezgra = GPUjezgra;
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
		String suma = String.format(
				"Ime: %s, organizacija: %s, kategorija: %s, broj jezgara: %s, RAM: %s, broj GPU jezgara: %s\n",
				this.ime, this.organizacija, this.kategorija, this.brojJezgara, this.RAM, this.GPUjezgra);
		suma += "AKTIVNOSTI: \n";
		for (Aktivnost a : this.aktivnosti)
			suma += a + "\n";
		suma += "DISKOVI: \n";
		for (String s : this.diskovi)
			suma += s + "\n";
		return suma;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof VirtuelnaMasina))
			return false;
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

	@Override
	public void updateReference(String className, String oldId, String newId) {
		// TODO Auto-generated method stub
		if (className.equals("Disk")) {
			int index = this.diskovi.indexOf(oldId);
			if (index != -1)
				this.diskovi.set(index, newId);
		} else {
			if (this.organizacija != null && this.organizacija.equals(oldId))
				this.organizacija = newId;
		}
	}

	@Override
	public void notifyUpdate(String newId) {
		// TODO Auto-generated method stub
		for (Disk d : Main.diskovi.getDiskovi())
			d.updateReference(this.getClass().getSimpleName(), this.ime, newId);
		for (Organizacija o : Main.organizacije.getOrganizacije())
			o.updateReference(this.getClass().getSimpleName(), this.ime, newId);

	}

	@Override
	public void removeReference(String className, String id) {
		// TODO Auto-generated method stub
		if (className.equals("Disk")) {
			int index = this.diskovi.indexOf(id);
			if (index != -1)
				this.diskovi.remove(index);
		} else {
			if (this.organizacija != null && this.organizacija.equals(id))
				this.organizacija = null;
		}
	}

	@Override
	public void notifyRemoval() {
		// TODO Auto-generated method stub
		for (Disk d : Main.diskovi.getDiskovi())
			d.removeReference(this.getClass().getSimpleName(), this.ime);
		for (Organizacija o : Main.organizacije.getOrganizacije())
			o.removeReference(this.getClass().getSimpleName(), this.ime);
	}

	private Organizacija getOrganizacija() {
		return Main.organizacije.nadjiOrganizaciju(this.organizacija);
	}

	public void dodajAktivnost(Aktivnost a) {
		this.aktivnosti.add(a);
	}

	public void dodajDisk(Disk d) {
		this.diskovi.add(d.getIme());
	}

	public ArrayList<Disk> getDiskovi() {

		ArrayList<Disk> diskovi = new ArrayList<Disk>();
		for (String d : this.diskovi)
			diskovi.add(Main.diskovi.nadjiDisk(d));
		return diskovi;

	}

	public StatusMasine upaljena() {
		if (this.aktivnosti.isEmpty())
			return StatusMasine.UGASENA;

		return this.aktivnosti.get(this.aktivnosti.size() - 1).getStatus();
	}

	public void initAktivnost() {
		this.aktivnosti.add(new Aktivnost(new Date(), null, StatusMasine.UGASENA));
	}

	public double izracunajRacun(JSONRacunZahtev racunZahtev) {
		double racunMasine = 0;

		double jedinicnaCena = izracunajJedinicnuCenu();

		double pocetni = racunZahtev.getPocetniDatum() / 1000.0 / 60.0;
		double krajnji = racunZahtev.getKrajnjiDatum() / 1000.0 / 60.0;

		for (Aktivnost a : aktivnosti) {
			if (a.getDatumGasenja() != null) {
				double datumPaljenja = a.getDatumPaljenja().getTime() / 1000.0 / 60.0;
				double datumGasenja = a.getDatumGasenja().getTime() / 1000.0 / 60.0;

				if (datumGasenja - datumPaljenja < 60)
					continue;

				if (datumGasenja <= pocetni || datumPaljenja >= krajnji)
					continue;

				if (datumGasenja <= krajnji && datumPaljenja >= pocetni)
					racunMasine += jedinicnaCena * ((datumGasenja - datumPaljenja) / 60.0 / 24.0 / 30.0);
				else if (datumGasenja > krajnji && datumPaljenja > pocetni)
					racunMasine += jedinicnaCena * ((krajnji - datumPaljenja) / 60.0 / 24.0 / 30.0);
				else if (datumGasenja < krajnji && datumPaljenja < pocetni)
					racunMasine += jedinicnaCena * ((datumGasenja - pocetni) / 60.0 / 24.0 / 30.0);

			} else {
				double datumPaljenja = a.getDatumPaljenja().getTime() / 1000.0 / 60.0;

				if (krajnji - datumPaljenja < 60)
					continue;

				if (datumPaljenja >= krajnji)
					continue;

				if (datumPaljenja > pocetni)
					racunMasine += jedinicnaCena * ((krajnji - datumPaljenja) / 60.0 / 24.0 / 30.0);
				else
					racunMasine += jedinicnaCena * ((krajnji - pocetni) / 60.0 / 24.0 / 30.0);

			}			
		}

		return racunMasine;
	}

	private double izracunajJedinicnuCenu() {
		return 25 * brojJezgara + 15 * RAM + 1 * GPUjezgra;
	}



	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.ime == null || this.ime.equals("")) return false;
		if (this.organizacija == null || this.organizacija.equals("")) return false;
		if (this.kategorija == null) return false;
		if (this.brojJezgara <= 0) return false;
		if (this.RAM <= 0) return false;
		if (this.GPUjezgra <= 0) return false;
		if (this.aktivnosti == null) return false;
		if (this.diskovi == null) return false;
		return this.kategorija.validData();
		
	}

}
