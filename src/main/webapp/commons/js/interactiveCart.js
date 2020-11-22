/* Работа с количеством товаров и ценами */
const aInt = 'int';
const aId = 'idProduct';

//   Короче ООП (Ну почти)
var Product=function() {}
//id, quantitySub, quantityCount, quantityAdd, finalPrice, eco, ecoCount, ecoPrice, buttonAdd, buttonToTrash;
Product.prototype.setId=function(id) {return this.id=id;};
Product.prototype.getId=function() {return this.id;};
Product.prototype.init=function() {
	var idProduct = this.id;
	this.quantitySub.addEventListener('click',function(){change(get(idProduct), -1);});
	this.quantityAdd.addEventListener('click',function(){change(get(idProduct), +1);});
	this.quantityCount.oninput = function() {changePrice(get(idProduct));};
};

/*  Основное  */
function changePrice(product) {
	var input = product.quantityCount;
	var finalPrice = product.finalPrice;
	if (Number(input.value) < 1) {input.value = 1;}
	if (isNaN(input.value)) {input.value = 1;}
	let finalPriceValue;
	for (var i = 0; i < product.optPrices.length; i++) {
	    let t = product.optPrices[i]
	    if (t.c <= input.value) {
	        finalPriceValue = input.value * t.m;
	    }
	}
	finalPrice.textContent = finalPriceValue;
};
function change(product, value) {
	var input = product.quantityCount;
	if (Number(input.value) + value >= 1) {
		input.value = Number(input.value) + value;
		changePrice(product);
	}
};
/*  Блок начала  */

var Con = function() {}
var allProducts = [];

var lastFullEco;
var lastFullPrice;
var buttonCreateOrder;

init();

function contains(id) {
	let result = new Con();
	result.isCon=false;
	for (var i = 0; i < allProducts.length; i++) {
		if (allProducts[i].id == id) {
			result.isCon=true;
			result.element=allProducts[i];
		}
	}
	return result;
}

function init() {
	let all = document.querySelectorAll('*[int]');
	for (var i = 0; i < all.length; i++) {
		let con = contains(all[i].getAttribute(aId));
		let product;
		if (con.isCon) {
			product = con.element;
		} else {
			product = new Product();
			allProducts.push(product);
			product.id = all[i].getAttribute(aId);
		}
		
		switch(all[i].getAttribute(aInt)) {
			case 'quantitySub':   product.quantitySub = all[i]; break;
			case 'quantityCount': product.quantityCount = all[i]; break;
			case 'quantityAdd': product.quantityAdd = all[i]; break;
			case 'finalPrice': product.finalPrice = all[i]; break;
			case 'eco': product.eco = all[i]; break;
			case 'ecoCount': product.ecoCount = all[i]; break;
			case 'ecoPrice': product.ecoPrice = all[i]; break;
			case 'buttonAdd': product.buttonAdd = all[i]; break;
			case 'buttonToTrash': product.buttonToTrash = all[i]; break;

			case 'lastFullEco': lastFullEco = all[i]; break;
			case 'lastFullPrice': lastFullPrice = all[i]; break;
			case 'buttonToTrash': buttonCreateOrder = all[i]; break;

			default: console.log('Warning: ' + all[i].getAttribute(aInt)); break;
		}
	}
	for (var i = 0; i < allProducts.length; i++) {
		try {
		    let temp = allProducts[i];
			temp.init();
				let xhr = new XMLHttpRequest();
                xhr.open("GET", "/productPrices/" + allProducts[i].id);
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.onload = function() {
                    temp.optPrices = JSON.parse(xhr.response);
                    console.log(temp.optPrices);
                };
                xhr.onerror = function() {console.log(`Ошибка соединения`);};
                xhr.send();
		} catch (e) {
			console.log(e);
		}
	}
}
function get(id) {return contains(id).element;}

/*==========*/
var optPrices;
function optPricesInit(argument) {
	optPrices = JSON.parse(argument);
	console.log(optPrices);
	for (var i = 0; i < argument.length; i++) {
		console.log(argument[i]);
	}
}

function sendJSON(url, idEl) {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", url);
	xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');

	xhr.onload = function() {
		console.log(`Загружено: ${xhr.status} ${xhr.response}`);
		let el = contains(idEl);
		el.optPrices = xhr.response;
		console.log('f')
	};
	xhr.onerror = function() {console.log(`Ошибка соединения`);};

	xhr.send();
}