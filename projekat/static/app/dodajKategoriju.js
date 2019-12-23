Vue.component("dodajKategoriju", {

    data: function(){
        return{
            novaKategorija: {
                "ime": '', 
                "brojJezgara": 0, 
                "RAM": 0, 
                "GPUjezgra": 0
            }, 
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

            <h1>Registracija nove kategorije</h1>
            Ime: <input type="text" v-model="novaKategorija.ime"> {{greskaIme}} <br><br>
            Broj jezgara: <input type="text" v-model="novaKategorija.brojJezgara"> {{greskaBrojJezgara}} <br><br>
            RAM: <input type="text" v-model="novaKategorija.RAM"> {{greskaRAM}} <br><br>
            GPU jezgra: <input type="text" v-model="novaKategorija.GPUjezgra"> {{greskaGPUjezgra}} <br><br>
            <button v-on:click="dodaj()">DODAJ</button><br><br>
            <router-link to="/kategorije">KATEGORIJE</router-link><br><br>
            {{greskaServer}}

        </div>
    
    `, 

    methods: {

        dodaj: function(){

            this.greskaIme = '';
            this.greskaBrojJezgara = '';
            this.greskaRAM = '';
            this.greskaGPUjezgra = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.novaKategorija.ime == ''){
                this.greskaIme = "Kategorija ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.novaKategorija.brojJezgara === '' || isNaN(this.novaKategorija.brojJezgara) || parseInt(this.novaKategorija.brojJezgara) <= 0){
                this.greskaBrojJezgara = "Broj jezgara mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (this.novaKategorija.RAM === '' || isNaN(this.novaKategorija.RAM) || parseInt(this.novaKategorija.RAM) <= 0){
                this.greskaRAM = "RAM mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (this.novaKategorija.GPUjezgra === '' || isNaN(this.novaKategorija.GPUjezgra) || parseInt(this.novaKategorija.GPUjezgra) < 0){
                this.greskaGPUjezgra = "GPU jezgra moraju biti nenegativan ceo broj. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/kategorije/dodavanje", this.novaKategorija)
            .then(response => {
                this.$router.push("kategorije");
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });
            
        }
    }

});