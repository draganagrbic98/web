Vue.component("dodajKorisnika", {

    data: function(){
        return{
            noviKorisnik: {
                "user": {
                    "korisnickoIme": '', 
                    "lozinka": ''
                }, 
                "email": '', 
                "ime": '', 
                "prezime": '', 
                "uloga": '', 
                "organizacija": ''
            }, 
            novaLozinka: '',
            ponovljenaLozinka: '',
            greskaKorisnickoIme: '', 
            greskaEmail: '', 
            greskaIme: '', 
            greskaPrezime: '', 
            greskaUloga: '', 
            greskaOrganizacija: '',
            greskaLozinka: '', 
            greskaPonovljenaLozinka: '',
            greskaServer: '',
            greska: false, 
            uloge: [],
            organizacije: [], 
            organizacija: {}
        }
    }, 

    

    template: `

        <div class="dodavanje">

            <h1>Registracija novog korisnika</h1>
            
            <br>
            
            <div>
            
	    		<table>

		            <tr><td class="left">Korisnicko ime: </td> <td class="right"><input type="text" v-model="noviKorisnik.user.korisnickoIme"></td> <td>{{greskaKorisnickoIme}}</td></tr>
		            <tr><td class="left">Email: </td> <td class="right"><input type="text" v-model="noviKorisnik.email"></td> <td>{{greskaEmail}}</td></tr>
		            <tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="noviKorisnik.ime"></td> <td>{{greskaIme}}</td></tr>
		            <tr><td class="left">Prezime: </td> <td class="right"><input type="text" v-model="noviKorisnik.prezime"></td> <td>{{greskaPrezime}}</td></tr>
		            
		            <tr><td class="left">Uloga: </td> <td class="right"><select v-model="noviKorisnik.uloga"> 
		                <option v-for="u in uloge">{{u}}</option>
		            </select> </td><td>
		            {{greskaUloga}}</td></tr>
		            
		            <tr><td class="left">Organizacija: </td>
		            <td class="right"><input type="text" v-model="organizacija.ime" v-bind:hidden="organizacije.length>1" disabled>
		            <select v-model="noviKorisnik.organizacija" v-bind:hidden="organizacije.length<=1">    
		                <option v-for="o in organizacije">{{o.ime}}</option>
		            </select> </td><td>
		            {{greskaOrganizacija}}</td></tr>
		            
		            <tr><td class="left">Lozinka: </td> <td class="right"><input type="password" v-model="novaLozinka"></td> <td>{{greskaLozinka}}</td></tr>
		            <tr><td class="left">Ponovljena lozinka: </td> <td class="right"><input type="password" v-model="ponovljenaLozinka" v-bind:disabled="novaLozinka==''"></td> <td>{{greskaPonovljenaLozinka}}</td></tr>
		            
		            <tr><td colspan="3"><button v-on:click="dodaj()">DODAJ</button><br></td></tr>
		            <tr><td colspan="3">{{greskaServer}}<br></td></tr>
		            
		            <tr><td colspan="3"><router-link to="/korisnici">KORISNICI</router-link><br></td></tr>

    			</table>
    		
    		</div>
    		
        </div>
    
    `, 

    watch: {
        novaLozinka: function(){
            if (this.novaLozinka == '')
            this.ponovljenaLozinka = '';
        } 
     }, 

     mounted(){

        axios.get("rest/uloge/unos/pregled")
        .then(response => {
            this.uloge = response.data;
        })
        .catch(error => {
            this.$router.push("masine");
        });
        
        axios.get("rest/organizacije/pregled")
        .then(response => {
            this.organizacije = response.data;
            this.organizacija = this.organizacije.length >= 1 ? this.organizacije[0] : {}
        })
        .catch(error => {
            this.$router.push("masine");
        });

    },

    methods: {

        dodaj: function(){

            this.noviKorisnik.lozinka = this.novaLozinka;
            if (this.organizacije.length == 1) this.noviKorisnik.organizacija = this.organizacija.ime;

            this.greskaKorisnickoIme = '';
            this.greskaEmail = '';
            this.greskaIme = '';
            this.greskaPrezime = '';
            this.greskaUloga = '';
            this.greskaOrganizacija = '';
            this.greskaLozinka = '';
            this.greskaPonovljenaLozinka = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.noviKorisnik.user.korisnickoIme == ''){
                this.greskaKorisnickoIme = "Korisnicko ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.noviKorisnik.email == ''){
                this.greskaEmail = "Email ne sme biti prazan. ";
                this.greska = true;
            }
            if (this.noviKorisnik.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.noviKorisnik.prezime == ''){
                this.greskaPrezime = "Prezime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.noviKorisnik.uloga == ''){
                this.greskaUloga = "Uloga ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.noviKorisnik.organizacija == ''){
                this.greskaOrganizacija = "Organizacia ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.novaLozinka == ''){
                this.greskaLozinka = "Lozinka ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.ponovljenaLozinka != this.novaLozinka){
                this.greskaPonovljenaLozinka = "Lozinke se ne poklapaju. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/korisnici/dodavanje", this.noviKorisnik)
            .then(response => {
                this.$router.push("korisnici");
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        }

    }

});