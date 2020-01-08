package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.beans.Korisnik;
import model.beans.Uloga;
import model.beans.User;
import rest.data.KorisnikChange;
import rest.data.OpResult.KorisnikResult;

public class Korisnici implements LoadStoreData{
		
	private ArrayList<Korisnik> korisnici;

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public Korisnici() {
		super();
		this.korisnici = new ArrayList<Korisnik>();
	}
	
	public Korisnik nadjiKorisnika(String korisnickoIme) {
		int index = this.korisnici.indexOf(new Korisnik(korisnickoIme));
		if (index == -1) return null;
		return this.korisnici.get(index);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = "KORISNICI: \n";
		for (Korisnik k: this.korisnici)
			suma += k + "\n";
		return suma;
	}
	
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("files" + File.separatorChar + FileNames.KORISNICI_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			this.korisnici.add(Korisnik.parse(line));
		}
		in.close();
	}
	
	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter("files" + File.separatorChar + FileNames.KORISNICI_FILE);
		for (Korisnik k: this.korisnici) {
			out.println(k.csvLine());
			out.flush();
		}
		out.close();
	}

	public KorisnikResult dodajKorisnika(Korisnik k) throws Exception {
		
		System.out.println(k);
		if (this.nadjiKorisnika(k.getKorisnickoIme()) != null) 
			return KorisnikResult.AL_EXISTS;
		if (this.hasEmail(k.getEmail()))
			return KorisnikResult.EMAIL_EXISTS;
		this.korisnici.add(k);
		this.store();
		return KorisnikResult.OK;
		
	}
	
	public KorisnikResult obrisiKorisnika(Korisnik k, Korisnik u) throws Exception {
		
		Korisnik korisnik = this.nadjiKorisnika(k.getKorisnickoIme());
		if (korisnik == null) return KorisnikResult.DOESNT_EXIST;
		if (korisnik.equals(u)) return KorisnikResult.CANT_DEL_SELF;
		korisnik.notifyRemoval();
		this.korisnici.remove(korisnik);
		this.store();
		return KorisnikResult.OK;
		
	}
	
	public KorisnikResult izmeniKorisnika(KorisnikChange k, Korisnik u) throws Exception {
		
		Korisnik korisnik = this.nadjiKorisnika(k.getStaroIme());
		if (korisnik == null) return KorisnikResult.DOESNT_EXIST;
		if (this.nadjiKorisnika(k.getNoviKorisnik().getKorisnickoIme()) != null && (!(k.getStaroIme().equals(k.getNoviKorisnik().getKorisnickoIme()))))
			return KorisnikResult.AL_EXISTS;
		if (this.hasEmail(k.getNoviKorisnik().getEmail()) && (!(korisnik.getEmail().equals(k.getNoviKorisnik().getEmail()))))
			return KorisnikResult.EMAIL_EXISTS;
		
		if (k.getStaroIme().equals(u.getIme())) {
			u.setKorisnickoIme(k.getNoviKorisnik().getKorisnickoIme());
			u.setLozinka(k.getNoviKorisnik().getLozinka());
			u.setEmail(k.getNoviKorisnik().getEmail());
			u.setIme(k.getNoviKorisnik().getIme());
			u.setPrezime(k.getNoviKorisnik().getPrezime());
			u.setUloga(k.getNoviKorisnik().getUloga());
			u.setOrganizacija(k.getNoviKorisnik().getOrganizacijaID());
		}
		
		korisnik.setKorisnickoIme(k.getNoviKorisnik().getKorisnickoIme());
		korisnik.setLozinka(k.getNoviKorisnik().getLozinka());
		korisnik.setEmail(k.getNoviKorisnik().getEmail());
		korisnik.setIme(k.getNoviKorisnik().getIme());
		korisnik.setPrezime(k.getNoviKorisnik().getPrezime());
		korisnik.setUloga(k.getNoviKorisnik().getUloga());
		korisnik.setOrganizacija(k.getNoviKorisnik().getOrganizacijaID());
		this.store();
		return KorisnikResult.OK;
		
	}
	
	private boolean hasSuperAdmin() {
		
		for (Korisnik k: this.korisnici) {
			if (k.getUloga().equals(Uloga.SUPER_ADMIN)) return true;
		}
		return false;
		
	}
	
	private boolean hasEmail(String email) {
		
		for (Korisnik k: this.korisnici) {
			if (k.getEmail().equals(email)) return true;
		}
		return false;
		
	}
	
	public void addSuperAdmin() throws Exception {
		
		if (!this.hasSuperAdmin()) {
			this.korisnici.add(new Korisnik("super", "super", "super", "super", "super", Uloga.SUPER_ADMIN, null));
			this.store();
		}
	}
	
	public Korisnik login(User u) {
		for (Korisnik k: this.korisnici) {
			if (k.getKorisnickoIme().equals(u.getKorisnickoIme()) && k.getLozinka().equals(u.getLozinka()))
				return k;
		}
		return null;
	}
	
	
}
