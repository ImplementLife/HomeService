initTableCreator();
function initTableCreator() {
    for (let i of document.querySelectorAll('*[tableInt]')) {
        i.onclick = function() {
            let attrVal = this.getAttribute('tableInt').split(" ");
            let pos = getPos(this.parentElement);
            let table = this.parentElement.parentElement.parentElement.parentElement;
            //if (confirm('Подтвердите действие')) {
                if (attrVal.indexOf("addNewRow") >= 0) addRow(table, pos);
                if (attrVal.indexOf("RemoveRow") >= 0) removeRow(table, pos.row);
                if (attrVal.indexOf("addNewCol") >= 0) addCol(table, pos);
                if (attrVal.indexOf("RemoveCol") >= 0) removeCol(table, pos.col);
                initTableCreator();
            //}
        };
    }
}

function getPos(cell) {
    let result = new Object();
    let td = cell.parentElement;
    result.col = td.cellIndex;
    result.row = td.parentElement.rowIndex;
    return result;
}
function newCell(element) {
    let td = document.createElement('td');
    let divCell = document.createElement('div');
    divCell.className = "cell";
    if (Array.isArray(element)) {
        for (var i = 0; i < element.length; i++) {
            divCell.insertAdjacentElement("beforeend", element[i]);
        }
    } else divCell.insertAdjacentElement("afterbegin", element);
    td.insertAdjacentElement("afterbegin", divCell);
    return td;
}

function getInputCell() {
    let input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("placeholder", "-");
    return newCell(input);
}
function getAddRemoveCell(isForRow) {
    let typeAdd = "addNewRow";
    let typeRemove = "RemoveRow";
    if (!Boolean(isForRow)) {
        typeAdd = "addNewCol";
        typeRemove = "RemoveCol";
    }

    let divAdd = document.createElement("div");
    divAdd.className = "button";
    divAdd.setAttribute("tableInt", typeAdd);
        let imgAdd = document.createElement("img");
        imgAdd.setAttribute("src", "/resources/com/img/add-black-36dp.svg");
        imgAdd.setAttribute("alt", typeAdd);
    divAdd.insertAdjacentElement("beforeend", imgAdd);

    let divRemove = document.createElement("div");
    divRemove.className = "button";
    divRemove.setAttribute("tableInt", typeRemove);
        let imgRemove = document.createElement("img");
        imgRemove.setAttribute("src", "/resources/com/img/delete-black-36dp.svg");
        imgRemove.setAttribute("alt", typeRemove);
    divRemove.insertAdjacentElement("beforeend", imgRemove);

    let result = new Array();
    result.push(divAdd);
    result.push(divRemove);
    return newCell(result);
}

function addRow(table, pos) {
    let row = table.rows[pos.row];
    let newRow = document.createElement("tr");
    newRow.insertAdjacentElement("beforeend", getAddRemoveCell(true));
    for (var i = 2; i < table.rows[0].cells.length; i++) newRow.insertAdjacentElement("beforeend", getInputCell());
    row.insertAdjacentElement("beforebegin", newRow);
}
function addCol(table, pos) {
    let rows = table.rows;
    let temp = rows[0].cells[pos.col];
    temp.insertAdjacentElement("beforebegin", getAddRemoveCell(false));
    for (var i = 1; i < rows.length-1; i++) {
        let temp = rows[i].cells[pos.col];
        if (temp != undefined) temp.insertAdjacentElement("beforebegin", getInputCell());
        else rows[i].insertAdjacentElement("beforeend", getInputCell());
    }
}

function removeRow(table, index) {table.rows[index].remove();}
function removeCol(table, index) {
    let rows = table.rows;
    for (var i = 0; i < rows.length-1; i++) {let element = rows[i].children[index].remove();}
}