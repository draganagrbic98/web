Vue.component("mojaOrganizacija", {
    
    data: function(){
        return {
            organizacija: {
            	"ime": '',
            	"opis": '',
            	"logo": '',
            	"korisnici": [],
            	"masine": []
            	
            },
            organizacijaID: '',
            uloga: '',
            greskaIme: '', 
            greskaServer: '', 
            greska: false
        }
    }, 

    template: `

        <div>

            <h1>Moja organizacije</h1>
                
    		<br>
    			
   			<div class="izmena_organizacije">
    				
	            <div class="izmena_ui">

	    			<table>
	    				<tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="organizacija.ime"> <td> </td>{{greskaIme}} </td></tr>
	                	<tr><td class="left">Opis: </td> <td class="right"><textarea v-model="organizacija.opis"></textarea></td></tr>
			            
			            <tr><td class="left">Logo: </td> <td class="right" colspan="2"><br><img v-bind:src="organizacija.logo" text="Logo"></img><br><br></td></tr>
			            
		            	<tr><td class="left">Novi Logo: </td> <td class="right"><input type="file" accept="image/*" v-on:change="updateLogo($event)"></td></tr>
			            
			            <tr v-if="uloga!='KORISNIK'"><td colspan="3"><br><br><button v-on:click="izmeni()">IZMENI</button></td></tr>
			                
			            <tr><td colspan="3">{{greskaServer}}<br><br></td></tr>
			            
			            <tr><td colspan="3"><router-link to="/masine">MASINE</router-link></td></tr>
	    			</table> <br><br>
	    				
    			</div>
    			
    			<div class="tabele">
    				
	    			<div class="org_masine">
	    				
		    			<h1> Virtuelne Masine </h1>
		    				
		    			<br>
		    				
			            <p v-if="organizacija.masine.length==0">NEMA</p>
			                
			            <div>
				            <table v-if="organizacija.masine.length!=0">
				                <tr><th>Ime</th></tr>
				                	
				                <tr v-for="m in organizacija.masine">
				                	<td>{{m}}</td>
				                </tr>
				            </table>
			                </div>
			                
			        </div>
			            
			        <div class="org_korisnici">
	    			
		    			<h1> Korisnici </h1>
		    				
		    			<br>
		    				
			            <p v-if="organizacija.korisnici.length==0">NEMA</p>
			                
			            <div>
				            <table v-if="organizacija.korisnici.length!=0">
				                <tr><th>Ime</th></tr>
				                	
				                <tr v-for="k in organizacija.korisnici">
				                	<td>{{k}}</td>
				                </tr>
				            </table>
			            </div>
			                
			        </div>
    				
    			</div>
    				
    		</div>
    			
        </div>
    `,
    
    mounted(){

        axios.get("rest/organizacije/pregled")
        .then(response => {
            this.organizacija = response.data;
            this.organizacijaID = this.organizacija.ime;
        }).catch(error => {
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

        updateLogo: function(event) {
	  		var reader = new FileReader();
	  		var instance = this;
	  		
	  		reader.onloadend = function() {
				instance.organizacija.logo = reader.result;
			}
			 
			reader.readAsDataURL(event.target.files[0]);
        },
        
        izmeni: function(){

            if (this.organizacija.opis == '') this.organizacija.opis = null;
            if (this.organizacija.logo == '') this.organizacija.logo = null;

            this.greskaIme = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.organizacija.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.greska) return;
            
            axios.post("rest/organizacije/izmena", {"staroIme": this.organizacijaID, "novaOrganizacija": this.organizacija})
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        },

    }

});