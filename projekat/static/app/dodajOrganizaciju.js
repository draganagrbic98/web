Vue.component("dodajOrganizaciju", {

    data: function(){
        return{
            novaOrganizacija: {
                "ime": '', 
                "opis": '', 
                "logo": '', 
                "korisnici": [], 
                "masine": []
            }, 
            greskaIme: '', 
            greskaUnos: '', 
            greska: false
        }
    }, 

    template: `

        <div>

            <h1>Registracija nove organizacije</h1>
            Ime: <input type="text" v-model="novaOrganizacija.ime"> {{greskaIme}} <br><br>
            Opis: <input type="text" v-model="novaOrganizacija.opis"><br><br>
            <button v-on:click="dodaj()">Dodaj</button><br><br>
            {{greskaUnos}}

        </div>

    `, 

    methods: {
        dodaj: function(){

            this.greskaIme = '';
            this.greskaOpis = '';
            this.greskaUnos = '';
            this.greska = false;

            if (this.novaOrganizacija.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno";
                this.greska = true;
            }

            if (this.greska == true) return;

            axios.post("rest/organizacije/dodavanje", this.novaOrganizacija)
            .then(response => {
                if (response.data.result == "true"){
                    this.$router.push("organizacije");
                }
                else{
                    this.greskaUnos = "Uneta organizacija vec postoji. ";
                    this.greska = true;
                    return;
                }
            })
        }
    }, 

    

})