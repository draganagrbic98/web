Vue.component("login", {

    data: function(){
        return {
            user: {
                "korisnickoIme": '', 
                "lozinka": ''
            },
            greskaKorisnickoIme: '',  
            greskaLozinka: '', 
            greskaLogin: '', 
            greska: false
        }
    }, 

    template: `
        <div>
            <h1>Prijava</h1>
            Korisnicko ime: <input type="text" v-model="user.korisnickoIme"> {{greskaKorisnickoIme}} <br><br>
            Lozinka: <input type="password" v-model="user.lozinka"> {{greskaLozinka}} <br><br>
            <button v-on:click="login()">Prijava</button><br><br>
            {{greskaLogin}}
        </div>
    `, 

    methods: {

        login(){
            
            this.greskaKorisnickoIme = '';
            this.greskaLozinka = '';
            this.greskaLogin = '';
            this.greska = false;

            if (this.user.korisnickoIme == ''){
                this.greskaKorisnickoIme = "Niste uneli korisnicko ime. ";
                this.greska = true;
            }
            if (this.user.lozinka == ''){
                this.greskaLozinka = "Niste uneli lozinku. ";
                this.greska = true;
            }
            if (this.greska) return;

            axios.post("rest/user/login", this.user)
            .then(response => {
                this.$router.push("masine");
            })
            .catch(error => {
                this.greskaLogin = error.response.data.result;
            });

        }
    }
});