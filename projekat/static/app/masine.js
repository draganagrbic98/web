Vue.component("masine", {
	data: function(){
		return {
			masine: null
		}
	},
	
	template: `
	<div>
	<h1>Registrovane masine</h1>
	<table border="1">
	<tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th></tr>
	<tr v-for="m in masine">
		<td>{{m.ime}}</td>
		<td>{{m.brojJezgara}}</td>
		<td>{{m.RAM}}</td>
		<td>{{m.GPUjezgra}}</td>
	</tr>
	</table>
	</div>
	`, 
	
	mounted(){
		axios.get('rest/masine/pregled')
		.then(response => this.masine = response.data)
	}
	
});