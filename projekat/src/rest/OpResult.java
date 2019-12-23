package rest;

public class OpResult {
	
	public enum KorisnikResult{
		
		OK, AL_EXISTS, DOESNT_EXIST, EMAIL_EXISTS, CANT_DEL_SELF;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0: return "OK";
			case 1: return "Unet korisnik vec postoji. ";
			case 2: return "Unet korisnik ne postoji. ";
			default: return "Nije moguce brisanje samog sebe. ";
			}
		}
		
	}
	
	public enum MasinaResult{
		
		OK, AL_EXISTS, DOESNT_EXIST, OR_DOESNT_EXIST, KAT_DOESNT_EXIST;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(this.ordinal()) {
			case 0:	return "OK";
			case 1: return "Uneta masina vec postoji. ";
			case 2: return "Uneta masina ne postoji. ";
			case 3: return "Uneta organizacija ne postoij. ";
			default: return "Uneta kategorija ne postoij. ";
			}
		}
		
	}
	
	public enum DiskResult{
		OK, AL_EXISTS, DOESNT_EXIST, MAC_DOESNT_EXIST;
		
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
	
	public enum KategorijaResult{
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
