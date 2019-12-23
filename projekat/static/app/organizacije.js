Vue.component("organizacije", {
    
    data: function(){
        return {
            organizacije: [], 
            selectedOrganizacija: {}, 
            selectedOrganizacijaId: '',
            selected: false, 
            greskaIme: '', 
            greskaServer: '', 
            greska: false
        }
    }, 

    template: `

        <div>

            <div v-if="selected">

                <h1>Izmena organizacije</h1>
                Ime: <input type="text" v-model="selectedOrganizacija.ime"> {{greskaIme}} <br><br>
                Opis: <br><textarea v-model="selectedOrganizacija.opis"></textarea><br><br>
                Korisnici: 
                <p v-if="selectedOrganizacija.korisnici.length==0">NEMA</p>
                <ol>
                    <li v-for="k in selectedOrganizacija.korisnici">{{k}}</li>
                </ol>
                Masine: 
                <p v-if="selectedOrganizacija.masine.length==0">NEMA</p>
                <ol>
                    <li v-for="d in selectedOrganizacija.masine">{{d}}</li>
                </ol>
                <button v-on:click="izmeni()">IZMENI</button><br><br>
                {{greskaServer}}

            </div>

            <div v-if="!selected">

                <h1>Registrovane organizacije</h1>
                <table border="1">
                <tr><th>Ime</th><th>Opis</th><th>Logo</th></tr>
                <tr v-for="o in organizacije" v-on:click="selectOrganizacija(o)">
                    <td>{{o.ime}}</td>
                    <td>{{o.opis}}</td>
                    <td>{{o.logo}}</td>
            	</tr>
                </table><br><br>
                <button v-on:click="dodaj()">DODAJ ORGANIZACIJU</button><br><br>
                <router-link to="/masine">MAIN PAGE</router-link><br><br>
                
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
        
    },

    methods: {

        selectOrganizacija: function(organizacija){
            this.selectedOrganizacija = organizacija;
            this.selectedOrganizacijaId = organizacija.ime;
            this.selected = true;
        }, 

        dodaj: function(){
            this.$router.push("dodajOrganizaciju");
        },

        izmeni: function(){

            if (this.selectedOrganizacija.opis == '') this.selectedOrganizacija.opis = null;
            if (this.selectedOrganizacija.logo == '') this.selectedOrganizacija.logo = null;

            this.greskaIme = '';
            this.greskaServer = '';
            this.greska = false;

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

        }

    }

});