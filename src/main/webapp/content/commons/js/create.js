document.getElementById("bCreate").addEventListener('click',function(){ipl();itc();document.getElementById("bSubmit").click();});
function ipl() {
    let priceList = document.getElementById("priceList");
    let temp = new Object();
    let discounts = new Array();
    for(let v of priceList.querySelectorAll("*[int]")) {
        if(v.getAttribute('int')=='defaultPrice') temp.defaultPrice = v.value;
        if(v.getAttribute('int')=='discount') {
            let discount = new Object();
            for(let t of v.querySelectorAll("*[int]")) {
                if(t.getAttribute('int')=='discountCount') discount.count = t.value;
                if(t.getAttribute('int')=='discountValue') discount.value = t.value;
            }
            discounts.push(discount);
        }
    }
    temp.discounts = discounts;
    document.getElementById("priceListInput").value = JSON.stringify(temp);
}
function itc() {
    let tableCharacteristics = document.getElementById("tableCharacteristics");
    let trs = tableCharacteristics.querySelectorAll("tr");
    let cells = tableCharacteristics.querySelectorAll("input");
    let i = (cells.length-1) / (trs.length - 2);
    let temp = new Array();
    let row = new Array();
    for(let v = 0; v < cells.length; v++) {
        if(v % i == 0 && v != 0) {
            temp.push(row);
            row = new Array();
        }
        row.push(cells[v].value);
    }
    document.getElementById("characteristics").value = JSON.stringify(temp);
}