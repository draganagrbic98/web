Vue.component("masine", {
    data: function(){
        return {
            masine: [], 
            selectedMasinaId: '',
            selectedMasina: {}, 
            selected: false, 
            uloga: '', 
            kategorije: [], 
            kat: '', 
            greskaIme: '',
            greskaOrganizacija: '', 
            greskaKategorija: '', 
            greskaUnos: '', 
            greska: false
            
        }
    }, 

    template: `
        <div>
        
            <div v-if="!selected">
                <h1>Registrovane masine</h1>
                <table border="1">
                <tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th><th>Organizacija</th></tr>
                <tr v-for="m in masine" v-on:click="selectMasina(m)">
                    <td>{{m.ime}}</td>
                    <td>{{m.brojJezgara}}</td>
                    <td>{{m.RAM}}</td>
                    <td>{{m.GPUjezgra}}</td>
                    <td>{{m.organizacija}}</td>
                </tr>
                </table><br><br>

                <div v-if="uloga!='KORISNIK'">
                	<button v-on:click="dodaj()">Dodaj</button>
                </div>

                <div v-if="uloga=='SUPER_ADMIN'">
                	<router-link to="/organizacije">Organizacije</router-link> <br>
                	<router-link to="/kategorije"> Kategorije</router-link>
    			</div>
    			
               	<router-link to="/diskovi">Diskovi</router-link> <br>

    			<router-link to="/profil">Profil</router-link>

                <div>
                	<button v-on:click="logout()">Odjava</button>
                </div>
            </div>

            <div v-if="selected">
                Ime: <input type="text" v-model="selectedMasina.ime" v-bind:disabled="uloga=='KORISNIK'"> {{greskaIme}} <br><br>

                Organizacija: <input type="text" v-model="selectedMasina.organizacija" disabled> {{greskaOrganizacija}} <br><br>

                Kategorija: <select v-model="kat" v-bind:disabled="uloga=='KORISNIK'">
                    <option v-for="k in kategorije">
                        {{k.ime}}
                    </option>
                </select> {{greskaKategorija}} <br><br>

                Broj jezgara: <input type="text" v-model="selectedMasina.brojJezgara" disabled><br><br>
                RAM: <input type="text" v-model="selectedMasina.RAM" disabled><br><br>
                Broj GPU jezgara: <input type="text" v-model="selectedMasina.GPUjezgra" disabled><br><br>
                Diskovi: 
                <p v-if="selectedMasina.diskovi.length==0">NEMA</p>
                <ol>
                    <li v-for="d in selectedMasina.diskovi">{{d}}</li>

                </ol><br>
                Aktivnosti: 
                <p v-if="selectedMasina.aktivnosti.length==0">NEMA</p>
                <ol>

                    <li v-for="a in selectedMasina.aktivnosti">{{a.datum}} {{a.upaljen}}</li>

                </ol><br>

                <div v-if="uloga!='KORISNIK'">
	                <button v-on:click="izmeni()">Izmeni</button><br><br>
	                <button v-on:click="obrisi()">Obrisi</button>
                </div>

                {{greskaUnos}}

            </div>

        </div>
    `, 

    watch: {
        kat: function(oldKat, newKat){
            for (let k of this.kategorije){
                if (k.ime == this.kat){
                    this.selectedMasina.kategorija.ime = k.ime;
                    this.selectedMasina.kategorija.brojJezgara = k.brojJezgara;
                    this.selectedMasina.kategorija.RAM = k.RAM;
                    this.selectedMasina.kategorija.GPUjezgra = k.GPUjezgra;
                    this.selectedMasina.brojJezgara = k.brojJezgara;
                    this.selectedMasina.RAM = k.RAM;
                    this.selectedMasina.GPUjezgra = k.GPUjezgra;
                }
            }
        }
    },

    methods: {
        selectMasina: function(masina){
            this.selectedMasina = masina;
            this.selected = true;
            this.selectedMasinaId = masina.ime;
            this.kat = this.selectedMasina.kategorija.ime;
        }, 

        izmeni: function(){

            this.greskaIme = '';
            this.greskaOrganizacija = '';
            this.greskaKategorija = '';
            this.greskaUnos = '';
            this.greska = false;

            if (this.selectedMasina.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno";
                this.greska = true;
            }

            if (this.selectedMasina.organizacija == ''){
                this.greskaOrganizacija = "Organizacija ne sme biti prazna";
                this.greska = true;
            }

            if (this.selectedMasina.kategorija == '' || this.kat == ''){
                this.greskaKategorija = "Kategorija ne sme biti prazna. ";
                this.greska = true;
            }

            if (this.greska == true) return;

            axios.post("rest/masine/izmena", {"staroIme": this.selectedMasinaId, "novaMasina": this.selectedMasina})
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();

                }
                else{
                    this.greskaUnos = "Uneta masina vec postoji. ";
                    this.greska = true;
                    return;
 
                }
            });
        },

        dodaj: function(){
            this.$router.push("dodajMasinu");
        },

        obrisi: function(){
            axios.post("rest/masine/brisanje", this.selectedMasina)
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();
                }
            });
        },
        
        logout: function(){
            axios.post("rest/user/logout")
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("/");
                }
            });
        }
    }, 

    mounted(){

        axios.get("rest/masine/pregled")
            .then(response => {
                this.masine = response.data;
        });

        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
            if (this.uloga == null){
                this.$router.push("/");
            }
            
        });

        

        axios.get("rest/kategorije/unos/pregled")
        .then(response => {
            this.kategorije = response.data;
        });

    }

})