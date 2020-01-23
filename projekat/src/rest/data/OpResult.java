package rest.data;

public class OpResult {
	
	public enum KategorijaResult {
		
		OK, AL_EXISTS, DOESNT_EXIST, CANT_DELETE;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Uneta kategorija vec postoji. ";
			case 2: return "Uneta kategorija ne postoji. ";
			default: return "Kategorija poseduje masine. Nemoguce brisanje. ";
			}
		}
	}
	
	public enum MasinaResult {
		
		OK, AL_EXISTS, DOESNT_EXIST, DISK_NOT_EXISTS, ORG_NOT_EXISTS;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0:	return "OK";
			case 1: return "Uneta masina vec postoji. ";
			case 2: return "Uneta masina ne postoji. ";
			case 3: return "Ne postoje svi uneti diskovi. ";
			default: return "Uneta organizacija ne postoji. ";
			}
		}
		
	}
	
	public enum DiskResult {
		
		OK, AL_EXISTS, DOESNT_EXIST;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Unet disk vec postoji. ";
			default: return "Unet disk ne postoji. ";
			}
		}
	}
	
	public enum OrganizacijaResponse {
		
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
	
	public enum KorisnikResult{
		
		OK, AL_EXISTS, DOESNT_EXIST, EMAIL_EXISTS, CANT_DEL_SELF, ORG_NOT_EXISTS;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Unet korisnik vec postoji. ";
			case 2: return "Unet korisnik ne postoji. ";
			case 3: return "Unet email vec postoji. ";
			case 4: return "Nije moguce brisanje samog sebe. ";
			default: return "Uneta organizacija ne postoji. ";
			}
		}
		
	}

}
