function send(url, onLoad, xhr, method="GET", body) {
    if (xhr == undefined) xhr=new XMLHttpRequest();
    xhr.open(method, url);

    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = onLoad;
    if (onLoad == undefined) xhr.onload = function(){console.log("'"+url+"': "+xhr.response);};
    xhr.onerror = function() {console.log("'" + url + "': " + "Ошибка соединения");};
    if (body == undefined) xhr.send(); else xhr.send(body);
}