Vue.component("korisnici", {

    data: function(){
        return{
            korisnici: [], 
            selectedKorisnik: {}, 
            selectedKorisnikId: '', 
            selected: false, 
            greskaIme: '', 
            greskaPrezime: '', 
            greskaServer: '',
            greska: false, 
            uloge: []
        }
    }, 

    template: `
    
        <div>

            <div v-if="selected">
                Email: <input type="text" v-model="selectedKorisnik.email" disabled> <br><br>
                Ime: <input type="text" v-model="selectedKorisnik.ime"> {{greskaIme}} <br><br>
                Prezime: <input type="text" v-model="selectedKorisnik.prezime"> {{greskaPrezime}} <br><br>
                Uloga: <select v-model="selectedKorisnik.uloga" v-bind:hidden="selectedKorisnik.uloga=='SUPER_ADMIN'">
                    <option v-for="u in uloge">
                        {{u}}
                    </option>
                </select>
                <input type="text" v-model="selectedKorisnik.uloga" disabled v-bind:hidden="selectedKorisnik.uloga!='SUPER_ADMIN'">
                <br><br>
                Organizacija: <input type="text" v-model="selectedKorisnik.organizacija" disabled><br><br>
                <button v-on:click="izmeni()">Izmeni</button><br><br>
                <button v-on:click="obrisi()">Obrisi</button><br><br>
                {{greskaServer}}
            </div>

            <div v-if="!selected">
                <h1>Registrovani korisnici</h1>
                <table border="1">
                <tr><th>Email</th><th>Ime</th><th>Prezime</th><th>Organizacija</th></tr>
                <tr v-for="k in korisnici" v-on:click="selectKorisnik(k)">
                    <td>{{k.email}}</td>
                    <td>{{k.ime}}</td>
                    <td>{{k.prezime}}</td>
                    <td>{{k.organizacija}}</td>
                </tr>
                </table><br><br>
                <button v-on:click="dodaj()">Dodaj korisnika</button><br><br>
                <router-link to="/masine">MAIN PAGE</router-link>
            </div>

        </div>
    
    `, 

    mounted(){

        axios.get("rest/korisnici/pregled")
        .then(response => {
            this.korisnici = response.data;
        })
        .catch(error => {
            this.$router.push("masine");
        });

        axios.get("rest/uloge/unos/pregled")
        .then(response => {
            this.uloge = response.data;
        });



    }, 

    methods: {

        selectKorisnik: function(korisnik){
            this.selectedKorisnik = korisnik;
            this.selectedKorisnikId = korisnik.user.korisnickoIme;
            this.selected = true;
        }, 

        dodaj: function(){
            this.$router.push("dodajKorisnika");

        }, 

        obrisi: function(){

            this.selectedKorisnik.user.korisnickoIme = this.selectedKorisnikId;
            axios.post("rest/korisnici/brisanje", this.selectedKorisnik)
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        }, 

        izmeni: function(){

            this.greskaIme = '';
            this.greskaPrezime = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.selectedKorisnik.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.selectedKorisnik.prezime == ''){
                this.greskaPrezime = "Prezime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/korisnici/izmena", {"staroIme": this.selectedKorisnikId, "noviKorisnik": this.selectedKorisnik})
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        }

    }

});