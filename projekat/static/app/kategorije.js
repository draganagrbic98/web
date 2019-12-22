Vue.component("kategorije", {
	data: function(){
		return {
			mama: '',
		}
	},
	
	template: `
	<div>
	<h1>Registrovane kategorije {{mama}} </h1>


	</div>
	`,
	
	mounted(){
		this.$root.$on('kategorijee', u => {
			this.mama = 'JEBOTE';
		});


		
	}
	
});
