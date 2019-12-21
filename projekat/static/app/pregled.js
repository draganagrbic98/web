Vue.component("pregled", {
	data: function(){
		return {
			diskovi: [],
			uloga: ''
		}
	},
	
	template: `
	<div>
	<h1>Dobrodosli! {{uloga}}</h1>
	<table border="1">
	<tr><th>Ime</th><th>Kapacitet</th><th>Masina</th></tr>
	<tr v-for="d in diskovi">
		<td>{{d.ime}}</td>
		<td>{{d.kapacitet}}</td>
		<td>{{d.masina}}</td>
	</tr>
	</table>
	</div>
	`, 
	
	mounted(){
		axios.get('rest/diskovi/pregled')
		.then(response => this.diskovi = response.data);
		
		this.$root.$on('pregled', u => {
			this.uloga = 'ifojawof';
		});
	}
	
});