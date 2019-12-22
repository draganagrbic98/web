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

        <div>

            <h1>Registracija novog diska</h1>
            Ime: <input type="text" v-model="noviDisk.ime"> {{greskaIme}} <br><br>
            Tip: <select v-model="noviDisk.tip">
	            <option v-for="t in tipovi">
	                {{t}}
	            </option>
            </select> {{greskaTip}} <br><br>
    		Kapacitet: <input type="text" v-model="noviDisk.kapacitet"> {{greskaKapacitet}} <br><br>
    		Virtuelna Masina: <select v-model="noviDisk.masina">
	            <option v-for="m in masine">
	                {{m.ime}}
	            </option>
            </select><br><br>
            <button v-on:click="dodaj()">Dodaj masinu</button><br><br>
            <router-link to="/diskovi">Diskovi</router-link><br><br>
            {{greskaServer}}

        </div>
    `, 

    mounted(){

        axios.get("rest/diskovi/unos/tipovi")
        .then(response => {
            this.tipovi = response.data;
        });

        axios.get("rest/masine/pregled")
        .then(response => {
            this.masine = response.data;
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
            if (this.noviDisk.kapacitet === '' || isNaN(this.noviDisk.kapacitet) || parseInt(this.noviDisk.kapacitet) <= 0){
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