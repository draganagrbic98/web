Vue.component("login", {

    data: function(){
        return {
            korisnickoIme: '', 
            lozinka: '', 
            greskaKorisnickoIme: '',  
            greskaLozinka: '', 
            greskaLogin: '', 
            greska: false
        }
    }, 

    template: `
        <div>
            <h1>Prijava</h1>
            Korisnicko ime: <input type="text" v-model="korisnickoIme"> {{greskaKorisnickoIme}} <br><br>
            Lozinka: <input type="password" v-model="lozinka"> {{greskaLozinka}} <br><br>
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

            if (this.korisnickoIme == ''){
                this.greskaKorisnickoIme = "Niste uneli korisnicko ime.";
                this.greska = true;
            }
            
            if (this.lozinka == null){
                this.lozinka = "Niste uneli lozinku. ";
                this.greska = true;
            }

            if (this.greska == false){
                axios.post("rest/user/login", {"korisnickoIme": this.korisnickoIme, "lozinka": this.lozinka})
                .then(response1 => {
	                if (response1.data == null){
	                    this.greskaLogin = "Unet korisnik ne postoji. ";
	                    this.greska = true;
	                }
                
	                if (this.greska == false){
	                    this.$router.push("masine");
	                }
                });
            }
        }
    }
});