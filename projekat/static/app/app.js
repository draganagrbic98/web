const login = {template: '<login></login>'}
const kategorije = {template: '<kategorije></kategorije>'}
const masine = {template: '<masine></masine>'}
const organizacije = {template: '<organizacije></organizacije>'}
const dodajMasinu = {template: '<dodajMasinu></dodajMasinu>'}
const dodajKategoriju = {template: '<dodajKategoriju></dodajKategoriju>'}
const dodajOrganizaciju = {template: '<dodajOrganizaciju></dodajOrganizaciju>'}
const diskovi = {template: '<diskovi></diskovi>'}
const dodajDisk = {template: '<dodajDisk></dodajDisk>'}

const router = new VueRouter({
    mode: 'hash', 
    routes: [
        {path: '/', component: login}, 
        {path: '/kategorije', component: kategorije}, 
        {path: '/masine', component: masine}, 
        {path: '/organizacije', component: organizacije},
        {path: '/diskovi', component: diskovi},
        {path: '/dodajMasinu', component: dodajMasinu}, 
        {path: '/dodajKategoriju', component: dodajKategoriju}, 
        {path: '/dodajOrganizaciju', component: dodajOrganizaciju},
        {path: '/dodajDisk', component: dodajDisk}
    ]
});

var app = new Vue({
    router: router, 
    el: '#mainDiv'
});