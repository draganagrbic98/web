package model.dmanipulation;

public enum DiskManipulation {
	
	OK, AL_EXISTS, DOESNT_EXIST, MAC_DOESN_EXIST;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		switch(this.ordinal()) {
		case 0:
			return "OK";
		case 1:
			return "Unet disk vec postoji. ";
		case 2:
			return "Unet disk ne postoji. ";
		default:
			return "Uneta masina ne postoji. ";
		}
	}

}
