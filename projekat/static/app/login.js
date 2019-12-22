Vue.component("login", {
	
	data: function(){
		return{
			username: '', 
			password: ''
		}
	},
	
	template: `
	
		<div>
		<button v-on:click="login()"><br><br>
		</div>
	
	`,
	
	methods: {
		login() {

			this.$root.$emit('kategorijee', 'hura');
			



		}
	}
		
});
