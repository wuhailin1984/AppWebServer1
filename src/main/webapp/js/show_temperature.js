/**
 * Created by Hailin on 2018/5/28.
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
    {
    	document.getElementById("btn_refresh_office").click();  //
    	document.getElementById("btn_refresh_home").click();  //
    	document.getElementById("btn_refresh_some_where").click();  //
    }  
}


window.onload = function () {
	document.getElementById("office_temp").innerHTML="11";
	
	//var ws = new WebSocket("ws://35.204.196.49:8080/AppWebServer1/websocketTemp");  //GCP
	var ws = new WebSocket("ws://localhost:8081/AppWebServer1/websocketTemp");  
	if (typeof ws == "undefined") {
		document.getElementById("office_temp").innerHTML="22";
	}
	
	ws.onopen = function(evt) { 
	  console.log("Connection open ..."); 
	  ws.send("Sensor1");
	  ws.send("Sensor2");
	  ws.send("Sensor3");
	};

	ws.onmessage = function(evt) {
	  console.log( "Received Message: " + evt.data);
	  
	  var receive_data=evt.data;
	  var len=receive_data.length;
	  
	  if(len>19) {               //example: length of Sensor1RealTempFlag  is 7+12  (19)		          
		  var flag=receive_data.substring(len-12,len);
		  if(flag=="RealTempFlag"){
			  var name=receive_data.substring(len-19,len-12);
			  var temp=receive_data.substring(0,len-19);
			  
			  if(name=="Sensor1"){
				  document.getElementById("office_temp").innerHTML=temp;
			  }
			  else if(name=="Sensor2"){
				  document.getElementById("home_temp").innerHTML=temp;
			  }
			  else if(name=="Sensor3"){
				  document.getElementById("somewhere_temp").innerHTML=temp;
			  }		  
		  }
		  else{		  
			  var name=receive_data.substring(len-7,len);
			  var temptime=receive_data.substring(0,len-7);
			  if(name=="Sensor1"){
				  document.getElementById("office_temp_time").innerHTML=temptime;
			  }
			  else if(name=="Sensor2"){
				  document.getElementById("home_temp_time").innerHTML=temptime;
			  }
			  else if(name=="Sensor3"){
				  document.getElementById("somewhere_temp_time").innerHTML=temptime;
			  }
		  }	  
	  }
	  
	  /*
	  var receive_data=evt.data;
	  var len=receive_data.length;
	  var index=receive_data.indexOf("***");
	  if(index>0){
		  var temp=receive_data.substring(0,index);
		  var time=receive_data.substring(index+3,len-7);
		  var name=receive_data.substring(len-7,len);
		  if(name=="Sensor1"){
			  document.getElementById("office_temp").innerHTML=temp;
			  document.getElementById("office_temp_time").innerHTML=time;
		  }
		  else if(name=="Sensor2"){
			  document.getElementById("home_temp").innerHTML=temp;
			  document.getElementById("home_temp_time").innerHTML=time;
		  }
	  }
	  */

	  //ws.close();
	};

	ws.onclose = function(evt) {
	  console.log("Connection closed.");
	};   
	
	
	var btn_refresh_office = document.getElementById("btn_refresh_office");
	var btn_refresh_home = document.getElementById("btn_refresh_home");
	var btn_refresh_some_where = document.getElementById("btn_refresh_some_where");

	if(typeof btn_refresh_office == "undefined")  console.log("button error.");

	btn_refresh_office.onclick = function refresh_office() {
	  console.log("request for Sensor1 data ..."); 
	  ws.send("Sensor1");
	}

	btn_refresh_home.onclick = function refresh_home() {
		console.log("request for Sensor2 data ..."); 
		ws.send("Sensor2");
	}

	btn_refresh_some_where.onclick = function refresh_some_where() {
		console.log("request for Sensor3 data ..."); 
		ws.send("Sensor3");
	}		 
}


window.onunload = function() {
	if (typeof ws != "undefined") {
		ws.close();
	}
}

