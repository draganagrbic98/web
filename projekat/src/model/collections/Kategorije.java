package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.beans.Kategorija;

public class Kategorije implements LoadStoreData{
	
	private ArrayList<Kategorija> kategorije;

	public ArrayList<Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(ArrayList<Kategorija> kategorije) {
		this.kategorije = kategorije;
	}

	public Kategorije() {
		super();
		this.kategorije = new ArrayList<Kategorija>();
	}
	
	public Kategorija nadjiKategoriju(String ime) {
		int index = this.kategorije.indexOf(new Kategorija(ime));
		if (index == -1) return null;
		return this.kategorije.get(index);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = "KATEGORIJE: \n";
		for (Kategorija k: this.kategorije)
			suma += k + "\n";
		return suma;
	}
	
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("files" + File.separatorChar + FileNames.KATEGORIJE_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			this.kategorije.add(Kategorija.parse(line));
		}
		in.close();
	}
	
	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(new FileWriter("files" + File.separatorChar + FileNames.KATEGORIJE_FILE));
		for (Kategorija k: this.kategorije) {
			out.println(k.csvLine());
			out.flush();
		}
		out.close();
	}

}
