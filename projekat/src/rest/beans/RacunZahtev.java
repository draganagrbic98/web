package rest.beans;

public class RacunZahtev {

	private long pocetniDatum;
	private long krajnjiDatum;

	public RacunZahtev() {
		super();
	}
	
	public RacunZahtev(long pocetniDatum, long krajnjiDatum) {
		super();
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
	}
	
	public long getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(long pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public long getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(long krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

}
