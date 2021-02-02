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
    if (-1 < this.getAll().indexOf(id)) return true;
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
init(cart, 'cart');
init(favorite, 'favorite');
function init(storage, type) {
    let xhr = new XMLHttpRequest();
    onLoad = function() {
        try {
            try {
                let array = JSON.parse(xhr.response);
                for (var i = 0; i < array.length; i++) storage.add(array[i]);
            } catch (e){console.log(e)}
            urlF = '/addToCF?type=' + type + '&productsCart=';
            let arrayF = storage.getAll();
            for (var i = 0; i < arrayF.length; i++) urlF += arrayF[i] + ' ';
            send(urlF);
        } catch (e){console.log(e)}
    };
    send('/getCF?type=' + type, onLoad, xhr);
}
function send(url, onLoad, xhr) {
    if (xhr == undefined) xhr = new XMLHttpRequest();
    xhr.open("GET", url);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = onLoad;
    if (onLoad == undefined) xhr.onload = function() {console.log("'" + url + "': " + xhr.response);};
    xhr.onerror = function() {console.log("'" + url + "': " + "Ошибка соединения");};
    xhr.send();
}