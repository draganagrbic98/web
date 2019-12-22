Vue.component("kategorije", {
    data: function(){
        return {
            kategorije: [], 
            mama: ""
        }
    }, 

    template: `
        <div>
            <h1>Registrovane kategorije {{mama}}</h1>
            <table border="1">
            <tr><th>Ime</th><th>Broj jezgara</th><th>RM</th><th>GPU jezgra</th></tr>
            <tr v-for="k in kategorije">
                <td>{{k.ime}}</td>
                <td>{{k.brojJezgara}}</td>
                <td>{{k.RAM}}</td>
                <td>{{k.GPUjezgra}}</td>
            </tr>
            </table>
        </div>
    `, 

    mounted(){
        
        this.$root.$on("fromLogin", (m) => {
            this.mama = "MAMA MIA";
        });

    }

   
    

});