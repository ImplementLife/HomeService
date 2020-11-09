const button = window.document.getElementById("theme-button");
const html = window.document.getElementsByTagName('html')[0];
var isDark = localStorage.getItem('isDark');
if (isDark == null) {isDark = 'false';}
if (isDark == 'true') {button.setAttribute('checked', 'checked');}
setTheme();
button.addEventListener("click", function() {changeTheme();});
function setTheme() {
    if (isDark == 'true') {html.setAttribute('dark', '');}
    else {html.removeAttribute('dark', '');}
    localStorage.setItem('isDark', isDark);
}
function changeTheme() {
    if (isDark == 'true') {isDark = 'false';}
    else {isDark = 'true';}
    setTheme();
}