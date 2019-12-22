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
            greskaUnos: '', 
            greska: false
        }
    }, 

    template: `
    
        <div>

            <div v-if="selected">

                Ime: <input type="text" v-model="selectedKategorija.ime"> {{greskaIme}} <br><br>
                Broj jezgara: <input type="text" v-model="selectedKategorija.brojJezgara"> {{greskaBrojJezgara}} <br><br>
                RAM: <input type="text" v-model="selectedKategorija.RAM"> {{greskaRAM}} <br><br>
                GPU jezgra: <input type="text" v-model="selectedKategorija.GPUjezgra"> {{greskaGPUjezgra}} <br><br>
                <button v-on:click="izmeni()">Izmeni</button><br><br>
                <button v-on:click="obrisi()">Obrisi</button><br><br>
                {{greskaUnos}}
                
            </div>

            <div v-if="!selected">

                <h1>Registrovane kategorije</h1>
                <table border="1">
                    <tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th></tr>
                    <tr v-for="k in kategorije" v-on:click="selectKategorija(k)">
                        <td>{{k.ime}}</td>
                        <td>{{k.brojJezgara}}</td>
                        <td>{{k.RAM}}</td>
                        <td>{{k.GPUjezgra}}</td>
                    </tr> 
                </table><br><br>

                <button v-on:click="dodaj()">Dodaj kategoriju</button><br><br>
                <router-link to="/masine">MAIN PAGE</router-link>
                
            </div>
        </div>
    `, 

    mounted(){

        axios.get("rest/kategorije/pregled")
        .then(response => {
            this.kategorije = response.data;
        })
        .catch(response => {
            this.$router.push("masine");
        });

    }, 

    methods: {

        selectKategorija: function(kategorija){
            this.selectedKategorija = kategorija;
            this.selectedKategorijaId = kategorija.ime;
            this.selected = true;
        }, 

        izmeni: function(){

            this.greskaIme = '';
            this.greskaBrojJezgara = '';
            this.greskaRAM = '';
            this.greskaGPUjezgra = '';
            this.greskaUnos = '';
            this.greska = false;

            if (this.selectedKategorija.ime == ''){
                this.greskaIme = "Ime kategorije ne sme biti prazno";
                this.greska = true;
            }

            if (isNaN(this.selectedKategorija.brojJezgara) || parseInt(this.selectedKategorija.brojJezgara) <= 0){
                this.greskaBrojJezgara = "Broj jezgara mora biti pozitivan ceo broj. ";
                this.greska = true;
            }

            if (isNaN(this.selectedKategorija.RAM) || parseInt(this.selectedKategorija.RAM) <= 0){
                this.greskaRAM = "RAM mora biti pozitivan ceo broj. ";
                this.greska = true;
            }

            if (isNaN(this.selectedKategorija.GPUjezgra) || parseInt(this.selectedKategorija.GPUjezgra) < 0){
                this.greskaGPUjezgra = "GPU jezgra mora biti nenegativan ceo broj. ";
                this.greska = true;
            }

            if (this.greska) return;

            axios.post("rest/kategorije/izmena", {"staroIme": this.selectedKategorijaId, "novaKategorija": this.selectedKategorija})
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaUnos = error.response.data.result;
            });

        }, 

        obrisi: function(){

            this.selectedKategorija.ime = this.selectedKategorijaId;
            axios.post("rest/kategorije/brisanje", this.selectedKategorija)
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaUnos = error.response.data.result;
            });

        }, 

        dodaj: function(){
            this.$router.push("dodajKategoriju");
        }

    }

})