Vue.component("login", {
	
	data: function(){
		return{
			username: null, 
			password: null
		}
	},
	
	template: `
	
		<div>
		<h1>Prijava korisnika</h1>
		Korisnicko ime: <input type="text" v-model="username"><br><br>
		Lozinka: <input type="password" v-model="password"><br><br>
		<button value="Prijavi se" v-on:click="login()"><br><br>
		</div>
	
	`,
	
	methods: {
		login(){

			axios.post('rest/user/login', {"korisnockoIme": this.username, "lozinka": this.password})
			.then(response => {
	    		this.username = 'mama';

			});
			//ocu da sacuvam response 
			this.$router.$emit(resposse);
			this.$router.push('kategorije');
		}
	}
		
});
