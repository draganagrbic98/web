package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.FileNames;
import model.LoadStoreData;
import model.StatusMasine;
import model.beans.Aktivnost;
import model.beans.Disk;
import model.beans.VirtuelnaMasina;
import rest.Main;
import rest.beans.MasinaChange;
import rest.beans.OperationResult.MasinaResult;

public class Masine implements LoadStoreData {

	private ArrayList<VirtuelnaMasina> masine;

	public Masine() {
		super();
		this.masine = new ArrayList<VirtuelnaMasina>();
	}

	public synchronized VirtuelnaMasina nadjiMasinu(String ime) {
		int index = this.masine.indexOf(new VirtuelnaMasina(ime));
		if (index == -1)
			return null;
		return this.masine.get(index);
	}

	public synchronized MasinaResult dodajMasinu(VirtuelnaMasina m) throws Exception {

		if (this.nadjiMasinu(m.getIme()) != null)
			return MasinaResult.AL_EXISTS;

		if (Main.organizacije.nadjiOrganizaciju(m.getOrganizacijaID()) == null)
			return MasinaResult.ORG_NOT_EXISTS;

		if (m.getOrganizacija().getMasine().contains(m.getIme()))
			return MasinaResult.INVALID_NAME;

		for (String d: m.getDiskoviID()) {
			if (Main.diskovi.nadjiDisk(d) == null)
				return MasinaResult.DISK_NOT_EXISTS;
		}

		m.getOrganizacija().dodajMasinu(m);
		for (Disk d: m.getDiskovi()) {
			d.notifyRemoval();
			d.setMasina(m.getIme());
		}
		
		this.masine.add(m);
		this.store();
		Main.diskovi.store();
		return MasinaResult.OK;

	}

	public synchronized MasinaResult obrisiMasinu(VirtuelnaMasina m) throws Exception {

		VirtuelnaMasina masina = this.nadjiMasinu(m.getIme());
		if (masina == null) return MasinaResult.DOESNT_EXIST;
		
		masina.notifyRemoval();
		this.masine.remove(masina);
		this.store();
		Main.diskovi.store();
		return MasinaResult.OK;

	}

	public synchronized MasinaResult izmeniMasinu(MasinaChange m) throws Exception {

		VirtuelnaMasina masina = this.nadjiMasinu(m.getStaroIme());
		if (masina == null) return MasinaResult.DOESNT_EXIST;
		
		if (this.nadjiMasinu(m.getNovaMasina().getIme()) != null && (!(m.getStaroIme().equals(m.getNovaMasina().getIme()))))
			return MasinaResult.AL_EXISTS;
		
		masina.setIme(m.getNovaMasina().getIme());
		masina.setOrganizacija(m.getNovaMasina().getOrganizacijaID());
		masina.setKategorija(m.getNovaMasina().getKategorija());
		masina.setBrojJezgara(m.getNovaMasina().getBrojJezgara());
		masina.setRAM(m.getNovaMasina().getRAM());
		masina.setGPUjezgra(m.getNovaMasina().getGPUjezgra());
		masina.setAktivnosti(m.getNovaMasina().getAktivnosti());
		
		for (Disk d: m.getNovaMasina().getDiskovi()) {
			d.notifyRemoval();
			d.setMasina(masina.getIme());
			masina.dodajDisk(d);
		}
		
		this.store();
		Main.diskovi.store();
		return MasinaResult.OK;
		
	}

	public synchronized MasinaResult promeniStatusMasine(MasinaChange m) throws Exception {
		
		VirtuelnaMasina masina = this.nadjiMasinu(m.getStaroIme());
		if (masina == null) return MasinaResult.DOESNT_EXIST;
				
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		
		if (!masina.getAktivnosti().isEmpty()) {
			
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
		return MasinaResult.OK;
		
	}
	
	public ArrayList<VirtuelnaMasina> getMasine() {
		return masine;
	}

	public void setMasine(ArrayList<VirtuelnaMasina> masine) {
		this.masine = masine;
	}
	
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader(FileNames.MASINE_FILE));
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

	private void loadAktivnosti() throws IOException, ParseException {

		BufferedReader in = new BufferedReader(new FileReader(FileNames.AKTIVNOSTI_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			Aktivnost.loadAktivnost(line);
		}
		in.close();

	}

	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		
		PrintWriter out = new PrintWriter(new FileWriter(FileNames.MASINE_FILE));
		PrintWriter aktivnostiOut = new PrintWriter(new FileWriter(FileNames.AKTIVNOSTI_FILE));
		
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
	
}
