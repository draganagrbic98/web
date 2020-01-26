package model.collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.beans.Disk;
import model.beans.VirtuelnaMasina;
import model.support.FileNames;
import model.support.LoadStoreData;
import rest.Main;
import rest.data.DiskChange;
import rest.data.OpResult.DiskResult;

public class Diskovi implements LoadStoreData {

	private ArrayList<Disk> diskovi;

	public Diskovi() {
		super();
		this.diskovi = new ArrayList<Disk>();
	}

	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader(FileNames.DISKOVI_FILE));
		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			this.diskovi.add(Disk.parse(line));
		}
		in.close();
	}

	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(new FileWriter(FileNames.DISKOVI_FILE));
		for (Disk d : this.diskovi) {
			out.println(d.csvLine());
			out.flush();
		}
		out.close();
	}

	public Disk nadjiDisk(String ime) {
		
		int index = this.diskovi.indexOf(new Disk(ime));
		if (index == -1) return null;
		return this.diskovi.get(index);
		
	}
	
	public DiskResult dodajDisk(Disk d) throws Exception {
		
		if (this.nadjiDisk(d.getIme()) != null) 
			return DiskResult.AL_EXISTS;
		
		if (d.getOrganizacija().getMasine().contains(d.getIme()))
			return DiskResult.INVALID_NAME;
		
		if (Main.organizacije.nadjiOrganizaciju(d.getOrganizacijaID()) == null)
			return DiskResult.ORG_NOT_EXISTS;
		
		d.getOrganizacija().dodajDisk(d);
		if (d.getMasina() != null)
			d.getMasina().dodajDisk(d);
		
		this.diskovi.add(d);
		this.store();
		return DiskResult.OK;
		
	}
	
	public DiskResult obrisiDisk(Disk d) throws Exception {
		
		Disk disk = this.nadjiDisk(d.getIme());
		if (disk == null) 
			return DiskResult.DOESNT_EXIST;
		
		disk.notifyRemoval();
		this.diskovi.remove(disk);
		this.store();
		return DiskResult.OK;
		
	}

	public DiskResult izmeniDisk(DiskChange d) throws Exception {
		
		Disk disk = this.nadjiDisk(d.getStaroIme());
		if (disk == null) 
			return DiskResult.DOESNT_EXIST;
		
		if (this.nadjiDisk(d.getNoviDisk().getIme()) != null && (!(d.getStaroIme().equals(d.getNoviDisk().getIme())))) 
			return DiskResult.AL_EXISTS;
		
		disk.setIme(d.getNoviDisk().getIme());
		disk.setTip(d.getNoviDisk().getTip());
		disk.setKapacitet(d.getNoviDisk().getKapacitet());
		disk.setMasina(d.getNoviDisk().getMasinaID());
		
		for (VirtuelnaMasina vm : Main.masine.getMasine()) {
			if (vm.getIme().equals(d.getStaraMasina())) {
				vm.getDiskoviID().remove(disk.getIme());
				Main.masine.store();
				break;
			}
		}
		
		for (VirtuelnaMasina vm : Main.masine.getMasine()) {
			if (vm.getIme().equals(d.getNoviDisk().getMasinaID())) {
				vm.getDiskoviID().add(disk.getIme());
				Main.masine.store();
				break;
			}
		}
		
		this.store();
		return DiskResult.OK;
	}
	
	public ArrayList<Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(ArrayList<Disk> diskovi) {
		this.diskovi = diskovi;
	}
	
}
