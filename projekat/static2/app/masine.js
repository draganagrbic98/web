Vue.component("masine", {
    data: function(){
        return {
            masine: [], 
            selectedMasinaId: '',
            selectedMasina: {}, 
            selected: false, 
            uloga: '', 
            organizacije: [], 
            kategorije: [], 
            kat: ''
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


            </div>

            <div v-if="selected">
                {{uloga}}
                Ime: <input type="text" v-model="selectedMasina.ime" v-bind:disabled="uloga=='KORISNIK'"><br><br>

                Organizacija: <select v-model="selectedMasina.organizacija" v-bind:disabled="uloga=='KORISNIK'">
                    <option v-for="o in organizacije">
                        {{o.ime}}
                    </option>
                </select>
                <br><br>

                Kategorija: <select v-model="kat" v-bind:disabled="uloga=='KORISNIK'">

                    <option v-for="k in kategorije">
                        {{k.ime}}

                    </option>
                </select><br><br>

                Broj jezgara: <input type="text" v-model="selectedMasina.brojJezgara" disabled><br><br>
                RAM: <input type="text" v-model="selectedMasina.RAM" disabled><br><br>
                Broj GPU jezgara: <input type="text" v-model="selectedMasina.GPUjezgra" disabled><br><br>
        
                <div v-if="uloga!='KORISNIK'">

                <button v-on:click="izmeni()">Izmeni</button><br><br>
                <button v-on:click="obrisi()">Obrisi</button>
                </div>



            </div>

            <div v-if="uloga=='SUPER_ADMIN'">
                <router-link to="/organizacije"> Organizacije</router-link>
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
            axios.post("rest/masine/izmena", {"staroIme": this.selectedMasinaId, "novaMasina": this.selectedMasina})
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();

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

        axios.get("rest/organizacije/unos/pregled")
        .then(response => {
            this.organizacije = response.data;
        });

        axios.get("rest/kategorije/unos/pregled")
        .then(response => {
            this.kategorije = response.data;
        });

    }

})