Vue.component("dodajMasinu", {

    data: function(){
        return{
            uloga: '', 
            kat: '',
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
            greska: '', 
            kategorije: [], 
            organizacije: [], 
            greskaIme: '',
            greskaOrganizacija: '',
            greskaKategorija: '', 
            greskaUnos: '',
            greska: false

        }
    }, 

    template:`

        <div>

            

            <h1>Registracija nove masine</h1>
                Ime: <input type="text" v-model="novaMasina.ime"> {{greskaIme}} <br><br>
                Organizacija: <select v-model="novaMasina.organizacija">

                <option v-for="o in organizacije">
                    {{o.ime}}
                </option>
                </select> {{greskaOrganizacija}} <br><br>

                Kategorija: <select v-model="kat">



                <option v-for="k in kategorije">
                    {{k.ime}}
                    </option>
                </select> {{greskaKategorija}} <br><br>
                Broj jezgara: <input type="text" v-model="novaMasina.brojJezgara" disabled><br><br>
                RAM: <input type="text" v-model="novaMasina.RAM" disabled><br><br>
                GPU jezgra: <input type="text" v-model="novaMasina.GPUjezgra" disabled><br><br>
                <button v-on:click="dodaj()">Dodaj</button><br><br>
                {{greskaUnos}}
                
        </div>
    
    `, 

    watch: {
        kat: function() {
          //novaMasina.kategorija = kat;
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

    methods: {
        dodaj: function(){


            this.greskaIme = '';
            this.greskaOrganizacija = '';
            this.greskaKategorija = '';
            this.greskaUnos = '';
            this.greska = false;

            if (this.novaMasina.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno";
                this.greska = true;
            }

            if (this.novaMasina.organizacija == ''){
                this.greskaOrganizacija = "Organizacija ne sme biti prazna";
                this.greska = true;
            }

            if (this.novaMasina.kategorija == '' || this.kat == ''){
                this.greskaKategorija = "Kategorija ne sme biti prazna. ";
                this.greska = true;
            }

            if (this.greska == true) return;

            axios.post("rest/masine/dodavanje", this.novaMasina)
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("masine");
                }
                else{
                    this.greska = "Uneta masina vec postoji. ";
                    this.greska = true;
                    return;
                }
            });
        }, 

        
       
    }, 

    

    mounted(){

        axios.get("rest/kategorije/unos/pregled")
        .then(response => {
            this.kategorije = response.data;
        });

        axios.get("rest/organizacije/unos/pregled")
        .then(response => {
            this.organizacije = response.data;
        });

        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
        });

        

    }

})