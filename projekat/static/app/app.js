const kategorije = {template: '<kategorije></kategorije>'}
const organizacije = {template: '<organizacije></organizacije>'}
const korisnici = {template: '<korisnici></korisnici>'}
const masine = {template: '<masine></masine>'}
const diskovi = {template: '<diskovi></diskovi>'}
const login = {template: '<login></login>'}
const pregled = {template: '<pregled></pregled>'}



const router = new VueRouter({
	mode: 'hash', 
	routes: [
		{path: '/kategorije', component: kategorije}, 
		{path: '/organizacije', component: organizacije}, 
		{path: '/korisnici', component: korisnici}, 
		{path: '/masine', component: masine}, 
		{path: '/diskovi', component: diskovi}, 
		{path: '/', component: login},
		{path: '/pregled', component: pregled}

	]
});

var app = new Vue({
	router: router, 
	el: '#mainDiv', 
	data: {
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
		}
	},
	mounted() {
		this.$root.$on('login', k => {
			this.korisnik = k;
		});
	},
	
});



