package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.beans.Korisnik;
import model.beans.User;

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
	
	public Korisnik login(User u) {
		for (Korisnik k: this.korisnici) {
			if (k.getUser().login(u)) return k;
		}
		return null;
	}

}
