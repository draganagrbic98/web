Vue.component("dodajOrganizaciju", {

    data: function(){
        return{
            uloga: '', 
            novaOrganizacija: {
                "ime": '', 
                "opis": '', 
                "logo": '', 
                "korisnici": [], 
                "masine": []
            }, 
            greska: ''
        }
    }, 

    template: `

        <div>

            <h1>Registracija nove organizacije</h1>
            Ime: <input type="text" v-model="novaOrganizacija.ime"><br><br>
            Opis: <input type="text" v-model="novaOrganizacija.opis"><br><br>
            <button v-on:click="dodaj()">Dodaj</button><br><br>
            {{greska}}

        </div>

    `, 

    methods: {
        dodaj: function(){
            axios.post("rest/organizacije/dodavanje", this.novaOrganizacija)
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("organizacije");
                }
                else{
                    this.greska = "Uneta organizacija vec postoij. Ponovo. ";
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