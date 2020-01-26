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
				"organizacija": null
			}, 
			korisnikID: '',
			novaLozinka: '', 
			ponovljenaLozinka: '',
			greskaEmail: '', 
			greskaIme: '',
			greskaPrezime: '', 
			greskaLozinka: '', 
			greskaServer: '',
			greska: false,
			greskaKorisnickoIme: ''
		}
	}, 

	template: `
	
		<div class="profil">

			<h1>Podaci o korisniku</h1><br><br>

			<table>
				<tr><td class="right">Korisnicko Ime: </td> <td class="left" colspan="2"><input type="text" v-model="korisnik.user.korisnickoIme">{{greskaKorisnickoIme}}</td></tr>
				<tr><td class="right">Email: </td> <td class="left"><input type="text" v-model="korisnik.email"></td> <td>{{greskaEmail}}</td></tr>
				<tr><td class="right">Ime: </td> <td class="left"><input type="text" v-model="korisnik.ime"></td> <td>{{greskaIme}}</td></tr>
				<tr><td class="right">Prezime: </td> <td class="left"><input type="text" v-model="korisnik.prezime"></td> <td>{{greskaPrezime}}</td></tr>
				<tr><td class="right">Uloga: </td> <td class="left" colspan="2"><input type="text" v-model="korisnik.uloga" disabled></td></tr>
				<tr><td class="right">Organizacija: </td> <td class="left" colspan="2"><input type="text" v-model="korisnik.organizacija" disabled></td></tr>
				<tr><td class="right">Nova Lozinka: </td> <td class="left" colspan="2"><input type="password" v-model="novaLozinka"></td></tr>
				<tr><td class="right">Ponovljena lozinka: </td> <td class="left"><input type="password" v-model="ponovljenaLozinka" v-bind:disabled="novaLozinka==''"></td> <td>{{greskaLozinka}}</td></tr>
				<tr><td colspan="3"><button v-on:click="izmeni()">IZMENI</button></td></tr>
				<tr><td colspan="3">{{greskaServer}}</td></tr>
			</table>
			
			<br><br>
			
			<router-link to="/masine">Pocetna Stranica</router-link><br><br>

		</div>
	`, 

	watch: {
		novaLozinka: function(oldLoz, newLoz){
			if (this.novaLozinka == '')
			this.ponovljenaLozinka = '';
		}
	},

	mounted(){

		axios.get("rest/user/profil")
		.then(response => {
			this.korisnik = response.data;
			this.korisnikID = this.korisnik.user.korisnickoIme;
		})
		.catch(error => {
			this.$router.push("/");
		});
	},

	methods: {
		
		emailProvera: function emailIsValid (email) {
    		return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
    	},
    	
    	osvezi: function(){
    		
    		this.greskaEmail = '';
			this.greskaIme = '';
			this.greskaPrezime = '';
			this.greskaLozinka = '';
			this.greska = false;
			this.greskaKorisnickoIme = '';
    		
    	},
		
		izmeni: function(){
			
			if (this.korisnik.user.korisnickoIme == ''){
				this.greskaKorisnickoIme = "Korisnicko ime ne sme biti prazno. ";
				this.greska = true;
			}

			if (this.korisnik.email == '' || !this.emailProvera(this.korisnik.email)){
				this.greskaEmail = "Email nije ispravan. ";
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
				this.greskaLozinka = "Lozinke se ne poklapaju. ";
				this.greska = true;
			}
			if (this.greska) return;

			this.korisnik.user.lozinka = this.novaLozinka != '' ? this.novaLozinka : this.korisnik.user.lozinka;
			axios.post("rest/korisnici/izmena", {"staroIme": this.korisnikID, "noviKorisnik": this.korisnik})
			.then(response => {
				this.$router.push("masine");
			})
			.catch(error => {
				this.greskaServer = error.response.data.result;
			});

		}
	}

});