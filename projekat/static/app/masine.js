Vue.component("masine", {

    data: function(){
        return {
        	diskovi: [],
            masine: [], 
            selectedMasina: {}, 
            selectedMasinaId: '',
            selectedMasinaStatus: '',
            selected: false, 
            pretragaIme: '',
            pretragaBrojJezgara: '', 
            pretragaRAM: '', 
            pretragaGPUjezgra: '',
            greskaIme: '',
            greskaServer: '', 
            greska: false, 
            greskaPocetni: '',
            greskaKrajnji: '',
            pocetniDatum: '',
            krajnjiDatum: '',
            prikaziRacun: false,
            racun: {
            	'racuniMasine' : {},
            	'racuniDiskovi' : {},
            	'ukupniRacun' : ''
            },
            kategorije: [], 
            backup: [],
            uloga: '', 
            kat: ''
        }
    }, 

    template: `

        <div>

            <div v-if="selected && !prikaziRacun">

                <h1>Izmena masine</h1>
                
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
                
                <table v-if="selectedMasina.aktivnosti.length!=0">
                	<th>Datum paljenja</th><th>Datum Gasenja</th>
                	
                	<tr v-for="a in selectedMasina.aktivnosti">
                		<td>{{a.datumPaljenja}}</td> <td>{{a.datumGasenja}}</td>
                	</tr>
                </table><br><br>
                
                Status: {{selectedMasinaStatus}}<br><br>
                
                Diskovi: 
                <p v-if="selectedMasina.diskovi.length==0">NEMA</p>
                
                <ol>
                    <li v-for="d in selectedMasina.diskovi">{{d}}</li>
                </ol><br>
                
                <div v-if="uloga=='SUPER_ADMIN'">
               		<button v-if="selectedMasinaStatus == 'UGASENA'" v-on:click="promeni_status()">UPALI MASINU</button>
                	<button v-if="selectedMasinaStatus == 'UPALJENA'" v-on:click="promeni_status()">UGASI MASINU</button>
                </div><br><br>
                
                <div v-if="uloga!='KORISNIK'">
	                <button v-on:click="izmeni()">IZMENI</button><br><br>
	                <button v-on:click="obrisi()">OBRISI</button><br><br>
                </div>
                
                <button v-on:click="vratiNaMasine">POVRATAK</button><br><br>

                {{greskaServer}}

            </div>
        
            <div v-if="!selected && !prikaziRacun">

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

                <h1>Pretraga</h1>
                
                Ime: <input type="text" v-model="pretragaIme"><br><br>
                Broj jezgara: <input type="number" min="1" v-model="pretragaBrojJezgara"><br><br>
                RAM: <input type="number" min="1" v-model="pretragaRAM"><br><br>
                GPU jezgra: <input type="number" min="0" v-model="pretragaGPUjezgra"><br><br>
                
                <button v-on:click="pretrazi()">FILTRIRAJ</button><br><br>

                <span v-if="uloga!='KORISNIK'">
                	<button v-on:click="dodaj()">DODAJ MASINU</button><br><br>
                	<router-link to="/korisnici">KORISNICI</router-link> &nbsp&nbsp <br><br>
                </span>

    			<div v-if="uloga=='ADMIN'">
    			    Pocetni datum: <input type="date" v-model="pocetniDatum"> {{greskaPocetni}} <br><br>
                	Krajnji datum: <input type="date" v-model="krajnjiDatum"> {{greskaKrajnji}} <br><br>
                	<button v-on:click="izracunajRacun">PRIKAZI RACUN</button><br><br>
    			</div>

                <span v-if="uloga=='SUPER_ADMIN'">
                    <router-link to="/kategorije">KATEGORIJE</router-link> &nbsp&nbsp
                    <router-link to="/organizacije">ORGANIZACIJE</router-link> &nbsp&nbsp
    			</span>

                <router-link to="/diskovi">DISKOVI</router-link> &nbsp&nbsp
                <router-link to="/profil">PROFIL</router-link><br><br>
                <button v-on:click="logout()">ODJAVA</button><br><br>

            </div>
            
            <div v-if="!selected && prikaziRacun">

                <h1>Pregled racuna za odabrani period</h1>
                
                <table border="1">
	                <tr><th>Tip uredjaja</th><th>Ime</th><th>Racun</th></tr>
	                
	                <tr v-for="m in masine">
	                    <td>Virtuelna masina</td><td>{{m.ime}}</td><td>{{racun.racuniMasine[m.ime]}}</td>
	                </tr>
	                
	                <tr v-for="d in diskovi">
	                    <td>Disk</td><td>{{d.ime}}</td><td>{{racun.racuniDiskovi[d.ime]}}</td>
	                </tr>
                </table><br><br>
                
                Ukupan racun: <input type="text" v-model="racun.ukupniRacun" disabled><br><br>
                
                <button v-on:click="vratiNaMasine">POVRATAK</button><br><br>
            </div>

        </div>
    `, 

    watch: {
        kat: function(){
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

    mounted(){

        axios.get("rest/masine/pregled")
        .then(response => {
            this.masine = response.data;
            this.backup = response.data;
        })
        .catch(error => {
            this.$router.push("/");
        });
        
        axios.get("rest/diskovi/pregled")
        .then(response => {
            this.diskovi = response.data;
        })
        .catch(error => {
            this.$router.push("/");
        });

        axios.get("rest/kategorije/unos/pregled")
        .then(response => {
            this.kategorije = response.data;
        })
        .catch(error => {
            this.$router.push("/");
        });

        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
        })
        .catch(error => {
            this.$router.push("/");
        });

    },

    methods: {

        pretrazi: function(){

            this.masine = [];
            for (let m of this.backup){
                let imePassed = (this.pretragaIme != '') ? (m.ime.includes(this.pretragaIme)) : true;
                let brojJezgaraPassed = (this.pretragaBrojJezgara != '') ? (m.brojJezgara == this.pretragaBrojJezgara) : true;
                let RAMPassed = (this.pretragaRAM != '') ? (m.RAM == this.pretragaRAM) : true;
                let GPUjezgraPassed = (this.pretragaGPUjezgra != '') ? (m.GPUjezgra == this.pretragaGPUjezgra) : true;
                if (imePassed && brojJezgaraPassed && RAMPassed && GPUjezgraPassed) this.masine.push(m);
            }

        },

        selectMasina: function(masina){
            this.selectedMasina = masina;
            this.selectedMasinaId = masina.ime;
            this.selected = true;
            this.kat = this.selectedMasina.kategorija.ime;
            
            axios.post("rest/masine/status", this.selectedMasinaId)
            .then(response => {
                this.selectedMasinaStatus = response.data;
            })
            .catch(error => {
                this.$router.push("/");
            });
        }, 

        dodaj: function(){
            this.$router.push("dodajMasinu");
        },

        obrisi: function(){
            this.selectMasina.ime = this.selectedMasinaId;
            axios.post("rest/masine/brisanje", this.selectedMasina)
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
            this.greskaServer = '';
            this.greska = false;

            if (this.selectedMasina.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno.";
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

        izracunajRacun: function(){

            this.greskaPocetni = '';
            this.greskaKrajnji = '';
            this.greska = false;

            if (this.pocetniDatum == ''){
                this.greskaPocetni = "Ovo polje ne sme biti prazno.";
                this.greska = true;
            }
            
            if (this.krajnjiDatum == ''){
                this.greskaKrajnji = "Ovo polje ne sme biti prazno.";
                this.greska = true;
            }

            if (this.greska) return;

            var y1 = this.pocetniDatum.substr(0,4),
                m1 = this.pocetniDatum.substr(5,2) - 1,
                d1 = this.pocetniDatum.substr(8,2);
            
            var pocetni = new Date(y1,m1,d1);
            
            var y2 = this.krajnjiDatum.substr(0,4),
            	m2 = this.krajnjiDatum.substr(5,2) - 1,
            	d2 = this.krajnjiDatum.substr(8,2);
        
            var krajnji = new Date(y2,m2,d2);
            
            if (pocetni > krajnji) {
                this.greskaPocetni = "Pocetni datum mora biti pre krajnjeg.";
                this.greska = true;
            }
            else if (this.pocetniDatum == this.krajnjiDatum) {
                this.greskaPocetni = "Pocetni i krajnji datum moraju biti razliciti.";
                this.greska = true;
            }
            
            if (this.greska) return;

            axios.post("rest/masine/izracunajRacun", {"pocetniDatum": pocetni.getTime(), "krajnjiDatum": krajnji.getTime()})
            .then(response => {
                this.prikaziRacun = true;
            	this.racun = response.data;
            })
            .catch(error => {
                this.greskaServer = "CRITICAL SERVER ERROR";
            });
            
        },
        
        vratiNaMasine: function() {
        	this.prikaziRacun = false;
        	this.selected = false;
        },
        
        promeni_status: function() {
            axios.post("rest/masine/promeni_status", {"staroIme": this.selectedMasinaId, "novaMasina": this.selectedMasina})
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
            })
            .catch(error => {
                this.$router.push("/");
            });
        }
        
    }

});