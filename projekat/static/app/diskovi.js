Vue.component("diskovi", {

    data: function(){
        return {
            diskovi: [], 
            selectedDisk: {}, 
            selectedDiskId: '',
            selected: false, 
            greskaIme: '', 
            greskaTip: '', 
            greskaKapacitet: '',
            greskaServer: '', 
            greska: false,
            uloga: '', 
            tipovi: []
        }
    }, 

    template: `

        <div>
        
            <div v-if="selected">

                Ime: <input type="text" v-model="selectedDisk.ime" v-bind:disabled="uloga=='KORISNIK'"> {{greskaIme}} <br><br>
                Tip: <select v-model="selectedDisk.tip" v-bind:disabled="uloga=='KORISNIK'"> 
	                <option v-for="t in tipovi">
	                    {{t}}
	                </option>
                </select> {{greskaTip}} <br><br>
                Kapacitet: <input type="text" v-model="selectedDisk.kapacitet" v-bind:disabled="uloga=='KORISNIK'"> {{greskaKapacitet}} <br><br>
                Virtuelna Masina: <input type="text" v-model="selectedDisk.masina" disabled><br><br>
                <div v-if="uloga!='KORISNIK'">
	                <button v-on:click="izmeni()">Izmeni</button><br><br>
	                <button v-on:click="obrisi()">Obrisi</button><br><br>
                </div>
                {{greskaServer}}
            </div>

            <div v-if="!selected">

                <h1>Registrovani diskovi</h1>
                <table border="1">
                <tr><th>Ime</th><th>Tip</th><th>Kapacitet</th><th>Virutelna Masina</th></tr>
                <tr v-for="d in diskovi" v-on:click="selectDisk(d)">
                    <td>{{d.ime}}</td>
                    <td>{{d.tip}}</td>
                    <td>{{d.kapacitet}}</td>
                    <td>{{d.masina}}</td>
                </tr>
                </table><br><br>
                <div v-if="uloga!='KORISNIK'">
                	<button v-on:click="dodaj()">Dodaj disk</button>
                </div>
            	<router-link to="/masine">MAIN PAGE</router-link>
            	
            </div>
        </div>
    `,

    mounted(){

        axios.get("rest/diskovi/pregled")
        .then(response => {
            this.diskovi = response.data
        })
        .catch(error => {
            this.$router.push("/");
        });
        
        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
        });

        axios.get("rest/diskovi/unos/tipovi")
        .then(response => {
            this.tipovi = response.data;
        });

    },

    methods: {

        selectDisk: function(disk){
            this.selectedDisk = disk;
            this.selectedDiskId = disk.ime;
            this.selected = true;
        }, 

        dodaj: function(){
            this.$router.push("dodajDisk");
        }, 

    },

    methods: {
        selectDisk: function(disk){
            this.selectedDisk = disk;
            this.selected = true;
            this.selectedDiskId = disk.ime;
        }, 

        dodaj: function(){
            this.$router.push("dodajDisk");
        },

        izmeni: function(){

            this.greskaIme = '';
            this.greskaTip = '';
            this.greskaKapacitet = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.selectedDisk.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.selectDisk.tip == ''){
                this.greskaTip = "Tip ne sme biti prazan. ";
                this.greska = true;
            }
            if (this.selectedDisk.kapacitet === '' || isNaN(this.selectedDisk.kapacitet) || parseInt(this.selectedDisk.greskaKapacitet) <= 0){
                this.greskaKapacitet = "Kapacitet mora biti pozitivan ceo broj. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/diskovi/izmena", {"staroIme": this.selectedDiskId, "noviDisk": this.selectedDisk})
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        },

        obrisi: function(){

            this.selectedDisk.ime = this.selectedDiskId;
            axios.post("rest/diskovi/brisanje", this.selectedDisk)
            .then(response => {
                this.selected = false;
                location.reload();
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            });

        }
    }, 
});