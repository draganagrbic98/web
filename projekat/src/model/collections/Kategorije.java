package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.beans.Kategorija;
import rest.Main;
import rest.data.KategorijaChange;
import rest.data.OpResult.KategorijaResult;

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
	
	public Kategorija nadjiKategoriju(String ime) {
		
		int index = this.kategorije.indexOf(new Kategorija(ime));
		if (index == -1) return null;
		return this.kategorije.get(index);
		
	}
	
	public KategorijaResult dodajKategoriju(Kategorija k) throws Exception {
		
		if (this.nadjiKategoriju(k.getIme()) != null) 
			return KategorijaResult.AL_EXISTS;
		this.kategorije.add(k);
		this.store();
		return KategorijaResult.OK;
		
	}
	
	public KategorijaResult obrisiKategoriju(Kategorija k) throws Exception {
		
		Kategorija kategorija = this.nadjiKategoriju(k.getIme());
		if (kategorija == null) 
			return KategorijaResult.DOESNT_EXIST;
		if (kategorija.hasMasina()) 
			return KategorijaResult.CANT_DELETE;
		this.kategorije.remove(kategorija);
		this.store();
		return KategorijaResult.OK;
		
	}

	public KategorijaResult izmeniKategoriju(KategorijaChange k) throws Exception {

		Kategorija kategorija = this.nadjiKategoriju(k.getStaroIme());
		if (kategorija == null) 
			return KategorijaResult.DOESNT_EXIST;
		if (this.nadjiKategoriju(k.getNovaKategorija().getIme()) != null && (!(k.getStaroIme().equals(k.getNovaKategorija().getIme())))) 
			return KategorijaResult.AL_EXISTS;
		kategorija.setIme(k.getNovaKategorija().getIme());
		kategorija.setBrojJezgara(k.getNovaKategorija().getBrojJezgara());
		kategorija.setRAM(k.getNovaKategorija().getRAM());
		kategorija.setGPUjezgra(k.getNovaKategorija().getGPUjezgra());
		this.store();
		Main.masine.store();
		return KategorijaResult.OK;
		
	}

}
