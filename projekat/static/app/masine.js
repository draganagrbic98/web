Vue.component("masine", {

    data: function(){
        return {
            masine: [], 
            selectedMasina: {}, 
            selectedMasinaId: '',
            selected: false, 
            greskaIme: '',
            greskaServer: '', 
            greska: false, 
            kategorije: [], 
            uloga: '', 
            kat: '', 
            backup: [],
            pretragaIme: '',
            pretragaBrojJezgara: '', 
            pretragaRAM: '', 
            pretragaGPUjezgra: ''
        }
    }, 

    template: `

        <div>

            <div v-if="selected">

                Ime: <input type="text" v-model="selectedMasina.ime" v-bind:disabled="uloga=='KORISNIK'"> {{greskaIme}} <br><br>
                Organizacija: <input type="text" v-model="selectedMasina.organizacija" disabled><br><br>
                Kategorija: <select v-model="kat" v-bind:disabled="uloga=='KORISNIK'">
                    <option v-for="k in kategorije">
                        {{k.ime}}
                    </option>
                </select><br><br>
                Broj jezgara: <input type="text" v-model="selectedMasina.brojJezgara" disabled><br><br>
                RAM: <input type="text" v-model="selectedMasina.RAM" disabled><br><br>
                Broj GPU jezgara: <input type="text" v-model="selectedMasina.GPUjezgra" disabled><br><br>
                Aktivnosti: 
                <p v-if="selectedMasina.aktivnosti.length==0">NEMA</p>
                <ol>
                    <li v-for="a in selectedMasina.aktivnosti">{{a.datum}} {{a.upaljen}}</li>
                </ol><br>
                Diskovi: 
                <p v-if="selectedMasina.diskovi.length==0">NEMA</p>
                <ol>
                    <li v-for="d in selectedMasina.diskovi">{{d}}</li>
                </ol><br>
                <div v-if="uloga!='KORISNIK'">
	                <button v-on:click="izmeni()">Izmeni</button><br><br>
	                <button v-on:click="obrisi()">Obrisi</button>
                </div>
                {{greskaServer}}

            </div>
        
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
                	<button v-on:click="dodaj()">Dodaj masinu</button>
                	<router-link to="/korisnici">Korisnici</router-link><br><br>
                </div>

                <div v-if="uloga=='SUPER_ADMIN'">
                    <router-link to="/kategorije">Kategorije</router-link><br><br>
                    <router-link to="/organizacije">Organizacije</router-link><br><br>
    			</div>
    			
               	<router-link to="/diskovi">Diskovi</router-link><br><br>
    			<router-link to="/profil">Profil</router-link><br><br>
                <button v-on:click="logout()">Odjava</button><br><br>

                <h1>Pretraga</h1>
                Ime: <input type="text" v-model="pretragaIme"><br><br>
                Broj jezgara: <input type="number" min="1" v-model="pretragaBrojJezgara"><br><br>
                RAM: <input type="number" min="1" v-model="pretragaRAM"><br><br>
                GPU jezgra: <input type="number" min="0" v-model="pretragaGPUjezgra"><br><br>
                <button v-on:click="pretrazi()">Filtriraj</button><br><br>

            </div>

        </div>
    `, 

    mounted(){

        axios.get("rest/masine/pregled")
        .then(response => {
            this.masine = response.data;
            this.backup = response.data;
        })
        .catch(error => {
            this.$router.push("/");
        });

        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
        });

        

        axios.get("rest/kategorije/unos/pregled")
        .then(response => {
            this.kategorije = response.data;
        });

    },

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

        pretrazi: function(){

            this.masine = [];
            for (let m of this.backup){
                imePassed = (this.pretragaIme != '') ? (m.ime == this.pretragaIme) : true;
                brojJezgaraPassed = (this.pretragaBrojJezgara != '') ? (m.brojJezgara == this.pretragaBrojJezgara) : true;
                RAMPassed = (this.pretragaRAM != '') ? (m.RAM == this.pretragaRAM) : true;
                GPUjezgraPassed = (this.pretragaGPUjezgra != '') ? (m.GPUjezgra == this.pretragaGPUjezgra) : true;
                if (imePassed && brojJezgaraPassed && RAMPassed && GPUjezgraPassed) this.masine.push(m);
            }

        },

        selectMasina: function(masina){
            this.selectedMasina = masina;
            this.selectedMasinaId = masina.ime;
            this.selected = true;
            this.kat = this.selectedMasina.kategorija.ime;
        }, 

        izmeni: function(){

            this.greskaIme = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.selectedMasina.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/masine/izmena", {"staroIme": this.selectedMasinaId, "novaMasina": this.selectedMasina})
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        },

        dodaj: function(){
            this.$router.push("dodajMasinu");
        },

        obrisi: function(){
            axios.post("rest/masine/brisanje", this.selectedMasina)
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });
        },

        logout: function(){
            axios.get("rest/user/logout")
            .then(response => {
                this.$router.push("/");
            });
        }
        
    }

});