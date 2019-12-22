Vue.component("diskovi", {
    data: function(){
        return {
            diskovi: [], 
            tipovi: [],
            selectedDiskId: '',
            selectedDisk: {}, 
            selected: false, 
            uloga: ''
        }
    }, 

    template: `
        <div>
        
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
                	<button v-on:click="dodaj()">Dodaj</button>
                </div>
                
            	<router-link to="/masine">Masine</router-link>
            	
            </div>

            <div v-if="selected">
                Ime: <input type="text" v-model="selectedDisk.ime" v-bind:disabled="uloga=='KORISNIK'"><br><br>

                Tip: <select v-model="selectedDisk.tip">
	                <option v-for="t in tipovi">
	                    {{t}}
	                </option>
                </select><br><br>
                                
                Kapacitet: <input type="text" v-model="selectedDisk.kapacitet"><br><br>
                
                Virtuelna Masina: <input type="text" v-model="selectedDisk.masina" disabled><br><br>
        
                <div v-if="uloga!='KORISNIK'">
	                <button v-on:click="izmeni()">Izmeni</button><br><br>
	                <button v-on:click="obrisi()">Obrisi</button>
                </div>

            </div>
            
        </div>
    `,

    methods: {
        selectDisk: function(disk){
            this.selectedDisk = disk;
            this.selected = true;
            this.selectedDiskId = disk.ime;
        }, 

        izmeni: function(){
            axios.post("rest/diskovi/izmena", {"staroIme": this.selectedDiskId, "noviDisk": this.selectedDisk})
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();

                }
            });
        },

        dodaj: function(){
            this.$router.push("dodajDisk");
        },

        obrisi: function(){
            axios.post("rest/diskovi/brisanje", this.selectedDisk)
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();
                }
            });
        }
    }, 

    mounted(){

        axios.get("rest/diskovi/pregled")
            .then(response => {
                this.diskovi = response.data;
        });

        axios.get("rest/diskovi/unos/tipovi")
        .then(response => {
            this.tipovi= response.data;
        });
        
        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
            if (this.uloga == null){
                this.$router.push("/");
            }
        });
    }

})