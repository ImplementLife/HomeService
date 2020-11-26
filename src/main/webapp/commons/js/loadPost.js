/*
var obl = document.getElementById("obl");
var town = document.getElementById("town");
var otd = document.getElementById("otd");

function get(body, met) {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "https://api.novaposhta.ua/v2.0/json/");
    xhr.setRequestHeader("Content-type", "application/json;");
	xhr.onload = function() {
	    if (met == "obl") initObl(xhr.response);
	    if (met == "town") initTown(xhr.response);
	    if (met == "otd") initOtd(xhr.response);
    };
	xhr.onerror = function() {console.log(`Ошибка соединения`);};
	xhr.send(body);
}
function app(element, val) {
    let div = document.createElement('option');
    //div.className = "alert";
    div.innerHTML = val;
    element.append(div);
}
/*=============   Загрузка областей   ===============*//*
var body = "{\"modelName\":\"Address\",\"calledMethod\":\"getAreas\",\"methodProperties\":{}}";
get(body, "obl");
function initObl(res) {
    let temp = JSON.parse(res)
    var data = temp.data;
    for (var i = 1; i < data.length; i++) app(obl, data[i].DescriptionRu);
}
town.addEventListener('click',function(){
    let temp = obl.querySelectorAll("[selected]");
    console.log(temp);
    let town = "{\"apiKey\":\"[ВАШ КЛЮЧ]\",\"modelName\":\"Address\",\"calledMethod\":\"getWarehouses\",
                 \"methodProperties\": {\"CityName\": \"київ\",\"Limit\": 5}}";
    get(town, "town");
})*/
