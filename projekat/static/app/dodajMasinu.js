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
            diskovi: [],
            kategorije: [], 
            organizacije: [], 
            organizacija: {},
            kat: ''
        }
    }, 

    template:`

        <div class="dodavanje">

            <h1>Registracija nove masine</h1>
            
            <br>
            
            <div>
            
            	<table>
            	
	                <tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="novaMasina.ime"></td> <td>{{greskaIme}}</td></tr>
	                
	                <tr><td class="left">Organizacija: </td>
	                <td class="right"><input type="text" v-model="organizacija.ime" v-bind:hidden="organizacije.length>1" disabled>
	                <select v-model="novaMasina.organizacija" v-bind:hidden="organizacije.length<=1">
		                <option v-for="o in organizacije">
		                    {{o.ime}}
		                </option>
	                </select></td> 
	                
	                <td class="right">{{greskaOrganizacija}}</td></tr>
	                
	                <tr><td class="left">Diskovi: </td>
	                <td class="right"><select v-model="novaMasina.diskovi" multiple>
		                <option v-for="d in diskovi">
		                    {{d.ime}}
		                </option>
	                </select></td></tr>	                
	                
	                <tr><td class="left">Kategorija: </td>
	                <td class="right"><select v-model="kat">
	                	<option v-for="k in kategorije">
	                    	{{k.ime}}
	                    </option>
	                </select> </td>
	                
	                <td class="right">{{greskaKategorija}}</td></tr>
	                
	                <tr><td class="left">Broj jezgara: </td> <td class="right" colspan="2"><input type="text" v-model="novaMasina.brojJezgara" disabled></td></tr>
	                <tr><td class="left">RAM: </td> <td class="right" colspan="2"><input type="text" v-model="novaMasina.RAM" disabled></td></tr>
	                <tr><td class="left">GPU jezgra: </td> <td class="right" colspan="2"><input type="text" v-model="novaMasina.GPUjezgra" disabled></td></tr>
	                
	                <tr><td colspan="3"><br><button v-on:click="dodaj()">DODAJ</button><br></td></tr>
	                <tr><td colspan="3"><br>{{greskaServer}}<br></td></tr>
	
	                <tr><td colspan="3"><br><router-link to="/masine">MASINE</router-link><br></td></tr>
	                
                </table>
        	</div>
        	
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
        
        axios.get("rest/diskovi/pregled")
        .then(response => {
            this.diskovi = response.data;
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