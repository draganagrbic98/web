const kategorije = {template: '<kategorije></kategorije>'}
const organizacije = {template: '<organizacije></organizacije>'}
const korisnici = {template: '<korisnici></korisnici>'}
const masine = {template: '<masine></masine>'}
const diskovi = {template: '<diskovi></diskovi>'}
const login = {template: '<login></login>'}



const router = new VueRouter({
	mode: 'hash', 
	routes: [
		{path: '/kategorije', component: kategorije}, 
		{path: '/organizacije', component: organizacije}, 
		{path: '/korisnici', component: korisnici}, 
		{path: '/masine', component: masine}, 
		{path: '/diskovi', component: diskovi}, 
		{path: '/', component: login}

	]
});

var app = new Vue({
	router: router, 
	el: '#mainDiv', 
	data: {
		user: null
	},
	mounted() {
		this.$root.$on('login', u => {
			this.user = 'dadawd';
		});
	},
	
});



