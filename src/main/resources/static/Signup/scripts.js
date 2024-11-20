function generateRandomSixDigitNumber() {
    return Math.floor(100000 + Math.random() * 900000);
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

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





// Move the email variable inside the event listener to get the current value when the button is clicked
var email;
var otp;

document.addEventListener("DOMContentLoaded", function () {

    var currentpage = window.location.pathname;

    if (currentpage.includes("page1.html")) {
        document.getElementById("btn1").addEventListener('click', function (event) {
            // Prevent the form from submitting and refreshing the page
            event.preventDefault();

            email = document.getElementById("email1").value;
            otp = generateRandomSixDigitNumber();
            jobj = {
                email: email,
                otp: otp
            }

            fetch('http://localhost:8080/generation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' // Make sure to set the Content-Type header for JSON
                },
                body: JSON.stringify(jobj) // Send the data as JSON
            })
                .then(response => response.text()) // Parse the JSON response
                .then(data => {
                    console.log('Signup successful:', data);
                    window.location.href = "page2.html";
                    // You can handle success here, e.g., redirect to another page or show a message
                })
                .catch(error => {
                    console.error('Error during signup:', error);
                    // Handle any error that occurred
                });



        });
    }


    if (currentpage.includes("page2.html")) {

        document.getElementById("btn2").addEventListener('click', function (event) {
            // Prevent the form from submitting and refreshing the page
            event.preventDefault();

            jobj = {
                otp: document.getElementById("otp2").value
            }

            fetch('http://localhost:8080/verification', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' // Make sure to set the Content-Type header for JSON
                },
                body: JSON.stringify(jobj) // Send the data as JSON
            })
                .then(response => response.text()) // Parse the JSON response
                .then(data => {
                    if (data === "Verified") {
                        alert("Success");
                        window.location.href = "page3.html";
                    }
                    else {
                        alert("Wrong OTP");
                    }
                })
                .catch(error => {
                    console.error('Error during signup:', error);
                    // Handle any error that occurred
                });


        });
    }


    if (currentpage.includes("page3.html")) {


        document.getElementById("btn3").addEventListener('click', function (event) {
            event.preventDefault();
        
            const uname = document.getElementById("uname").value;
            const pwd = document.getElementById("pwd").value;
            const vpwd = document.getElementById("vpwd").value;
            const nname = document.getElementById("name").value;
        
            if (pwd !== vpwd) {
                alert("Passwords Mis-Match!!");
            } else {
                // Hash the password and then handle the fetch request
                hashWithSHA256(pwd).then(hashedContent => {
                    const hashedpwd = hashedContent; // Use the hashed password here
                    console.log("Hashed Password:", hashedpwd);
        
                    // Construct the JSON object with the hashed password
                    const jobj = {
                        'uname': uname,
                        'pwd': hashedpwd,
                        'name': nname
                    };
        
                    console.log("jobj:", jobj);
        
                    // Send the fetch request
                    fetch('http://localhost:8080/registration', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json' // Make sure to set the Content-Type header for JSON
                        },
                        body: JSON.stringify(jobj) // Send the data as JSON
                    })
                        .then(response => response.text()) // Parse the JSON response
                        .then(data => {
                            data = parseInt(data);
                            if (data > 0) {
                                alert("User Registration Succeeded!");
                                // Redirect if registration is successful
                                window.location.href = "/Login/login.html";
                            } else {
                                alert("User Registration Failed! Try again Later...");
                            }
                        })
                        .catch(error => {
                            console.error('Error during registration:', error);
                            // Handle any error that occurred
                        });
                }).catch(error => {
                    console.error("Error hashing password:", error);
                    // Handle any error that occurred during hashing
                });
            }
        });
        
    }

});
