var ws;

function connect() {
    var username = document.getElementById("username").value;
    
    var host = document.location.host;
    var pathname = document.location.pathname;
    
    ws = new WebSocket("ws://" +"cs309-sd-7.misc.iastate.edu:8080"+"/websocket" + "/"+username);

    ws.onmessage = function(event) {
    var log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML += event.data + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;
    
    ws.send(content);
}