/**
 * Created by Hailin on 2018/5/8.
 */

function keyLogin(event) {

    var browser = navigator.appName;
    var userAgent = navigator.userAgent;
    var code;
    if(browser.indexOf('Internet')>-1) //IE
    code = window.event.keyCode;
    else if(userAgent.indexOf("Firefox")>-1)  //Firefox
    code = event.which;
    else  //other browsers
    code = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;

    if ( code == 13)  //13 is the key value of Enter
        document.getElementById("btn_login").click();  
}


window.onload = function () {
    var btn_register = document.getElementById('btn_register');
    btn_register.onclick = function register() {
        window.open("register.html");
    }
}


