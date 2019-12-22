package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

import model.Main;
import model.beans.Aktivnost;
import model.beans.Disk;
import model.beans.VirtuelnaMasina;
import model.dmanipulation.JMasinaChange;

public class Masine implements LoadStoreData{
	
	private ArrayList<VirtuelnaMasina> masine;

	public ArrayList<VirtuelnaMasina> getMasine() {
		return masine;
	}

	public void setMasine(ArrayList<VirtuelnaMasina> masine) {
		this.masine = masine;
	}

	public Masine() {
		super();
		this.masine = new ArrayList<VirtuelnaMasina>();
	}
	
	public VirtuelnaMasina nadjiMasinu(String ime) {
		int index = this.masine.indexOf(new VirtuelnaMasina(ime));
		if (index == -1) return null;
		return this.masine.get(index);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = "MASINE: \n";
		for (VirtuelnaMasina m: this.masine)
			suma += m + "\n";
		return suma;
	}
	
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("files" + File.separatorChar + FileNames.MASINE_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			this.masine.add(VirtuelnaMasina.parse(line));
		}
		in.close();
		this.loadAktivnosti();
	}
	
	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(new FileWriter("files" + File.separatorChar + FileNames.MASINE_FILE));
		PrintWriter aktivnostiOut = new PrintWriter(new FileWriter("files" + File.separatorChar + FileNames.AKTIVNOSTI_FILE));
		for (VirtuelnaMasina m: this.masine) {
			out.println(m.csvLine());
			out.flush();
			for (Aktivnost a: m.getAktivnosti()) {
				aktivnostiOut.println(m.getIme() + ";" + a.csvLine());
				aktivnostiOut.flush();
			}
		}
		out.close();
		aktivnostiOut.close();
	}
	
	private void loadAktivnosti() throws IOException, ParseException {
		
		BufferedReader in = new BufferedReader(new FileReader("files" + File.separatorChar + FileNames.AKTIVNOSTI_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			Aktivnost.loadAktivnost(line);
		}
		in.close();
		
	}
	
	
	public boolean izmeniMasinu(JMasinaChange m) throws Exception {
		if (this.nadjiMasinu(m.getNovaMasina().getIme()) != null && (!(m.getStaroIme().equals(m.getNovaMasina().getIme())))) return false;
		VirtuelnaMasina masina = this.nadjiMasinu(m.getStaroIme());
		if (masina == null) return false;
		if (Main.organizacije.nadjiOrganizaciju(m.getNovaMasina().getOrganizacijaID()) == null) {
			return false;
		}
		if (Main.kategorije.nadjiKategoriju(m.getNovaMasina().getKategorija().getIme()) == null) {
			return false;
		}

		//menjanje rama i ostalo se radi preko kategorije
		masina.setIme(m.getNovaMasina().getIme());
		masina.setOrganizacija(m.getNovaMasina().getOrganizacijaID());
		masina.setKategorija(m.getNovaMasina().getKategorija());
		masina.setBrojJezgara(m.getNovaMasina().getBrojJezgara());
		masina.setRAM(m.getNovaMasina().getRAM());
		masina.setGPUjezgra(m.getNovaMasina().getGPUjezgra());
		masina.setAktivnosti(m.getNovaMasina().getAktivnosti());
		masina.setDiskovi(m.getNovaMasina().getDiskoviID());
		this.store();
		return true;
	}
	
	
	public boolean dodajMasinu(VirtuelnaMasina m) throws Exception {
		
		if (this.nadjiMasinu(m.getIme()) != null) return false;
		this.masine.add(m);
		this.store();
		return true;
		
	}

	public boolean obrisiMasinu(VirtuelnaMasina m) throws Exception {
		
		VirtuelnaMasina masina = this.nadjiMasinu(m.getIme());
		if (masina == null) return false;
		for (Disk d: Main.diskovi.getDiskovi()) {
			if (d.getMasinaID().equals(masina.getIme()))
				d.setMasina("null");
		}
		this.masine.remove(masina);
		this.store();
		return true;
		
	}

}
