package model.beans;

import java.util.ArrayList;

import model.Main;

public class Organizacija implements CSVData {

	private String ime;
	private String opis;
	private String logo;
	private ArrayList<String> korisnici;
	private ArrayList<String> masine;

	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		for (Korisnik k: Main.korisnici.getKorisnici()) {
			if (k.getOrganizacijaID().equals(this.ime))
				k.setOrganizacija(ime);
		}
		for (VirtuelnaMasina m: Main.masine.getMasine()) {
			if (m.getOrganizacijaID().equals(this.ime))
				m.setOrganizacija(ime);
		}
		this.ime = ime;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public ArrayList<String> getKorisnici() {
		return korisnici;
	}
	public void setKorisnici(ArrayList<String> korisnici) {
		this.korisnici = korisnici;
	}
	public ArrayList<String> getMasine() {
		return masine;
	}
	public void setMasine(ArrayList<String> masine) {
		this.masine = masine;
	}
	
	public Organizacija(String ime) {
		this();
		this.ime = ime;
	}
	public Organizacija() {
		super();
		this.korisnici = new ArrayList<String>();
		this.masine = new ArrayList<String>();
	}
	public Organizacija(String ime, String opis, String logo) {
		this();
		this.ime = ime;
		this.opis = opis;
		this.logo = logo;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = String.format("Ime: %s, opis: %s, uloga: %s\n", this.ime, this.opis, this.logo);
		suma += "KORISNICI: \n";
		for (String k : this.korisnici)
			suma += k + "\n";
		suma += "MASINE: \n";
		for (String m : this.masine)
			suma += m + "\n";
		return suma;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof Organizacija))
			return false;
		return ((Organizacija) obj).ime.equals(this.ime);
	}

	public static Organizacija parse(String line) {
		String[] array = line.split(";");
		String ime = array[0].trim();
		String opis = array[1].trim();
		String logo = array[2].trim();
		if (logo == "null") logo = "slike/default.jpg";
		return new Organizacija(ime, opis, logo);
	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.ime + ";" + this.opis + ";" + this.logo;
	}

	public void dodajKorisnika(Korisnik k) {
		this.korisnici.add(k.getUser().getKorisnickoIme());
	}

	public void dodajMasinu(VirtuelnaMasina m) {
		this.masine.add(m.getIme());
	}
	
	public void obrisiMasinu(VirtuelnaMasina m) {
		int index = this.masine.indexOf(m.getIme());
		if (index != -1) this.masine.remove(index);
	}
	
	public void updateMasina(String oldIme, String newIme) {
		// TODO Auto-generated method stub
		int index = this.masine.indexOf(oldIme);
		this.masine.set(index, newIme);
	}

}
