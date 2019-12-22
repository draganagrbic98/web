Vue.component("dodajKategoriju", {

    data: function(){
        return{
            uloga: '', 
            novaKategorija: {
                "ime": '', 
                "brojJezgara": 0, 
                "RAM": 0, 
                "GPUjezgra": 0
            }, 
            greska: ''
        }
    }, 

    template: `

        <div>

            <h1>Registracija nove kategorije</h1>
            Ime: <input type="text" v-model="novaKategorija.ime"><br><br>
            Broj jezgara: <input type="text" v-model="novaKategorija.brojJezgara"><br><br>
            RAM: <input type="text" v-model="novaKategorija.RAM"><br><br>
            GPU jezgra: <input type="text" v-model="novaKategorija.GPUjezgra"><br><br>
            <button v-on:click="dodaj()">Dodaj</button><br><br>
            {{greska}}

        </div>
    
    `, 

    methods: {
        dodaj: function(){
            axios.post("rest/kategorije/dodavanje", this.novaKategorija)
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("kategorije");
                }
                else{
                    this.greska = "Uneta kategorija vec postoji. Ponovo. ";
                }
            })
        }
    }, 

    mounted(){
        axios.get("rest/user/uloga")
        .then(response => {
            this.uloga = response.data.result;
        });
    }

})