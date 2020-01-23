Vue.component("kategorije", {

    data: function(){
        return{
            kategorije: [], 
            selectedKategorija: {}, 
            selectedKategorijaId: '', 
            selected: false, 
            uloga: '',
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
                
    			<br>
    			
    			<div class="izmena">
    				
    				<table>		
		                <tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="selectedKategorija.ime"></td> <td>{{greskaIme}}</td></tr>
		                <tr><td class="left">Broj jezgara: </td> <td class="right"><input type="text" v-model="selectedKategorija.brojJezgara"></td> <td>{{greskaBrojJezgara}}</td></tr>
		                <tr><td class="left">RAM: </td> <td class="right"><input type="text" v-model="selectedKategorija.RAM"></td> <td>{{greskaRAM}}</td></tr>
		                <tr><td class="left">GPU jezgra: </td> <td class="right"><input type="text" v-model="selectedKategorija.GPUjezgra"></td> <td>{{greskaGPUjezgra}}</td></tr>
		                
		                <tr><td colspan="3"><br><button v-on:click="izmeni()">IZMENI</button><br></td></tr>
		                <tr><td colspan="3"><br><button v-on:click="obrisi()">OBRISI</button><br></td></tr>
		                <tr><td colspan="3">{{greskaServer}}<br></td></tr>
    				</table>
    				
    				<button v-on:click="vratiNaKategorije()">POVRATAK</button>

    			</div>
    			
    		</div>

            <div v-if="!selected">

                <h1>Registrovane kategorije</h1>
                
                <br>
                
	            <div class="main">
		                
	    			<div class="left">
	    			
		    			<table class="data" border="1">
			                <tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th></tr>
			                <tr v-for="k in kategorije" v-on:click="selectKategorija(k)">
			                    <td>{{k.ime}}</td>
			                    <td>{{k.brojJezgara}}</td>
			                    <td>{{k.RAM}}</td>
			                    <td>{{k.GPUjezgra}}</td>
			                </tr> 
	                	</table><br><br>
	                	
	                </div>
                
	    			<div class="right">
		    			
		    			<table class="right_menu">
		    			
			    			<tr><td>
			    			
			    				<table>
			    					<tr v-if="uloga!='KORISNIK'"><td><router-link to="/korisnici">KORISNICI</router-link></td></tr>
			    					
			    					<tr v-if="uloga=='SUPER_ADMIN'"><td><router-link to="/organizacije">ORGANIZACIJE</router-link></td></tr>
		
		    						<tr><td><router-link to="/masine">MASINE</router-link></td></tr>
		    						<tr><td><router-link to="/diskovi">DISKOVI</router-link></td></tr>
		    						
		    						<tr><td><router-link to="/profil">PROFIL</router-link></td></tr>
		    						
		    						<tr><td><br><button v-on:click="logout()">ODJAVA</button><br><br></td></tr>
			    				</table>
			    		
			   				</td></tr>
    					
					        <tr v-if="uloga!='KORISNIK'"><td>
					        	<br>
				                <button v-on:click="dodaj()">DODAJ KATEGORIJU</button><br><br>
    						</td></tr>
    						                    	
                    	</table>
    					
			   		</div>
			   		
	            </div>
                
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

        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
        })
        .catch(error => {
            this.$router.push("/");
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
        	let temp = confirm("Da li ste sigurni?");
        	if (!temp) return;

            this.selectedKategorija.ime = this.selectedKategorijaId;
            axios.post("rest/kategorije/brisanje", this.selectedKategorija)
            .then(response => {
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
            if (isNaN(parseInt(this.selectedKategorija.brojJezgara)) || parseInt(this.selectedKategorija.brojJezgara) <= 0){
                this.greskaBrojJezgara = "Broj jezgara mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (isNaN(parseInt(this.selectedKategorija.RAM)) || parseInt(this.selectedKategorija.RAM) <= 0){
                this.greskaRAM = "RAM mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (isNaN(parseInt(this.selectedKategorija.GPUjezgra)) || parseInt(this.selectedKategorija.GPUjezgra) < 0){
                this.greskaGPUjezgra = "GPU jezgra moraju biti nenegativan ceo broj. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/kategorije/izmena", {"staroIme": this.selectedKategorijaId, "novaKategorija": this.selectedKategorija})
            .then(response => {
            	location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        },

        vratiNaKategorije: function() {
            location.reload();
        },
        
        logout: function(){
            axios.get("rest/user/logout")
            .then(response => {
                this.$router.push("/");
            })
            .catch(error => {
                this.$router.push("/");
            });
        }
    }

});