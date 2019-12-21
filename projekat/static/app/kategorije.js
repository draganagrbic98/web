Vue.component("kategorije", {
	data: function(){
		return {
			kategorije: null,
		}
	},
	
	template: `
	<div>
	<h1>Registrovane kategorije</h1>
	<table border="1">
	<tr><th>Ime</th><th>Broj jezgara</th><th>RAM</th><th>GPU jezgra</th></tr>
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
		axios.get('rest/kategorije/pregled')
		.then(response => this.kategorije = response.data);
	}
	
});
