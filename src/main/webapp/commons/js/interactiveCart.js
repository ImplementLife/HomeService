/* Работа с количеством товаров и ценами */
const aInt = 'int';
const aId = 'idProduct';
var cart = new Storage('countCart');
var favorite = new Storage('countFavorite');

//   Короче ООП (Ну почти)
var Product = function() {this.vars = {};}
//id, quantitySub, quantityCount, quantityAdd, finalPrice, eco, ecoCount, ecoPrice, buttonAdd, buttonToTrash;
Product.prototype.addVar=function(name, value) {this.vars[name] = value;}
Product.prototype.init=function() {
	var idProduct = this.id;
	this.quantitySub.addEventListener('click',function(){change(get(idProduct), -1);});
	this.quantityAdd.addEventListener('click',function(){change(get(idProduct), +1);});
	this.quantityCount.oninput = function() {changePrice(get(idProduct));};

	try {
	    var b = this.buttonCart;
        this.buttonCart.addEventListener('click',function(){
            let xhr = new XMLHttpRequest();
            let url;
            if (Boolean(b.checked)) {
                cart.add(idProduct);
                url = '/addToCF?type=cart&productsCart=' + idProduct;
                document.getElementById('cartText').textContent = 'Убрать из корзины';
            } else {
                cart.remove(idProduct);
                url = '/removeFromCF?type=cart&productsCart=' + idProduct;
                document.getElementById('cartText').textContent = 'Добавить в корзину';
            }
            console.log('cart: ' + cart.getAll());
            try {
                xhr.open("GET", url);
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.onload = function() {console.log(xhr.response);};
                xhr.onerror = function() {console.log(`Ошибка соединения`);};
                xhr.send();
            } catch (e) {console.log(e);}
        });
	} catch(e) {console.log(e);}

	try {
	    var bf = this.buttonFavorite;
        this.buttonFavorite.addEventListener('click',function(){
            let xhr = new XMLHttpRequest();
            let url;
            if (Boolean(bf.checked)) {
                favorite.add(idProduct);
                url = '/addToCF?type=favorite&productsCart=' + idProduct;
            } else {
                favorite.remove(idProduct);
                url = '/removeFromCF?type=favorite&productsCart=' + idProduct;
            }
            console.log('fav: ' + favorite.getAll());
            try {
                xhr.open("GET", url);
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.onload = function() {console.log(xhr.response);};
                xhr.onerror = function() {console.log(`Ошибка соединения`);};
                xhr.send();
            } catch (e) {console.log(e);}
        });
	} catch(e) {console.log(e);}
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
	changeFinalPrice();
};
function change(product, value) {
	var input = product.quantityCount;
	if (Number(input.value) + value >= 1) {
		input.value = Number(input.value) + value;
		changePrice(product);
	}
};
function changeFinalPrice() {
    let fin = 0;
    for (var i = 0; i < allProducts.length; i++) {
        try {
            fin += Number(allProducts[i].finalPrice.textContent);
        } catch (e) {
            console.log(e);
        }
    }
    document.getElementById("lastFullPrice").textContent = fin;
}

/*  Блок начала  */
var allProducts = [];

var lastFullEco;
var lastFullPrice;
var buttonCreateOrder;

init();
try {
buttonCreateOrder.addEventListener('click',function(){
    let orderInfo = new Array();
    for (var i = 0; i < allProducts.length; i++) {
        if (allProducts[i].id != null) {
            let tempProduct = new Object();
            tempProduct.id = allProducts[i].id;
            tempProduct.count = allProducts[i].quantityCount.value;
            orderInfo.push(tempProduct);
        }
    }
    console.log(JSON.stringify(orderInfo));
    document.getElementById('productsJSON').value = JSON.stringify(orderInfo);
    document.getElementById('submitButton').click();
});
} catch(e){}
function contains(id) {
	let result = new Object();
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

			case 'buttonCart': product.buttonCart = all[i]; break;
			case 'buttonFavorite': product.buttonFavorite = all[i]; break;

			case 'lastFullEco': lastFullEco = all[i]; break;
			case 'lastFullPrice': lastFullPrice = all[i]; break;
			case 'buttonCreateOrder': buttonCreateOrder = all[i]; break;

			default: console.log('Warning: ' + all[i].getAttribute(aInt)); break;
		}
	}
	for (var i = 0; i < allProducts.length; i++) {
//	    try {
//	        temp.buttonCart.setAttribute('checked','');
//	    } catch(e) {
//	        console.log(e);
//	    }
		try {
		    let temp = allProducts[i];
			temp.init();
				let xhr = new XMLHttpRequest();
                xhr.open("GET", "/productPrices/" + allProducts[i].id);
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.onload = function() {temp.optPrices = JSON.parse(xhr.response);};
                xhr.onerror = function() {console.log(`Ошибка соединения`);};
                xhr.send();
		} catch (e) {
			console.log(e);
		}
	}
}
function get(id) {return contains(id).element;}