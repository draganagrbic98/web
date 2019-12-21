package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

import model.beans.Aktivnost;
import model.beans.VirtuelnaMasina;

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

}