Vue.component("kategorije", {

    data: function(){
        return{
            kategorije: [], 
            selectedKategorija: {}, 
            selectedKategorijaId: '', 
            selected: false, 
            uloga: ''
        }
    }, 

    template: `
    
        <div>

            <div v-if="selected">

                Ime: <input type="text" v-model="selectedKategorija.ime"><br><br>
                Broj jezgara: <input type="text" v-model="selectedKategorija.brojJezgara"><br><br>
                RAM: <input type="text" v-model="selectedKategorija.RAM"><br><br>
                GPU jezgra: <input type="text" v-model="selectedKategorija.GPUjezgra"><br><br>

                <button v-on:click="izmeni()">Izmeni</button><br><br>
                <button v-on:click="obrisi()">Obrisi</button><br><br>

            </div>

            <div v-if="!selected">

                <h1>Registrovane kategorije</h1>
                <table border="1">
                    <tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th></tr>
                    <tr v-for="k in kategorije" v-on:click="selectKategorija(k)">
                        <td>{{k.ime}}</td>
                        <td>{{k.brojJezgara}}</td>
                        <td>{{k.RAM}}</td>
                        <td>{{k.GPUjezgra}}</td>
                    </tr> 

                </table><br><br>

                <button v-on:click="dodaj()">Dodaj</button><br><br>

            </div>

        </div>
    
    `, 

    mounted(){

        axios.get("rest/kategorije/pregled")
        .then(response => {
            this.kategorije = response.data;
        });


    }, 

    methods: {
        selectKategorija: function(kategorija){
            this.selectedKategorija = kategorija;
            this.selected = true;
            this.selectedKategorijaId = kategorija.ime;

        }, 

        izmeni: function(){
            axios.post("rest/kategorije/izmena", {"staroIme": this.selectedKategorijaId, "novaKategorija": this.selectedKategorija})
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();
                }
            })
        }, 

        obrisi: function(){
            axios.post("rest/kategorije/brisanje", this.selectedKategorija)
            .then(response => {
                if (response.data.result == "true"){
                    this.selected = false;
                    location.reload();
                }
            })
        }, 

        dodaj: function(){
            this.$router.push("dodajKategoriju");
        }

    }

})