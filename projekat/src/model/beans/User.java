package model.beans;

public class User implements CSVData {

	private String korisnickoIme;
	private String lozinka;
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public User() {
		super();
	}

	public User(String korisnickoIme, String lozinka) {
		this();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
	}

	public User(String korisnickoIme) {
		super();
		this.korisnickoIme = korisnickoIme;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Korisnicko ime: %s, lozinka: %s", this.korisnickoIme, this.lozinka);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof User))
			return false;
		return ((User) obj).korisnickoIme.equals(this.korisnickoIme);
	}

	@Override
	public String csvLine() {
		// TODO Auto-generated method stub
		return this.korisnickoIme + ";" + this.lozinka;
	}
	
	@Override
	public boolean validData() {
		// TODO Auto-generated method stub

		if (this.korisnickoIme == null || this.korisnickoIme.equals("")) return false;
		if (this.lozinka == null || this.lozinka.equals("")) return false;
		return true;
		
	}

}
