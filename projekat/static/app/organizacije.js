Vue.component("organizacije", {
	data: function(){
		return {
			organizacije: null
		}
	},
	
	template: `
	<div>
	<h1>Registrovane organizacije</h1>
	<table border="1">
	<tr><th>Ime</th><th>Opis</th><th>Logo</th></tr>
	<tr v-for="o in organizacije">
		<td>{{o.ime}}</td>
		<td>{{o.opis}}</td>
		<td>{{o.logo}}</td>
	</tr>
	</table>
	</div>
	`, 
	
	mounted(){
		axios.get('rest/organizacije/pregled')
		.then(response => this.organizacije = response.data)
	}
	
});