Vue.component("profil", {
    data: function(){
        return {
            korisnik: {
            	user: {
            		korisnickoIme: '',
            		lozinka: ''
            	},
            	email: '',
            	ime: '',
            	prezime: '',
            	uloga: '',
            	organizacija: ''
            },
            ponovljena_lozinka: '',
            greskaIme: '',
            greskaPrezime: '',
            greskaEmail: '',
            greskaLozinka: '',
            greskaPonovljena: '',
            greska: false
        }
    }, 

    template: `
        <div>
        	<h1>Izmena profila</h1><br><br>
        	
        	Uloga: <input type="text" v-model="korisnik.uloga" disabled> <br><br>
        	
            Ime: <input type="text" v-model="korisnik.ime"> {{greskaIme}}<br><br>
            Prezime: <input type="text" v-model="korisnik.prezime"> {{greskaPrezime}}<br><br>
            Email: <input type="text" v-model="korisnik.email"> {{greskaEmail}}<br><br>

    		Korisnicko Ime: <input type="text" v-model="korisnik.user.korisnickoIme" disabled> <br><br>
    		
    		Nova Lozinka: <input type="password" v-model="korisnik.user.lozinka"> {{greskaLozinka}}<br><br>
    		Unesite Ponovo: <input type="password" v-model="ponovljena_lozinka"> {{greskaPonovljena}}<br><br>

	        <button v-on:click="izmeni()">Izmeni</button><br><br>
	        
	        <router-link to="/masine">Masine</router-link>
        </div>
    `, 

    methods: {
        izmeni: function(){
        	
        	if (this.korisnik.ime == '') {
        		this.greskaIme = "Ovo je obavezno polje!";
        		this.greska = true;
        	}
        	
        	if (this.korisnik.prezime == '') {
        		this.greskaPrezime = "Ovo je obavezno polje!";
        		this.greska = true;
        	}
        	
        	if (this.korisnik.email == '') {
        		this.greskaEmail = "Ovo je obavezno polje!";
        		this.greska = true;
        	}
        	
        	if (this.korisnik.user.lozinka == '') {
        		this.greskaLozinka = "Ovo je obavezno polje!";
        		this.greska = true;
        	}
        	
        	if (this.ponovljena_lozinka !== this.korisnik.user.lozinka) {
        		this.greskaPonovljena = "Lozinke se ne poklapaju!";
        		this.greska = true;
        	}
        	
            if (this.greska == false){
	            axios.post("rest/user/izmena", {"korisnickoIme": this.korisnik.user.korisnickoIme, "noviKorisnik": this.korisnik})
	            .then(response => {
	                if (response.data.result == "true"){
	                    this.$router.push("masine");
	                }
	            });
            }
        }
    }, 

    mounted(){
        axios.get("rest/user/profil")
        .then(response => {
            this.korisnik = response.data;
        });
    }

})