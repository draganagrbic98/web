Vue.component("organizacije", {
    data: function(){
        return {
            organizacije: [], 
            selectedOrganizacijaId: '',
            selectedOrganizacija: {}, 
            selected: false, 
            uloga: ''
        }
    }, 

    template: `
        <div>
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
                <button v-on:click="dodaj()">Dodaj</button><br><br>

            </div>

            <div v-if="selected">

                Ime: <input type="text" v-model="selectedOrganizacija.ime"><br><br>
                Opis: <input type="text" v-model="selectedOrganizacija.opis"><br><br>
                Logo: <input type="text" v-model="selectedOrganizacija.logo"><br><br>
                <button v-on:click="izmeni()">Izmeni</button><br><br>

            </div>


        </div>
    
    `, 

    methods: {
        selectOrganizacija: function(organizacija){
            this.selectedOrganizacija = organizacija;
            this.selected = true;
            this.selectedOrganizacijaId = organizacija.ime;
        }, 

        izmeni: function(){
            axios.post("rest/organizacije/izmena", {"staroIme": this.selectedOrganizacijaId, "novaOrganizacija": this.selectedOrganizacija})
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();

                }
            });
        }, 

        dodaj: function(){
            this.$router.push("dodajOrganizaciju");
        }
    },

    mounted(){
        axios.get("rest/organizacije/pregled")
        .then(response => {
            this.organizacije = response.data;
        }).catch(error => {
            this.$router.push("masine");
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