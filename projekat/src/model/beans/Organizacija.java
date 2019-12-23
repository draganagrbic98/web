package model.beans;

import java.util.ArrayList;

import model.Main;
import model.collections.FileNames;

public class Organizacija implements CSVData, ReferenceManager {

	private String ime;
	private String opis;
	private String logo;
	private ArrayList<String> korisnici;
	private ArrayList<String> masine;

	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.notifyUpdate(ime);
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
	
	public Organizacija() {
		super();
		this.korisnici = new ArrayList<String>();
		this.masine = new ArrayList<String>();
	}

	public Organizacija(String ime) {
		this();
		this.ime = ime;
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
		if (logo == "null") logo = FileNames.DEFAULT_LOGO;
		return new Organizacija(ime, opis, logo);
	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.ime + ";" + this.opis + ";" + this.logo;
	}

	public void dodajKorisnika(Korisnik k) {
		this.korisnici.add(k.getKorisnickoIme());
	}

	public void dodajMasinu(VirtuelnaMasina m) {
		this.masine.add(m.getIme());
	}
	
	

	@Override
	public void updateReference(String className, String oldId, String newId) {
		// TODO Auto-generated method stub
		
		if (className.equals("Korisnik")) {
			int index = this.korisnici.indexOf(oldId);
			if (index != -1) this.korisnici.set(index, newId);
		}
		else {
			int index = this.masine.indexOf(oldId);
			if (index != -1) this.masine.set(index, newId);
		}
		
	}

	@Override
	public void notifyUpdate(String newId) {
		// TODO Auto-generated method stub
		for (Korisnik k: Main.korisnici.getKorisnici())
			k.updateReference(this.getClass().getSimpleName(), this.ime, newId);
		for (VirtuelnaMasina m: Main.masine.getMasine())
			m.updateOrganizacija(this.ime, newId);

	}

	@Override
	public void removeReference(String className, String id) {
		// TODO Auto-generated method stub
		if (className.equals("Korisnik")) {
			int index = this.korisnici.indexOf(id);
			if (index != -1) this.korisnici.remove(index);
		}
		else {
			int index = this.masine.indexOf(id);
			if (index != -1) this.masine.remove(index);
		}
	}

	@Override
	public void notifyRemoval() {
		// TODO Auto-generated method stub
		for (Korisnik k: Main.korisnici.getKorisnici())
			k.removeReference(this.getClass().getSimpleName(), this.ime);
		for (VirtuelnaMasina m: Main.masine.getMasine())
			m.removeReference(this.getClass().getSimpleName(), this.ime);
	}
	

}
