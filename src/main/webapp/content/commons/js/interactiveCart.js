var allProducts;
initIntCart();
try { //Создание заказа
document.getElementById('buttonCreateOrder').addEventListener('click',function(){
    let orderInfo = new Array();
    for (let v of allProducts) {
        if (cart.contains(v.id)) {
            try {
                let tempProduct = new Object();
                tempProduct.id = v.id;
                tempProduct.count = v.quantityCount.value;
                orderInfo.push(tempProduct);
            }catch(e){}
        }
    }
    document.getElementById('productsJSON').value = JSON.stringify(orderInfo);
    document.getElementById('submitButton').click();
});
}catch(e){}
/*=========================================*/
function initIntCart() {
    allProducts = new Array();
    for (let v of document.querySelectorAll('*[int=\"product\"]')) {
        let vars = new Map();
        vars.set('id', v.getAttribute('product_id'));
        vars.set('block', v);
        for (let p of v.querySelectorAll('*[int]')){
            try {vars.set(p.getAttribute('int'),p);}
            catch(e){console.log(e);}
        }
        allProducts.push(Object.fromEntries(vars.entries()));
    }
    //init block
    for (let temp of allProducts) {
        try { //init quantity
            temp.quantitySub.onclick=function(){changeCount(temp,-1);}
            temp.quantityAdd.onclick=function(){changeCount(temp,+1);}
            temp.quantityCount.oninput=function(){changePrice(temp);}
            let xhr = new XMLHttpRequest();
            send("/productPrices/"+temp.id,function(){temp.priceList=JSON.parse(xhr.response);},xhr);
            temp.buttonToTrash.onclick=function(){cart.remove(temp.id);temp.block.remove();initIntCart();changeFinalPrice();}
        }catch(e){}

        try { //init buttonCart
            temp.buttonCart.onclick=function(){
                if (Boolean(this.checked)) {
                    cart.add(temp.id);
                    send('/addToCF?type=cart&productsCart=' + temp.id);
                    document.getElementById('cartText').textContent = 'Убрать из корзины';
                } else {
                    cart.remove(temp.id);
                    send('/removeFromCF?type=cart&productsCart=' + temp.id);
                    document.getElementById('cartText').textContent = 'Добавить в корзину';
                }
                console.log('cart: ' + cart.getAll());
            };
        }catch(e){}

        try { //init buttonFavorite
            temp.buttonFavorite.onclick=function(){
                if (Boolean(this.checked)) {
                    favorite.add(temp.id);
                    send('/addToCF?type=favorite&productsCart=' + temp.id);
                } else {
                    favorite.remove(temp.id);
                    send('/removeFromCF?type=favorite&productsCart=' + temp.id);
                }
                console.log('fav: ' + favorite.getAll());
            };
            if (favorite.contains(temp.id)) temp.buttonFavorite.setAttribute('checked','');
        }catch(e){}
    }
}
/*  Основное  */
function changePrice(product) {
	var input = product.quantityCount;
	var finalPrice = product.finalPrice;
	if (Number(input.value) < 1) {input.value = 1;}
	if (isNaN(input.value)) {input.value = 1;}
	let finalPriceValue = input.value * product.priceList.defaultPrice;
	let correct = 0;
	for (let v of product.priceList.discounts) {
	    if (Number(v.count) <= input.value) correct = (finalPriceValue / 100) * Number(v.value);
	}
	finalPrice.textContent = evenRound(finalPriceValue - correct);
	changeFinalPrice();
};
function changeCount(product, value) {
	var input = product.quantityCount;
	if (Number(input.value) + value >= 1) {
		input.value = Number(input.value) + value;
		changePrice(product);
	}
};
function changeFinalPrice() {
    let fin=0;
    for(let v of allProducts)try{fin+=Number(v.finalPrice.textContent);}catch(e){}
    try{document.getElementById("lastFullPrice").textContent=fin;}catch(e){}
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

function evenRound(num, decimalPlaces) {
    var d = decimalPlaces || 0;
    var m = Math.pow(10, d);
    var n = +(d ? num * m : num).toFixed(8); // Avoid rounding errors
    var i = Math.floor(n), f = n - i;
    var e = 1e-8; // Allow for rounding errors in f
    var r = (f > 0.5 - e && f < 0.5 + e) ? ((i % 2 == 0) ? i : i + 1) : Math.round(n);
    return d ? r / m : r;
}