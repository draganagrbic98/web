package model.dmanipulation;

public enum KategorijaManipulation {
	
	OK, AL_EXISTS, DOESNT_EXIST, CANT_DELETE;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch(this.ordinal()) {
		case 0:
			return "OK";
		case 1:
			return "Uneta kategorija vec postoji. ";
		case 2:
			return "Uneta kategorija ne postoji. ";
		default:
			return "Nije moguce brisanje unete kategorije. ";
		}
	}

}
