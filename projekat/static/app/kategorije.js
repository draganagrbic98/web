Vue.component("kategorije", {

    data: function(){
        return{
            kategorije: [], 
            selectedKategorija: {}, 
            selectedKategorijaId: '', 
            selected: false, 
            uloga: '', 
            greska: false, 
            greskaIme: '', 
            greskaBrojJezgara: '', 
            greskaRAM: '', 
            greskaGPUjezgra: '', 
            greskaUnos: ''
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

                <button v-on:click="dodaj()">Dodaj</button><br><br>
                
            	<router-link to="/masine">Masine</router-link>
            </div>

        </div>
    
    `, 

    mounted(){

        axios.get("rest/kategorije/pregled")
        .then(response => {
            this.kategorije = response.data;
        });


    }, 

    methods: {
        selectKategorija: function(kategorija){
            this.selectedKategorija = kategorija;
            this.selected = true;
            this.selectedKategorijaId = kategorija.ime;

        }, 

        izmeni: function(){

            this.greskaBrojJezgara = '';
            this.greskaRAM = '';
            this.greskaGPUjezgra = '';
            this.greska = false;
            this.greskaIme = '';
            this.greskaUnos = '';

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

            if (this.selectedKategorija.ime == ''){
                this.greskaIme = "Ime kategorije ne sme biti prazno";
                this.greska = true;
            }

            if (this.greska == true) return;

            axios.post("rest/kategorije/izmena", {"staroIme": this.selectedKategorijaId, "novaKategorija": this.selectedKategorija})
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();
                }
                else{
                    this.greskaUnos = "Uneta kategorija vec postoji. ";
                    return;

                }
            })
        }, 

        obrisi: function(){
            axios.post("rest/kategorije/brisanje", this.selectedKategorija)
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();
                }
            })
        }, 

        dodaj: function(){
            this.$router.push("dodajKategoriju");
        }

    }

})