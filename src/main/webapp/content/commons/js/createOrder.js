var button = document.getElementById("bSubmit");
button.addEventListener('click',function(){send();});

var firstSend = true;

var npd = document.getElementById('deliveryNewPostDiv');
var upd = document.getElementById('deliveryUkrPostDiv');
npd.addEventListener('click',function(){tog()});
upd.addEventListener('click',function(){tog()});
function tog() {
    myToggle(document.getElementById('districtDiv'), 'style', 'display:none;');
    myToggle(document.getElementById('findPostOfficeDiv'), 'style', 'display:none;');
    let o = npd.querySelector('input');
        o.toggleAttribute('checked');
    let a = upd.querySelector('input');
        a.toggleAttribute('checked');
}
function myToggle(element, attr, val) {
    if (!element.hasAttribute(attr)) {element.setAttribute(attr, val);}
    else {element.removeAttribute(attr, val);}
}

function send() {
    var finalObject = new Object();
    var validRes = new Array();

    //id товаров и количество
    let all = document.querySelectorAll('*[prodId]');
    let array = new Array();
    for (var i = 0; i < all.length; i++) {
        let info = new Object();
        info.id = all[i].getAttribute("prodId");
        info.count = all[i].innerText;
        array.push(info);
    }
    finalObject.ProductsInfo = array;

    //Контактные данные
    finalObject.user = new Object();
    //Валидация контактов
    let o = new Object();
    o.el = document.getElementById("firstName");
    o.isValid = false;
    if (o.el.value.length >= 2) {
        o.isValid = true;
        finalObject.user.firstName = o.el.value;
    }
    validRes.push(o);

    o = new Object();
    o.el = document.getElementById("lastName");
    o.isValid = false;
    if (o.el.value.length >= 2) {
        o.isValid = true;
        finalObject.user.lastName = o.el.value;
    }
    validRes.push(o);

    o = new Object();
    o.el = document.getElementById("phone");
    o.isValid = false;
    if (o.el.value.length >= 10) {
        o.isValid = true;
        finalObject.user.phone = o.el.value;
    }
    validRes.push(o);

    o = new Object();
    o.el = document.getElementById("email");
    o.isValid = false;
    if (o.el.value.length >= 5) {
        o.isValid = true;
        finalObject.user.phone = o.el.value;
    }
    validRes.push(o);

    //Способ и адрес доставки
    finalObject.delivery = new Object();
    let delivery = document.getElementsByName("delivery");
    let address = document.getElementsByName("address");
    for (var i = 0; i < delivery.length; i++) {
        if (delivery[i].checked) {
            finalObject.delivery.method = delivery[i].value;
            o = new Object();
            o.el = address[i];
            o.isValid = false;
            if (o.el.value.length >= 10) {
                o.isValid = true;
                finalObject.delivery.address = o.el.value;
            }
            validRes.push(o);
        }
    }

    //Способ оплаты
    let payMethod = document.getElementsByName("payMethod");
    for (var i = 0; i < payMethod.length; i++) {
        if (payMethod[i].checked) finalObject.payMethod = payMethod[i].value;
    }
    //комментарий к заказу
    finalObject.comment = document.getElementById("comment").value;

    //Итоги валидаций
    let loseValid = false;
    for (var i = 0; i < validRes.length; i++) {
        if (!Boolean(validRes[i].isValid)) {
            loseValid = true;
            validRes[i].el.setAttribute("err", "");
        } else {
            validRes[i].el.removeAttribute("err", "");
        }
    }
    if (Boolean(loseValid)) return;

    //Отправка запроса
    if (Boolean(firstSend)) {
        firstSend = false;
        document.getElementById("iSend").value = JSON.stringify(finalObject);
        document.getElementById("bSend").click();
    }
}