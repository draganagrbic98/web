Vue.component("dodajOrganizaciju", {

    data: function(){
        return{
            novaOrganizacija: {
                "ime": '', 
                "opis": null, 
                "logo": null,
                "korisnici": [], 
                "masine": []
            }, 
            greskaIme: '', 
            greskaServer: '', 
            greska: false
        }
    }, 

    template: `

        <div>

            <h1>Registracija nove organizacije</h1>
            Ime: <input type="text" v-model="novaOrganizacija.ime"> {{greskaIme}} <br><br>
            Opis: <input type="text" v-model="novaOrganizacija.opis"><br><br>
            <button v-on:click="dodaj()">Dodaj organizaciju</button><br><br>
            <router-link to="/organizacije">Organizacije</router-link><br><br>
            {{greskaServer}}

        </div>

    `, 

    methods: {

        dodaj: function(){

            this.greskaIme = '';
            this.greskaServer = '';
            this.greska = false;

            if (this.novaOrganizacija.ime == ''){
                this.greskaIme = "Ime ne sme biti prazno. ";
                this.greska = true;
            }
            if (this.greska == true) return;

            axios.post("rest/organizacije/dodavanje", this.novaOrganizacija)
            .then(response => {
                this.$router.push("organizacije")
            })
            .catch(error => {
                this.greskaServer = error.response.data.result;
            })
           
        }
    }, 

});