package model.collections;

public enum OrganizacijaManipulation {
	
	OK, AL_EXISTS, DOESNT_EXIST;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch(this.ordinal()) {
		case 0:
			return "OK";
		case 1:
			return "Uneta organizacija vec postoji. ";
		default:
			return "Uneta organizacija ne postoji. ";
		}
	}	

}
