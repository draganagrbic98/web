Vue.component("dodajDisk", {

    data: function(){
        return{
            noviDisk:{
                "ime": '', 
                "tip": '',
                "kapacitet": 0, 
                "masina": null, 
                "organizacija": ''
            }, 
            greskaIme: '', 
            greskaTip: '', 
            greskaKapacitet: '', 
            greskaServer: '', 
            greska: false, 
            tipovi: [],
            masine: [], 
            organizacije: [], 
            organizacija: {}, 
            greskaOrganizacija: ''
        }
    }, 

    template:`

        <div class="dodavanje">

            <h1>Registracija novog diska</h1>
            
            <br>
            
            <div>
            
            	<table>

		            <tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="noviDisk.ime"></td> <td>{{greskaIme}}</td></tr>
		            
		            
		            <tr><td class="left">Organizacija: </td>
	                <td class="right"><input type="text" v-model="organizacija.ime" v-bind:hidden="organizacije.length>1" disabled>
	                <select v-model="noviDisk.organizacija" v-bind:hidden="organizacije.length<=1">
		                <option v-for="o in organizacije">
		                    {{o.ime}}
		                </option>
	                </select></td> 
	                
	                <td class="right">{{greskaOrganizacija}}</td></tr>
		            
		            <tr><td class="left">Tip: </td>
		            <td class="right"><select v-model="noviDisk.tip">
			            <option v-for="t in tipovi">
			                {{t}}
			            </option>
		            </select> </td> 
		            
		            {{greskaTip}}</td></tr>
		    		
		    		<tr><td class="left">Kapacitet: </td> <td class="right"><input type="text" v-model="noviDisk.kapacitet"> </td> <td>{{greskaKapacitet}}</td></tr>
		    		
		    		<tr><td class="left">Virtuelna Masina: </td>
		    		<td class="right" colspan="2"><select v-model="noviDisk.masina">
			            <option v-for="m in masine">
			                {{m.ime}}
			            </option>
		            </select>
		            </td></tr>
		            
		            <tr><td colspan="3"><br><button v-on:click="dodaj()">DODAJ</button><br></td></tr>
		            <tr><td colspan="3">{{greskaServer}}<br></td></tr>

		            <tr><td colspan="3"><router-link to="/diskovi">DISKOVI</router-link><br></td></tr>

    			</table>
    			
    		</div>

        </div>
    `, 

    mounted(){
    	
    	axios.get("rest/check/korisnik")
        .catch(error => {
            this.$router.push("masine");
        });

        axios.get("rest/diskovi/unos/pregled")
        .then(response => {
            this.tipovi = response.data;
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

        axios.get("rest/masine/pregled")
        .then(response => {
            this.masine = response.data;
        })
        .catch(error => {
            this.$router.push("masine");
        });

    }, 

    methods: {
    	
    	osvezi : function(){
    		
    		
    		this.greskaIme = '';
    		this.greskaTip = '';
    		this.greskaKapacitet = '';
    		this.greskaServer = '';
    		this.greska = false;
    		this.greskaOrganizacija = '';
    		
    	},

        dodaj: function(){
            

        	this.osvezi();
        	
            if (this.organizacije.length == 1) this.noviDisk.organizacija = this.organizacija.ime;


            if (this.noviDisk.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.noviDisk.tip == ''){
                this.greskaTip = "Tip ne sme biti prazan. ";
                this.greska = true;
            }
            if (isNaN(parseInt(this.noviDisk.kapacitet)) || parseInt(this.noviDisk.kapacitet) <= 0){
                this.greskaKapacitet = "Kapacitet mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (this.noviDisk.organizacija == ''){
                this.greskaOrganizacija = "Organizacija ne sme biti prazna. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/diskovi/dodavanje", this.noviDisk)
            .then(response => {
                this.$router.push("diskovi");
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        }

    }

});