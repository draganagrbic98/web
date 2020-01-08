package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Main;
import model.beans.Aktivnost;
import model.beans.StatusMasine;
import model.beans.VirtuelnaMasina;
import rest.data.JSONMasinaChange;
import rest.data.OpResult.MasinaResult;

public class Masine implements LoadStoreData {

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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = "MASINE: \n";
		for (VirtuelnaMasina m : this.masine)
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
		PrintWriter aktivnostiOut = new PrintWriter(
				new FileWriter("files" + File.separatorChar + FileNames.AKTIVNOSTI_FILE));
		for (VirtuelnaMasina m : this.masine) {
			out.println(m.csvLine());
			out.flush();
			for (Aktivnost a : m.getAktivnosti()) {
				aktivnostiOut.println(m.getIme() + ";" + a.csvLine());
				aktivnostiOut.flush();
			}
		}
		out.close();
		aktivnostiOut.close();
	}

	private void loadAktivnosti() throws IOException, ParseException {

		BufferedReader in = new BufferedReader(
				new FileReader("files" + File.separatorChar + FileNames.AKTIVNOSTI_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			Aktivnost.loadAktivnost(line);
		}
		in.close();

	}

	public VirtuelnaMasina nadjiMasinu(String ime) {
		int index = this.masine.indexOf(new VirtuelnaMasina(ime));
		if (index == -1)
			return null;
		return this.masine.get(index);
	}

	public MasinaResult dodajMasinu(VirtuelnaMasina m) throws Exception {

		if (this.nadjiMasinu(m.getIme()) != null)
			return MasinaResult.AL_EXISTS;
		
		this.masine.add(m);
		this.store();
		return MasinaResult.OK;

	}

	public MasinaResult obrisiMasinu(VirtuelnaMasina m) throws Exception {

		VirtuelnaMasina masina = this.nadjiMasinu(m.getIme());
		if (masina == null)
			return MasinaResult.DOESNT_EXIST;
		masina.notifyRemoval();
		this.masine.remove(masina);
		this.store();
		Main.diskovi.store();
		return MasinaResult.OK;

	}

	public MasinaResult izmeniMasinu(JSONMasinaChange m) throws Exception {

		VirtuelnaMasina masina = this.nadjiMasinu(m.getStaroIme());
		
		if (masina == null)
			return MasinaResult.DOESNT_EXIST;
		if (this.nadjiMasinu(m.getNovaMasina().getIme()) != null
				&& (!(m.getStaroIme().equals(m.getNovaMasina().getIme()))))
			return MasinaResult.AL_EXISTS;

		masina.setIme(m.getNovaMasina().getIme());
		masina.setOrganizacija(m.getNovaMasina().getOrganizacijaID());
		masina.setKategorija(m.getNovaMasina().getKategorija());
		masina.setBrojJezgara(m.getNovaMasina().getBrojJezgara());
		masina.setRAM(m.getNovaMasina().getRAM());
		masina.setGPUjezgra(m.getNovaMasina().getGPUjezgra());
		masina.setAktivnosti(m.getNovaMasina().getAktivnosti());
		masina.setDiskovi(m.getNovaMasina().getDiskoviID());
		this.store();
		Main.diskovi.store();
		return MasinaResult.OK;

	}

	public MasinaResult promeniStatusMasine(JSONMasinaChange m) throws Exception {
		
		VirtuelnaMasina masina = this.nadjiMasinu(m.getStaroIme());

		if (masina == null)
			return MasinaResult.DOESNT_EXIST;
		if (this.nadjiMasinu(m.getNovaMasina().getIme()) != null
				&& (!(m.getStaroIme().equals(m.getNovaMasina().getIme()))))
			return MasinaResult.AL_EXISTS;
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		
		if (masina.getAktivnosti().isEmpty() == false) {
			Aktivnost trenutnaAktivnost = masina.getAktivnosti().get(masina.getAktivnosti().size() - 1);
			
			if (trenutnaAktivnost.getStatus() == StatusMasine.UPALJENA) {
				trenutnaAktivnost.setDatumGasenja(f.parse(f.format(date)));
				trenutnaAktivnost.setStatus(StatusMasine.UGASENA);
			}
			else {
				Aktivnost novaAktivnost = new Aktivnost(f.parse(f.format(date)), null, StatusMasine.UPALJENA);
				masina.getAktivnosti().add(novaAktivnost);
			}
		}
		else {
			Aktivnost novaAktivnost = new Aktivnost(f.parse(f.format(date)), null, StatusMasine.UPALJENA);
			masina.getAktivnosti().add(novaAktivnost);
		}
		
		this.store();
		Main.diskovi.store();
		return MasinaResult.OK;
		
	}

}
