package model.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Main;

public class Aktivnost implements CSVData{
	
	private Date datum;
	private boolean upaljen;
	
	public Date getDatum() {
		return datum;
	}
	
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public boolean isUpaljen() {
		return upaljen;
	}
	
	public void setUpaljen(boolean upaljen) {
		this.upaljen = upaljen;
	}
	
	public Aktivnost() {
		super();
	}
	
	public Aktivnost(Date datum, boolean upaljen) {
		this();
		this.datum = datum;
		this.upaljen = upaljen;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");
		return String.format("Datum: %s, upaljen: %s", f.format(this.datum), this.upaljen);
	}
	
	public static void loadAktivnost(String line) throws ParseException {
		
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");
		String[] array = line.split(";");
		VirtuelnaMasina masina = Main.masine.nadjiMasinu(array[0].trim());
		Date datum = f.parse(array[1].trim());
		boolean upaljen = Boolean.parseBoolean(array[2].trim());
		masina.dodajAktivnost(new Aktivnost(datum, upaljen));
		
	}
	
	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");
		return f.format(this.datum) + ";" + this.upaljen;
	}
	
}
