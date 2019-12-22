package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.beans.Organizacija;
import rest.JSONOrganizacijaChange;
import rest.OpResult.OrganizacijaResponse;

public class Organizacije implements LoadStoreData{
		
	private ArrayList<Organizacija> organizacije;

	public ArrayList<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(ArrayList<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}

	public Organizacije() {
		super();
		this.organizacije = new ArrayList<Organizacija>();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = "ORGANIZACIJE: \n";
		for (Organizacija o: this.organizacije)
			suma += o + "\n";
		return suma;
	}
	
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("files" + File.separatorChar + FileNames.ORGANIZACIJE_FILE));
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
		PrintWriter out = new PrintWriter(new FileWriter("files" + File.separatorChar + FileNames.ORGANIZACIJE_FILE));
		for (Organizacija o: this.organizacije) {
			out.println(o.csvLine());
			out.flush();
		}
		out.close();
	}
	
	public Organizacija nadjiOrganizaciju(String ime) {
		int index = this.organizacije.indexOf(new Organizacija(ime));
		if (index == -1) return null;
		return this.organizacije.get(index);
	}
	
	public OrganizacijaResponse dodajOrganizaciju(Organizacija o) throws Exception {
		
		if (this.nadjiOrganizaciju(o.getIme()) != null) return OrganizacijaResponse.AL_EXISTS;
		this.organizacije.add(o);
		this.store();
		return OrganizacijaResponse.OK;
		
	}

	public OrganizacijaResponse izmeniOrganizaciju(JSONOrganizacijaChange o) throws Exception {
		
		Organizacija organizacija = this.nadjiOrganizaciju(o.getStaroIme());
		if (organizacija == null) return OrganizacijaResponse.DOESNT_EXIST;
		if (this.nadjiOrganizaciju(o.getNovaOrganizacija().getIme()) != null && (!(o.getStaroIme().equals(o.getNovaOrganizacija().getIme()))))
			return OrganizacijaResponse.AL_EXISTS;
		organizacija.setIme(o.getNovaOrganizacija().getIme());
		organizacija.setOpis(o.getNovaOrganizacija().getOpis());
		organizacija.setLogo(o.getNovaOrganizacija().getLogo());
		organizacija.setKorisnici(o.getNovaOrganizacija().getKorisnici());
		organizacija.setMasine(o.getNovaOrganizacija().getMasine());
		this.store();
		return OrganizacijaResponse.OK;
		
	}

}
