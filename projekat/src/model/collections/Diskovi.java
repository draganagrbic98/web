package model.collections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Main;
import model.beans.Disk;
import rest.JSONDiskChange;
import rest.OpResult.DiskResult;

public class Diskovi implements LoadStoreData {

	private ArrayList<Disk> diskovi;

	public ArrayList<Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(ArrayList<Disk> diskovi) {
		this.diskovi = diskovi;
	}

	public Diskovi() {
		super();
		this.diskovi = new ArrayList<Disk>();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String suma = "DISKOVI: \n";
		for (Disk d : this.diskovi) 
			suma += d + "\n";
		return suma;
	}

	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("files" + File.separatorChar + FileNames.DISKOVI_FILE));
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
		PrintWriter out = new PrintWriter(new FileWriter("files" + File.separatorChar + FileNames.DISKOVI_FILE));
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
		if (this.nadjiDisk(d.getIme()) != null) return DiskResult.AL_EXISTS;
		this.diskovi.add(d);
		this.store();
		return DiskResult.OK;
	}
	
	public DiskResult obrisiDisk(Disk d) throws Exception {
		Disk disk = this.nadjiDisk(d.getIme());
		if (disk == null) return DiskResult.DOESNT_EXIST;
		disk.getMasina().obrisiDisk(disk);
		this.diskovi.remove(disk);
		this.store();
		return DiskResult.OK;
	}

	public DiskResult izmeniDisk(JSONDiskChange d) throws Exception {
		Disk disk = this.nadjiDisk(d.getStaroIme());
		if (disk == null) return DiskResult.DOESNT_EXIST;
		if (this.nadjiDisk(d.getNoviDisk().getIme()) != null && (!(d.getStaroIme().equals(d.getNoviDisk().getIme())))) 
			return DiskResult.AL_EXISTS;
		if (Main.masine.nadjiMasinu(d.getNoviDisk().getMasinaID()) == null) 
			return DiskResult.MAC_DOESNT_EXIST;
		disk.setIme(d.getNoviDisk().getIme());
		disk.setTip(d.getNoviDisk().getTip());
		disk.setKapacitet(d.getNoviDisk().getKapacitet());
		disk.setMasina(d.getNoviDisk().getMasinaID());
		this.store();
		return DiskResult.OK;
	}
	
}
