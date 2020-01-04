package rest.data;

public class JSONRacunZahtev {

	private long pocetniDatum;
	private long krajnjiDatum;

	public JSONRacunZahtev() {
		super();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s, %s", this.pocetniDatum, this.krajnjiDatum);
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
