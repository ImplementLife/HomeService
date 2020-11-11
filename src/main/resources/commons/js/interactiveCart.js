/* Работа с количеством товаров и ценами */
const aInt = 'int';
const aId = 'idProduct';
var defaultPrice = 30;

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
	finalPrice.textContent = input.value * defaultPrice;
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
			allProducts[i].init();
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


/* метод-заглушка */
function get() {
	let temp = "[{\"1\":{\"1\":\"29.99\",\"5\":\"23.50\",\"10\":\"18.40\"}},{\"2\":{\"1\":\"400\",\"5\":\"365\",\"10\":\"330\"}}]";
	return temp;
}

optPricesInit(get());

function getOpt() {
	let temp = "{\"productID\":[";
	for (var i = 0; i < allProducts.length; i++) {
		//if (allProducts[i].id != null) {}
		temp += "\"" + allProducts[i].id + "\"";
		if (i != allProducts.length - 1) {temp += ",";}
	}
	temp += "]}";
	
	//sendJSON("/fe", temp);
	optPricesInit(temp);
}

function sendJSON(url, jsonString) {
	let xhr = new XMLHttpRequest();

	//let json = JSON.stringify({
	//	name: "Вася",surname: "Петров"
	//});

	xhr.open("POST", url);
	xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');

	xhr.onload = function() {
		console.log(`Загружено: ${xhr.status} ${xhr.response}`);
		optPricesInit(xhr.response);
	};
	xhr.onerror = function() {console.log(`Ошибка соединения`);};

	xhr.send(jsonString);
}