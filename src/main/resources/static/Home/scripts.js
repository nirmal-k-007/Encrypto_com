// Function to open a chat window
function openChat(userName) {
    // Display the chat window
    document.getElementById('chatWindow').style.display = 'block';
    document.getElementById('chatWithUser').innerText = `Chat with: ${userName}`;

    // Clear existing messages (if any)
    document.getElementById('chatMessages').innerHTML = '';

    // Example of adding a sample message (you would use WebSocket or AJAX for real-time data)
    document.getElementById('chatMessages').innerHTML += `<div><strong>${userName}</strong>: Hi! How are you?</div>`;
}



// Get the query string from the URL
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

// Retrieve the username from the query parameters
const myuname = urlParams.get('username');
console.log("Username:", myuname);

// Set up WebSocket connection
const socket = new WebSocket(`ws://localhost:8080/websocket-endpoint?username=${myuname}`);

// Handle the connection opening
socket.onopen = function (event) {
    console.log("WebSocket connection opened successfully");
};

// Handle incoming messages from the server
socket.onmessage = function (event) {
    try {
        const obj = JSON.parse(event.data);
        console.log(obj);

        if (obj["messageType"] === "update") {
            const arr = obj["list"];

            // Get the user list container
            const userListContainer = document.getElementById("userList");

            // Clear the old list
            userListContainer.innerHTML = "";

            // Create and append new elements
            arr.forEach(element => {
                const ele = document.createElement('div');
                ele.textContent = element;
                ele.className = "user-item";
                ele.id = element;
                userListContainer.appendChild(ele);
                console.log(element);
            });
        }
        else if(obj["messageType"]==="message")
        {
            console.log(obj["messageContent"])
            const userName = document.getElementById('chatWithUser').innerText.replace('Chat with: ', '');
            document.getElementById('chatMessages').innerHTML += `<div><strong>${userName}</strong>: ${obj["messageContent"]}</div>`;
            
        }
    } catch (error) {
        console.error("Error handling message:", error);
    }
};

// Handle errors
socket.onerror = function (error) {
    console.error("WebSocket error: " + error);
};

// Handle connection close
socket.onclose = function (event) {
    console.log("WebSocket connection closed: " + event.reason);
};

// Use event delegation to handle clicks on dynamically created elements
document.getElementById("userList").addEventListener("click", function (event) {
    // Check if the clicked element has the class "user-item"
    if (event.target && event.target.classList.contains("user-item")) {
        const userName = event.target.textContent;
        console.log("Clicked on:", userName);

        // Call the function to open a chat window
        openChat(userName);
    }
});


// Function to send a message
function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value;

    if (message.trim() !== '') {
        const userName = document.getElementById('chatWithUser').innerText.replace('Chat with: ', '');
        document.getElementById('chatMessages').innerHTML += `<div><strong>You</strong>: ${message}</div>`;
        messageInput.value = ''; // Clear the input field

        // Scroll to the bottom of the chat
        document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight;
        jobj = {
            "messageType":"message",
            "from": myuname,
            "to":userName,
            "messageContent":message
        }
        socket.send(JSON.stringify(jobj));
    }
}
