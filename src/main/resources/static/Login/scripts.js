async function hashWithSHA256(input) {
    // Encode the input string as a Uint8Array
    const encoder = new TextEncoder();
    const data = encoder.encode(input);

    // Hash the data using SHA-256
    const hashBuffer = await crypto.subtle.digest('SHA-256', data);

    // Convert the ArrayBuffer to a hex string
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

    return hashHex;
}



document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("btn").addEventListener('click', function (event) {
        event.preventDefault();

        pwd = document.getElementById("pwd").value;

        // Hash the password and then handle the fetch request
        hashWithSHA256(pwd).then(hashedContent => {
            const hashedpwd = hashedContent; // Use the hashed password here
            console.log("Hashed Password:", hashedpwd);
            email = document.getElementById("email").value;
            // Construct the JSON object with the hashed password
            const jobj = {
                'email': email,
                'pwd': hashedpwd,
            };

            console.log("jobj:", jobj);

            // Send the fetch request
            fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' // Make sure to set the Content-Type header for JSON
                },
                body: JSON.stringify(jobj) // Send the data as JSON
            })
                .then(response => response.text()) // Parse the JSON response
                .then(data => {
                    if (data === "0") {
                        alert("Login Failed !!");

                    } else {

                        alert("Login Success !!");
                        // Redirect to the new page and pass the username as a query parameter
                        window.location.href = `/Home/index.html?username=${encodeURIComponent(data)}`;
                    }
                })
        })
            .catch(error => {
                console.error('Error during registration:', error);
                // Handle any error that occurred
            });
    }).catch(error => {
        console.error("Error hashing password:", error);
        // Handle any error that occurred during hashing
    });

});