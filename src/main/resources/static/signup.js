// The URL of your Spring Boot endpoint
const url = 'http://localhost:8080/signup';

// The data you want to send, such as the username and password
const loginData = {
  username: 'nirmal',
  password: 'yourPassword',
  email: '@gmail.com'
};

fetch('http://localhost:8080/signup', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(loginData)
})
.then(response => {
  // Check if the response is not empty and can be parsed as JSON
  if (response.ok) {
    return response.json();
  } else {
    return response.text(); // In case it's not JSON, return it as text
  }
})
.then(data => {
  console.log('Response:', data); // Handle the response (can be JSON or text)
})
.catch(error => {
  console.error('Error during login:', error);
});
