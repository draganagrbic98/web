const login = {template: '<login></login>'}
const kategorije = {template: '<kategorije></kategorije>'}
const masine = {template: '<masine></masine>'}
const organizacije = {template: '<organizacije></organizacije>'}
const dodajMasinu = {template: '<dodajMasinu></dodajMasinu>'}
const dodajKategoriju = {template: '<dodajKategoriju></dodajKategoriju>'}

const router = new VueRouter({
    mode: 'hash', 
    routes: [
        {path: '/', component: login}, 
        {path: '/kategorije', component: kategorije}, 
        {path: '/masine', component: masine}, 
        {path: '/organizacije', component: organizacije}, 
        {path: '/dodajMasinu', component: dodajMasinu}, 
        {path: '/dodajKategoriju', component: dodajKategoriju}

    ]
});

var app = new Vue({
    router: router, 
    el: '#mainDiv'
});