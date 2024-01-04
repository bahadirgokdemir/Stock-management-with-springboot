document.getElementById('loginForm').onsubmit = function(event) {
    event.preventDefault();
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/login", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = '/main.html';
        } else if (xhr.readyState === 4) {
            alert("Login failed");
        }
    };

    var userData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    xhr.send(JSON.stringify(userData));
};
