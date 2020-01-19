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

        <div class="dodavanje">
        
		    <h1>Registracija nove kategorije</h1>
		    
		    <br>
		    
            <div>
            
	    		<table>
	
			        <tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="novaKategorija.ime"></td> <td>{{greskaIme}}</td></tr>
			        <tr><td class="left">Broj jezgara: </td> <td class="right"><input type="text" v-model="novaKategorija.brojJezgara"></td> <td>{{greskaBrojJezgara}}</td></tr>
			        <tr><td class="left">RAM: </td> <td class="right"><input type="text" v-model="novaKategorija.RAM"></td> <td>{{greskaRAM}}</td></tr>
			        <tr><td class="left">GPU jezgra: </td> <td class="right"><input type="text" v-model="novaKategorija.GPUjezgra"></td> <td>{{greskaGPUjezgra}}</td></tr>
			        
			        <tr><td colspan="3"><button v-on:click="dodaj()">DODAJ</button><br></td></tr>
			        <tr><td colspan="3">{{greskaServer}}<br></td></tr>
			        
			        <tr><td colspan="3"><router-link to="/kategorije">KATEGORIJE</router-link><br></td></tr>
	
	    		</table>

    		</div>

        </div>
    
    `, 
    
    mounted(){

        axios.get("rest/masine/pregled")
        .catch(error => {
            this.$router.push("masine");
        });

    }, 

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
            if (isNaN(parseInt(this.novaKategorija.brojJezgara)) || parseInt(this.novaKategorija.brojJezgara) <= 0){
                this.greskaBrojJezgara = "Broj jezgara mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (isNaN(parseInt(this.novaKategorija.RAM)) || parseInt(this.novaKategorija.RAM) <= 0){
                this.greskaRAM = "RAM mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (isNaN(parseInt(this.novaKategorija.GPUjezgra)) || parseInt(this.novaKategorija.GPUjezgra) < 0){
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