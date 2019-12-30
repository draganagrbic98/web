package model.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Main;

public class Aktivnost implements CSVData {

	private Date datumPaljenja;
	private Date datumGasenja;
	private boolean upaljen;

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

	public boolean isUpaljen() {
		return upaljen;
	}

	public void setUpaljen(boolean upaljen) {
		this.upaljen = upaljen;
	}

	public Aktivnost() {
		super();
	}

	public Aktivnost(Date datumPaljenja, Date datumGasenja, boolean upaljen) {
		this();
		this.datumPaljenja = datumPaljenja;
		this.datumGasenja = datumGasenja;
		this.upaljen = upaljen;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");

		if (this.datumGasenja.equals(null))
			return String.format("Datum Paljenja: %s, Datum Gasenja: /, Upaljena: %s", f.format(this.datumPaljenja),
					this.upaljen);
		else
			return String.format("Datum Paljenja: %s, Datum Gasenja: %s, Upaljena: %s", f.format(this.datumPaljenja),
					f.format(this.datumGasenja), this.upaljen);
	}

	public static void loadAktivnost(String line) throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");
		String[] array = line.split(";");
		VirtuelnaMasina masina = Main.masine.nadjiMasinu(array[0].trim());
		Date datumPaljenja = f.parse(array[1].trim());

		Date datumGasenja = null;

		if (array[2].trim().equals("/") == false)
			datumGasenja = f.parse(array[2].trim());

		boolean upaljen = Boolean.parseBoolean(array[3].trim());
		masina.dodajAktivnost(new Aktivnost(datumPaljenja, datumGasenja, upaljen));

	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy.");

		String datumGasenja = "/";

		if (this.datumGasenja == null)
			return f.format(this.datumPaljenja) + ";" + datumGasenja + ";" + this.upaljen;
		else
			return f.format(this.datumPaljenja) + ";" + f.format(this.datumGasenja) + ";" + this.upaljen;
	}

}
