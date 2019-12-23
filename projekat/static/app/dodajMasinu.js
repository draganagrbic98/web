Vue.component("dodajMasinu", {

    data: function(){
        return{
            novaMasina:{
                "ime": '', 
                "organizacija": '', 
                "kategorija": {
                    "ime": '', 
                    "brojJezgara": 0, 
                    "RAM": 0, 
                    "GPUjezgra": 0
                },
                "brojJezgara": 0, 
                "RAM": 0, 
                "GPUjezgra": 0, 
                "aktivnosti": [], 
                "diskovi": []
            }, 
            greskaIme: '',
            greskaOrganizacija: '',
            greskaKategorija: '', 
            greskaServer: '',
            greska: false, 
            kategorije: [], 
            organizacije: [], 
            organizacija: {},
            kat: ''
        }
    }, 

    template:`

        <div>

            <h1>Registracija nove masine</h1>
                Ime: <input type="text" v-model="novaMasina.ime"> {{greskaIme}} <br><br>
                Organizacija: 
                <input type="text" v-model="organizacija.ime" v-bind:hidden="organizacije.length>1" disabled>
                <select v-model="novaMasina.organizacija" v-bind:hidden="organizacije.length<=1">
                <option v-for="o in organizacije">
                    {{o.ime}}
                </option>
                </select> 
                {{greskaOrganizacija}} <br><br>
                Kategorija: <select v-model="kat">
                <option v-for="k in kategorije">
                    {{k.ime}}
                    </option>
                </select> 
                {{greskaKategorija}} <br><br>
                Broj jezgara: <input type="text" v-model="novaMasina.brojJezgara" disabled><br><br>
                RAM: <input type="text" v-model="novaMasina.RAM" disabled><br><br>
                GPU jezgra: <input type="text" v-model="novaMasina.GPUjezgra" disabled><br><br>
                <button v-on:click="dodaj()">DODAJ</button><br><br>
                <router-link to="/masine">MASINE</router-link><br><br>
                {{greskaServer}}
                
        </div>
    
    `, 

    watch: {
        kat: function() {
          for (let k of this.kategorije){
              if (k.ime == this.kat){
                  this.novaMasina.kategorija.ime = k.ime;
                  this.novaMasina.kategorija.brojJezgara = k.brojJezgara;
                  this.novaMasina.kategorija.RAM = k.RAM;
                  this.novaMasina.kategorija.GPUjezgra = k.GPUjezgra;
                  this.novaMasina.brojJezgara = k.brojJezgara;
                  this.novaMasina.RAM = k.RAM;
                  this.novaMasina.GPUjezgra = k.GPUjezgra;
              }
          }

        }
    },

    mounted(){

        axios.get("rest/kategorije/unos/pregled")
        .then(response => {
            this.kategorije = response.data;
        })
        .catch(error => {
            this.$router.push("masine");
        });

        axios.get("rest/organizacije/pregled")
        .then(response => {
            this.organizacije = response.data;
            this.organizacija = this.organizacije.length >= 1 ? this.organizacije[0] : {};
        })
        .catch(error => {
            this.$router.push("masine");
        });

    },

    methods: {

        dodaj: function(){

            if (this.organizacije.length == 1) this.novaMasina.organizacija = this.organizacija.ime;

            this.greskaIme = '';
            this.greskaOrganizacija = '';
            this.greskaKategorija = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.novaMasina.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.novaMasina.organizacija == ''){
                this.greskaOrganizacija = "Organizacija ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.novaMasina.kategorija == '' || this.kat == ''){
                this.greskaKategorija = "Kategorija ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.greska == true) return;

            axios.post("rest/masine/dodavanje", this.novaMasina)
            .then(response => {
                this.$router.push("masine");
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            })
        }, 
       
    }, 

});