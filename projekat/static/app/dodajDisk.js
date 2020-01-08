Vue.component("dodajDisk", {

    data: function(){
        return{
            noviDisk:{
                "ime": '', 
                "tip": '',
                "kapacitet": 0, 
                "masina": null
            }, 
            greskaIme: '', 
            greskaTip: '', 
            greskaKapacitet: '', 
            greskaServer: '', 
            greska: false, 
            tipovi: [],
            masine: []
        }
    }, 

    template:`

        <div class="dodavanje">

            <h1>Registracija novog diska</h1>
            
            <br>
            
            <div>
            
            	<table>

		            <tr><td class="left">Ime: </td> <td class="right"><input type="text" v-model="noviDisk.ime"></td> <td>{{greskaIme}}</td></tr>
		            
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

        axios.get("rest/diskovi/unos/pregled")
        .then(response => {
            this.tipovi = response.data;
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

        dodaj: function(){
            
            this.greskaIme = '';
            this.greskaTip = '';
            this.greskaKapacitet = '';
            this.greskaServer = '';
            this.greska = false;

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
            if (this.greska) return;

            axios.post("rest/diskovi/dodavanje", this.noviDisk)
            .then(response => {
                this.$router.push("diskovi");
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            })

        }

    }

});