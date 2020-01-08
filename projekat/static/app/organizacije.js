Vue.component("organizacije", {
    
    data: function(){
        return {
            organizacije: [], 
            selectedOrganizacija: {}, 
            selectedOrganizacijaId: '',
            selected: false,
            uloga: '',
            greskaIme: '', 
            greskaServer: '', 
            greska: false
        }
    }, 

    template: `

        <div>

            <div v-if="selected">

                <h1>Izmena organizacije</h1>
                
    			<br>
    			
    			<div class="izmena_organizacije">
    				
	                <div class="izmena_ui">

	    				<table>
	    					<tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="selectedOrganizacija.ime"> <td> </td>{{greskaIme}} </td></tr>
	                		<tr><td class="left">Opis: </td> <td class="right"><textarea v-model="selectedOrganizacija.opis"></textarea></td></tr>
			                
			                <tr v-if="uloga!='KORISNIK'"><td colspan="3"><br><button v-on:click="izmeni()">IZMENI</button></td></tr>
			                
			                <tr><td colspan="3">{{greskaServer}}<br><br></td></tr>
			                
	    					<tr><td colspan="3"><button v-on:click="vratiNaOrganizacije()">POVRATAK</button></td></tr>

	    				</table>
	    				

    				</div>
    				
    				<div class="tabele">
    				
	    				<div class="org_masine">
	    				
		    				<h1> Virtuelne Masine </h1>
		    				
		    				<br>
		    				
			                <p v-if="selectedOrganizacija.masine.length==0">NEMA</p>
			                
			                <div>
				                <table v-if="selectedOrganizacija.masine.length!=0">
				                	<tr><th>Ime</th></tr>
				                	
				                	<tr v-for="m in selectedOrganizacija.masine">
				                		<td>{{m}}</td>
				                	</tr>
				                </table>
			                </div>
			                
			            </div>
			            
			            <div class="org_korisnici">
	    			
		    				<h1> Korisnici </h1>
		    				
		    				<br>
		    				
			                <p v-if="selectedOrganizacija.korisnici.length==0">NEMA</p>
			                
			                <div>
				                <table v-if="selectedOrganizacija.korisnici.length!=0">
				                	<tr><th>Ime</th></tr>
				                	
				                	<tr v-for="k in selectedOrganizacija.korisnici">
				                		<td>{{k}}</td>
				                	</tr>
				                </table>
			                </div>
			                
			            </div>
    				
    				</div>
    				
    			</div>
    			
            </div>

            <div v-if="!selected">

                <h1>Registrovane organizacije</h1>
                
                <br>
                
	            <div class="main">
		                
	    			<div class="left">
	    			
		                <table class="data" border="1">
			                <tr><th>Ime</th><th>Opis</th><th>Logo</th></tr>
			                <tr v-for="o in organizacije" v-on:click="selectOrganizacija(o)">
			                    <td>{{o.ime}}</td>
			                    <td>{{o.opis}}</td>
			                    <td>{{o.logo}}</td>
			            	</tr>
		                </table>
		            
		            </div>
		            
	    			<div class="right">
		    			
		    			<table class="right_menu">
		    			
			    			<tr><td>
			    			
			    				<table>
			    					<tr v-if="uloga!='KORISNIK'"><td><router-link to="/korisnici">KORISNICI</router-link></td></tr>
			    					
			    					<tr v-if="uloga=='SUPER_ADMIN'"><td><router-link to="/kategorije">KATEGORIJE</router-link></td></tr>
		
		    						<tr><td><router-link to="/masine">MASINE</router-link></td></tr>
		    						<tr><td><router-link to="/diskovi">DISKOVI</router-link></td></tr>
		    						
		    						<tr><td><router-link to="/profil">PROFIL</router-link></td></tr>
		    						
		    						<tr><td><br><button v-on:click="logout()">ODJAVA</button><br><br></td></tr>
			    				</table>
			    		
			   				</td></tr>
			   						
					        <tr><td>
					        	<br>
				                <button v-on:click="dodaj()">DODAJ ORGANIZACIJU</button><br><br>
    						</td></tr>
                	    </table>
                	</div>
                </div>
            </div>
        </div>
    `,
    
    mounted(){

        axios.get("rest/organizacije/pregled")
        .then(response => {
            this.organizacije = response.data;
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

        selectOrganizacija: function(organizacija){
            this.selectedOrganizacija = organizacija;
            this.selectedOrganizacijaId = organizacija.ime;
            this.selected = true;
            this.greskaIme = '';
            this.greskaServer = '';
            this.greska = false;

        }, 

        dodaj: function(){
            this.$router.push("dodajOrganizaciju");
        },

        izmeni: function(){

            if (this.selectedOrganizacija.opis == '') this.selectedOrganizacija.opis = null;
            if (this.selectedOrganizacija.logo == '') this.selectedOrganizacija.logo = null;

            if (this.selectedOrganizacija.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.greska) return;
            
            axios.post("rest/organizacije/izmena", {"staroIme": this.selectedOrganizacijaId, "novaOrganizacija": this.selectedOrganizacija})
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        },

        vratiNaOrganizacije: function() {
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