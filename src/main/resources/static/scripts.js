const socket = new WebSocket("ws://localhost:8080/websocket-endpoint");

// When the connection is open
socket.onopen = function() {
    console.log("WebSocket connection opened.");
    socket.send("Hello from client!");
};

// When a message is received from the server
socket.onmessage = function(event) {
    console.log("Message from server:", event.data);
};

// When the connection is closed
socket.onclose = function() {
    console.log("WebSocket connection closed.");
};

// Handle errors
socket.onerror = function(error) {
    console.error("WebSocket error:", error);
};
