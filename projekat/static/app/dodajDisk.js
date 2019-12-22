Vue.component("dodajDisk", {
    data: function(){
        return{
            tipovi: [],
            masine: [],
            noviDisk:{
                "ime": '', 
                "tip": '',
                "kapacitet": '', 
                "masina": ''
            }, 
            greska: ''
        }
    }, 

    template:`
        <div>
            <h1>Registracija novog diska</h1>
                Ime: <input type="text" v-model="noviDisk.ime"><br><br>
                
                Tip: <select v-model="noviDisk.tip">
	                <option v-for="t in tipovi">
	                    {{t}}
	                </option>
                </select><br><br>
                
    			Kapacitet: <input type="number" v-model="noviDisk.kapacitet" min="0"><br><br>

    			Virtuelna Masina: <select v-model="noviDisk.masina">
	                <option v-for="vm in masine">
	                    {{vm.ime}}
	                </option>
                </select><br><br>

                <button v-on:click="dodaj()">Dodaj</button><br><br>
                
                <router-link to="/diskovi">Diskovi</router-link> <br><br>
                
                {{greska}}
        </div>
    `, 

    methods: {
        dodaj: function(){
            axios.post("rest/diskovi/dodavanje", this.noviDisk)
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("diskovi");
                }
                else{
                    this.greska = "Uneti disk vec postoji. Ponovo. ";
                }
            });
        },
    },

    mounted(){
        axios.get("rest/masine/pregled")
        .then(response => {
            this.masine = response.data;
        });

        axios.get("rest/diskovi/unos/tipovi")
        .then(response => {
            this.tipovi= response.data;
        });
    }

})