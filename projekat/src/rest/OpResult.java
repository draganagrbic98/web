package rest;

public class OpResult {
	
	public enum MasinaResponse{
		
		
		
	}
	
	public enum DiskResponse{
		OK, AL_EXISTS, DOESNT_EXIST, MAC_DOESN_EXIST;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Unet disk vec postoji. ";
			case 2: return "Unet disk ne postoji. ";
			default: return "Uneta masina ne postoji. ";
			}
		}
	}
	
	public enum KategorijaResponse{
		OK, AL_EXISTS, DOESNT_EXIST, CANT_DELETE;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Uneta kategorija vec postoji. ";
			case 2: return "Uneta kategorija ne postoji. ";
			default: return "Nije moguce brisanje unete kategorije. ";
			}
		}
	}
	
	public enum OrganizacijaResponse{
		OK, AL_EXISTS, DOESNT_EXIST;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Uneta organizacija vec postoji. ";
			default: return "Uneta organizacija ne postoji. ";
			}
		}
	}

}
