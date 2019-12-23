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
            organizacije: []
        }
    }, 

    

    template: `

        <div>

            <h1>Registracija novog korisnika</h1>
            Korisnicko ime: <input type="text" v-model="noviKorisnik.user.korisnickoIme"> {{greskaKorisnickoIme}} <br><br>
            Email: <input type="text" v-model="noviKorisnik.email"> {{greskaEmail}} <br><br>
            Ime: <input type="text" v-model="noviKorisnik.ime"> {{greskaIme}} <br><br>
            Prezime: <input type="text" v-model="noviKorisnik.prezime"> {{greskaPrezime}} <br><br>
            Uloga: <select v-model="noviKorisnik.uloga"> 
                <option v-for="u in uloge">{{u}}</option>
            </select> {{greskaUloga}} <br><br>
            Organizacija: 
            <input type="text" value="organizacije[0]" disabled v-model="noviKorisnik.organizacija" v-bind:hidden="organizacije.length>1">
            <select v-model="noviKorisnik.organizacija" v-bind:hidden="organizacije.length<=1">    
                <option v-for="o in organizacije">{{o.ime}}</option>
            </select> {{greskaOrganizacija}} <br><br>
            Lozinka: <input type="password" v-model="novaLozinka"> {{greskaLozinka}} <br><br>
            Ponovljena lozinka: <input type="password" v-model="ponovljenaLozinka" v-bind:disabled="novaLozinka==''"> {{greskaPonovljenaLozinka}} <br><br>
            <button v-on:click="dodaj()">Dodaj</button><br><br>
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

        dodaj: function(){

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

            this.noviKorisnik.lozinka = this.novaLozinka;
            axios.post("rest/korisnici/dodavanje", this.noviKorisnik)
            .then(response => {
                this.$router.push("korisnici");
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        }

    },

    mounted(){

        axios.get("rest/organizacije/pregled")
        .then(response => {
            this.organizacije = response.data;
        })
        .catch(error => {
            this.$router.push("masine");
        });

        axios.get("rest/uloge/unos/pregled")
        .then(response => {
            this.uloge = response.data;
        });

    }

})