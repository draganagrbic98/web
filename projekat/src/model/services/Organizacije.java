package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.FileNames;
import model.LoadStoreData;
import model.beans.Organizacija;
import rest.Main;
import rest.beans.OrganizacijaChange;
import rest.beans.OperationResult.OrganizacijaResponse;

public class Organizacije implements LoadStoreData{
		
	private ArrayList<Organizacija> organizacije;

	public Organizacije() {
		super();
		this.organizacije = new ArrayList<Organizacija>();
	}
	
	public synchronized Organizacija nadjiOrganizaciju(String ime) {
		int index = this.organizacije.indexOf(new Organizacija(ime));
		if (index == -1) return null;
		return this.organizacije.get(index);
	}
	
	public synchronized OrganizacijaResponse dodajOrganizaciju(Organizacija o) throws Exception {
		
		if (this.nadjiOrganizaciju(o.getIme()) != null) 
			return OrganizacijaResponse.AL_EXISTS;
		
		if (o.getLogo() == null)
			o.setDefaultLogo();
				
		this.organizacije.add(o);
		this.store();
		return OrganizacijaResponse.OK;
		
	}

	public synchronized OrganizacijaResponse izmeniOrganizaciju(OrganizacijaChange o) throws Exception {
		
		Organizacija organizacija = this.nadjiOrganizaciju(o.getStaroIme());
		if (organizacija == null) 
			return OrganizacijaResponse.DOESNT_EXIST;
		
		if (this.nadjiOrganizaciju(o.getNovaOrganizacija().getIme()) != null && (!(o.getStaroIme().equals(o.getNovaOrganizacija().getIme()))))
			return OrganizacijaResponse.AL_EXISTS;
		
		organizacija.setIme(o.getNovaOrganizacija().getIme());
		organizacija.setOpis(o.getNovaOrganizacija().getOpis());
		organizacija.setLogo(o.getNovaOrganizacija().getLogo());
		organizacija.setKorisnici(o.getNovaOrganizacija().getKorisnici());
		organizacija.setMasine(o.getNovaOrganizacija().getMasine());
		
		this.store();
		Main.korisnici.store();
		Main.masine.store();
		return OrganizacijaResponse.OK;
		
	}
	
	public ArrayList<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(ArrayList<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}
	
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader(FileNames.ORGANIZACIJE_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			this.organizacije.add(Organizacija.parse(line));
		}
		in.close();
	}
	
	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(new FileWriter(FileNames.ORGANIZACIJE_FILE));
		for (Organizacija o: this.organizacije) {
			out.println(o.csvLine());
			out.flush();
		}
		out.close();
	}

}
