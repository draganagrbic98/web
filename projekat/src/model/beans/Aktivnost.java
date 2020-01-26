package model.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.support.CSVData;
import model.support.StatusMasine;
import model.support.ValidData;
import rest.Main;

public class Aktivnost implements CSVData, ValidData {

	private Date datumPaljenja;
	private Date datumGasenja;
	private StatusMasine status;

	public Aktivnost() {
		super();
	}

	public Aktivnost(Date datumPaljenja, Date datumGasenja, StatusMasine status) {
		this();
		this.datumPaljenja = datumPaljenja;
		this.datumGasenja = datumGasenja;
		this.status = status;
	}

	public static void loadAktivnost(String line) throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");
		String[] array = line.split(";");
		VirtuelnaMasina masina = Main.masine.nadjiMasinu(array[0].trim());
		Date datumPaljenja = f.parse(array[1].trim());

		Date datumGasenja = null;

		if (array[2].trim().equals("/") == false)
			datumGasenja = f.parse(array[2].trim());

		StatusMasine status = StatusMasine.valueOf(array[3].trim());
		if (masina != null)
			masina.dodajAktivnost(new Aktivnost(datumPaljenja, datumGasenja, status));

	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");

		String datumGasenja = "/";

		if (this.datumGasenja == null)
			return f.format(this.datumPaljenja) + ";" + datumGasenja + ";" + this.status;
		else
			return f.format(this.datumPaljenja) + ";" + f.format(this.datumGasenja) + ";" + this.status;
	}

	@Override
	public boolean validData() {
		// TODO Auto-generated method stub
		
		//petre, ovo ti sredi
		return true;
	}
	
	public Date getDatumPaljenja() {
		return datumPaljenja;
	}
	public void setDatumPaljenja(Date datumPaljenja) {
		this.datumPaljenja = datumPaljenja;
	}
	public Date getDatumGasenja() {
		return datumGasenja;
	}
	public void setDatumGasenja(Date datumGasenja) {
		this.datumGasenja = datumGasenja;
	}
	public StatusMasine getStatus() {
		return status;
	}
	public void setStatus(StatusMasine status) {
		this.status = status;
	}

}
