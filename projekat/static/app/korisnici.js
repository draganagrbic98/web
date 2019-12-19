Vue.component("korisnici", {
	data: function(){
		return {
			korisnici: null
		}
	},
	
	template: `
	<div>
	<h1>Registrovani korisnici</h1>
	<table border="1">
	<tr><th>Email</th><th>Ime</th><th>Email</th><th>Organizacija</th></tr>
	<tr v-for="k in korisnici">
		<td>{{k.email}}</td>
		<td>{{k.ime}}</td>
		<td>{{k.prezime}}</td>
		<td>{{k.organizacija}}</td>
	</tr>
	</table>
	</div>
	`, 
	
	mounted(){
		axios.get('rest/korisnici/pregled')
		.then(response => this.korisnici = response.data)
	}
	
});