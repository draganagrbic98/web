Vue.component("login", {
	
	data: function(){
		return{
			username: null, 
			password: null,
			korisnik: {
				'user' : {
					'korisnickoIme' : '',
					'lozinka' : ''
				},
				'email' : '',
				'ime' : '',
				'prezime' : '',
				'uloga' : '',
				'organizacija' : ''
			},
			greska: ''
		}
	},
	
	template: `
	
		<div>
		<h1>Prijava korisnika</h1>
		Korisnicko ime: <input type="text" v-model="username"><br><br>
		Lozinka: <input type="password" v-model="password"><br><br>
		<button v-on:click="login()">Prijava</button><br><br>
		{{greska}}
		</div>
	
	`,
	
	methods: {
		login() {

			axios.post('rest/user/login', {"korisnickoIme": this.username, "lozinka": this.password})
			.then(response => {
				if (response.data != null) {
					this.$root.$emit('login', response.data);
					this.$root.$emit('pregled', 'blabla');
					this.$router.push('pregled');
				} else {
					this.greska = 'Pogresni kredencijali.';
				}
			});
			// ocu da sacuvam response

		}
	}
		
});
