Vue.component("profil", {

	data: function(){
		return{
			korisnik: {
				"user": {
					"korisnickoIme": '', 
					"lozinka": '',
				}, 
				"email": '', 
				"ime": '', 
				"prezime": '',
				"uloga": '', 
				"organizacija": ''
			}, 
			novaLozinka: '', 
			ponovljenaLozinka: '',
			greskaEmail: '', 
			greskaIme: '',
			greskaPrezime: '', 
			greskaLozinka: '', 
			greskaPonovljenaLozinka: '',
			greskaServer: '',
			greska: false
		}
	}, 

	template: `
	
		<div>
			<h1>Podaci o korisniku</h1><br><br>

			Korisnicko Ime: <input type="text" v-model="korisnik.user.korisnickoIme" disabled> <br><br>
			Email: <input type="text" v-model="korisnik.email"> {{greskaEmail}}<br><br>
			Ime: <input type="text" v-model="korisnik.ime"> {{greskaIme}}<br><br>
			Prezime: <input type="text" v-model="korisnik.prezime"> {{greskaPrezime}}<br><br>
			Uloga: <input type="text" v-model="korisnik.uloga" disabled> <br><br>
			Organizacija: <input type="text" v-model="korisnik.organizacija" disabled><br><br>
			Nova Lozinka: <input type="password" v-model="novaLozinka"> {{greskaLozinka}}<br><br>
			Unesite Ponovo: <input type="password" v-model="ponovljenaLozinka" v-bind:disabled="novaLozinka==''"> {{greskaPonovljenaLozinka}}<br><br>
			<button v-on:click="izmeni()">Izmeni</button><br><br>
			<router-link to="/masine">MAIN PAGE</router-link>
			{{greskaServer}}

		</div>
	`, 

	watch: {
		novaLozinka: function(oldLoz, newLoz){
			if (this.novaLozinka == '')
			this.ponovljenaLozinka = '';
		}
	},

	methods: {
		izmeni: function(){
			
			this.greskaEmail = '';
			this.greskaIme = '';
			this.greskaPrezime = '';
			this.greskaLozinka = '';
			this.greskaPonovljenaLozinka = '';
			this.greska = false;

			if (this.korisnik.email == ''){
				this.greskaEmail = "Email ne sme biti prazan. ";
				this.greska = true;
			}
			if (this.korisnik.ime == ''){
				this.greskaIme = "Ime ne sme biti prazno. ";
				this.greska = true;
			}
			if (this.korisnik.prezime == ''){
				this.greskaPrezime = "Prezime ne sme biti prazno. ";
				this.greska = true;
			}
			if (this.novaLozinka != '' && this.novaLozinka != this.ponovljenaLozinka){
				this.greskaPonovljenaLozinka = "Lozinke se ne poklapaju. ";
				this.greska = true;
			}
			if (this.greska) return;

			this.korisnik.user.lozinka = this.novaLozinka;
			axios.post("rest/user/izmena", {"staroIme": this.korisnik.user.korisnickoIme, "noviKorisnik": this.korisnik})
			.then(response => {
				this.$router.push("masine");
			})
			.catch(error => {
				this.greskaServer = error.response.data.result;
			})

		}
	}, 

	mounted(){
		axios.get("rest/user/profil")
		.then(response => {
			this.korisnik = response.data
		})
		.catch(error => {
			this.$router.push("/");
		})
	}

});