// Local Storage
var Storage=function(type) {
    this.type = type;
    this.countView = document.getElementById(type);
    let count = this.getAll().length;
    this.countView.textContent = count;
    if (count > 0) this.countView.removeAttribute('hidden','');
    if (count < 1) this.countView.setAttribute('hidden','');
}
Storage.prototype.contains=function(id) {
    var array = this.getAll();
    var index = array.indexOf(id);
    if (index > -1) return true;
    return false;
}
Storage.prototype.add=function(id) {
    var array = this.getAll();
    for (var i = 0; i < array.length; i++) if (array[i] == id) return;
    array.push(id);
    localStorage.setItem(this.type, JSON.stringify(array));
    let count = array.length;
    this.countView.textContent = count;
    if (count > 0) this.countView.removeAttribute('hidden','');
}
Storage.prototype.remove=function(id) {
    var array = this.getAll();
    var index = array.indexOf(id);
    if (index > -1) array.splice(index, 1);
    localStorage.setItem(this.type, JSON.stringify(array));
    let count = array.length;
    this.countView.textContent = count;
    if (count < 1) this.countView.setAttribute('hidden','');
}
Storage.prototype.clear=function() {
    localStorage.removeItem(this.type);
    this.countView.textContent = 0;
    this.countView.setAttribute('hidden','');
}
Storage.prototype.getAll=function() {
    var array = localStorage.getItem(this.type);
    if (array == null) {
        return new Array();
        localStorage.setItem(this.type, JSON.stringify(array));
    }
    return JSON.parse(array);
}
// Header Init
var cart = new Storage('countCart');
var favorite = new Storage('countFavorite');
init1();
init2();
function init1() {
    let xhr = new XMLHttpRequest();
    let url = '/getCF?type=cart';
    xhr.onload = function() {
        try {
            try {
                let array = JSON.parse(xhr.response);
                for (var i = 0; i < array.length; i++) cart.add(array[i]);
            } catch (e) {console.log(e)}
            let xhrC = new XMLHttpRequest();
            urlC = '/addToCF?type=cart&productsCart=';
            let arrayC = cart.getAll();
            for (var i = 0; i < arrayC.length; i++) urlC += arrayC[i] + ' ';
            xhr.open("GET", urlC);
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.onload = function() {console.log(xhr.response);};
            xhr.onerror = function() {console.log(`Ошибка соединения`);};
            xhr.send();
        } catch (e){console.log(e)}
    };
    xhr.open("GET", url);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onerror = function() {console.log(`Ошибка соединения`);};
    xhr.send();
}
function init2() {
    let xhr = new XMLHttpRequest();
    let url = '/getCF?type=favorite';
    xhr.onload = function() {
        try {
            try {
                let array = JSON.parse(xhr.response);
                for (var i = 0; i < array.length; i++) favorite.add(array[i]);
            } catch (e) {console.log(e)}
            let xhrF = new XMLHttpRequest();
            urlF = '/addToCF?type=favorite&productsCart=';
            let arrayF = favorite.getAll();
            for (var i = 0; i < arrayF.length; i++) urlF += arrayF[i] + ' ';
            xhr.open("GET", urlF);
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.onload = function() {console.log(xhr.response);};
            xhr.onerror = function() {console.log(`Ошибка соединения`);};
            xhr.send();
        } catch (e){console.log(e)}
    };
    xhr.open("GET", url);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onerror = function() {console.log(`Ошибка соединения`);};
    xhr.send();
}