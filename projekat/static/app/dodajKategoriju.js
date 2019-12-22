Vue.component("dodajKategoriju", {

    data: function(){
        return{
            novaKategorija: {
                "ime": '', 
                "brojJezgara": 1, 
                "RAM": 1, 
                "GPUjezgra": 0
            }, 
            greska: false, 
            greskaIme: '', 
            greskaBrojJezgara: '', 
            greskaRAM: '', 
            greskaGPUjezgra: ''
        }
    }, 

    template: `

        <div>

            <h1>Registracija nove kategorije</h1>
            Ime: <input type="text" v-model="novaKategorija.ime"> {{greskaIme}} <br><br>
            Broj jezgara: <input type="text" v-model="novaKategorija.brojJezgara"> {{greskaBrojJezgara}} <br><br>
            RAM: <input type="text" v-model="novaKategorija.RAM"> {{greskaRAM}} <br><br>
            GPU jezgra: <input type="text" v-model="novaKategorija.GPUjezgra"> {{greskaGPUjezgra}} <br><br>
            <button v-on:click="dodaj()">Dodaj</button><br><br>

        </div>
    
    `, 

    methods: {
        dodaj: function(){

            this.greskaBrojJezgara = '';
            this.greskaRAM = '';
            this.greskaGPUjezgra = '';
            this.greska = false;
            this.greskaIme = '';

            if (isNaN(this.novaKategorija.brojJezgara) || parseInt(this.novaKategorija.brojJezgara) <= 0){
                this.greskaBrojJezgara = "Broj jezgara mora biti pozitivan ceo broj. ";
                this.greska = true;
            }

            if (isNaN(this.novaKategorija.RAM) || parseInt(this.novaKategorija.RAM) <= 0){
                this.greskaRAM = "RAM mora biti pozitivan ceo broj. ";
                this.greska = true;
            }

            if (isNaN(this.novaKategorija.GPUjezgra) || parseInt(this.novaKategorija.GPUjezgra) < 0){
                this.greskaGPUjezgra = "GPU jezgra mora biti nenegativan ceo broj. ";
                this.greska = true;
            }

            if (this.novaKategorija.ime == ''){
                this.greskaIme = "Ime kategorije ne sme biti prazno";
                this.greska = true;
            }

            if (this.greska == true) return;

            axios.post("rest/kategorije/dodavanje", this.novaKategorija)
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("kategorije");
                }
                else{
                    this.greska = "Uneta kategorija vec postoji. Ponovo. ";
                }
            });
            
        }
    }

})