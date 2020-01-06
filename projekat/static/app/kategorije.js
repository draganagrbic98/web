Vue.component("kategorije", {

    data: function(){
        return{
            kategorije: [], 
            selectedKategorija: {}, 
            selectedKategorijaId: '', 
            selected: false, 
            greskaIme: '', 
            greskaBrojJezgara: '', 
            greskaRAM: '', 
            greskaGPUjezgra: '', 
            greskaServer: '', 
            greska: false
        }
    }, 

    template: `
    
        <div>

            <div v-if="selected">

                <h1>Izmena kategorije</h1>
                
                Ime: <input type="text" v-model="selectedKategorija.ime"> {{greskaIme}} <br><br>
                Broj jezgara: <input type="text" v-model="selectedKategorija.brojJezgara"> {{greskaBrojJezgara}} <br><br>
                RAM: <input type="text" v-model="selectedKategorija.RAM"> {{greskaRAM}} <br><br>
                GPU jezgra: <input type="text" v-model="selectedKategorija.GPUjezgra"> {{greskaGPUjezgra}} <br><br>
                
                <button v-on:click="izmeni()">IZMENI</button><br><br>
                <button v-on:click="obrisi()">OBRISI</button><br><br>
                {{greskaServer}}
                
            </div>

            <div v-if="!selected">

                <h1>Registrovane kategorije</h1>
                
                <table class="data" border="1">
	                <tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th></tr>
	                <tr v-for="k in kategorije" v-on:click="selectKategorija(k)">
	                    <td>{{k.ime}}</td>
	                    <td>{{k.brojJezgara}}</td>
	                    <td>{{k.RAM}}</td>
	                    <td>{{k.GPUjezgra}}</td>
	                </tr> 
                </table><br><br>
                
                <button v-on:click="dodaj()">DODAJ KATEGORIJU</button><br><br>
                <router-link to="/masine">MAIN PAGE</router-link><br><br>
                
            </div>
        </div>
    `, 

    mounted(){

        axios.get("rest/kategorije/pregled")
        .then(response => {
            this.kategorije = response.data;
        })
        .catch(error => {
            this.$router.push("masine");
        });

    }, 

    methods: {

        selectKategorija: function(kategorija){
            this.selectedKategorija = kategorija;
            this.selectedKategorijaId = kategorija.ime;
            this.selected = true;
        }, 
        
        dodaj: function(){
            this.$router.push("dodajKategoriju");
        },

        obrisi: function(){

            this.selectedKategorija.ime = this.selectedKategorijaId;
            axios.post("rest/kategorije/brisanje", this.selectedKategorija)
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
            this.greskaBrojJezgara = '';
            this.greskaRAM = '';
            this.greskaGPUjezgra = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.selectedKategorija.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno";
                this.greska = true;
            }
            if (this.selectedKategorija.brojJezgara === '' || isNaN(this.selectedKategorija.brojJezgara) || parseInt(this.selectedKategorija.brojJezgara) <= 0){
                this.greskaBrojJezgara = "Broj jezgara mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (this.selectedKategorija.RAM === '' || isNaN(this.selectedKategorija.RAM) || parseInt(this.selectedKategorija.RAM) <= 0){
                this.greskaRAM = "RAM mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (this.selectedKategorija.GPUjezgra === '' || isNaN(this.selectedKategorija.GPUjezgra) || parseInt(this.selectedKategorija.GPUjezgra) < 0){
                this.greskaGPUjezgra = "GPU jezgra moraju biti nenegativan ceo broj. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/kategorije/izmena", {"staroIme": this.selectedKategorijaId, "novaKategorija": this.selectedKategorija})
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