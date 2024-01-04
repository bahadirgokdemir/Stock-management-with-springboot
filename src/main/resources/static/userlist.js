document.addEventListener('DOMContentLoaded', function() {
    var userList = document.getElementById('userList');
    var users = ['User1', 'User2', 'User3']; // Örnek kullanıcı adları

    users.forEach(function(user) {
        var li = document.createElement('li');
        li.textContent = user;
        userList.appendChild(li);
    });
});
