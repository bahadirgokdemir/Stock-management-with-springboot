document.addEventListener('DOMContentLoaded', function() {
    fetchOrders();
});
document.getElementById('productManagementButton').addEventListener('click', function() {
    window.location.href = '/products.html';
});
document.getElementById('stockStatusButton').addEventListener('click', function() {
    window.location.href = '/stock-status.html';
});
document.getElementById('OrderButton').addEventListener('click', function() {
    window.location.href = '/orders.html';
});

function fetchOrders() {
    fetch('/api/orders')
        .then(response => response.json())
        .then(data => {
            displayOrders(data);
        })
        .catch(error => console.error('Error:', error));
}


function displayOrders(data) {
    const table = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
    table.innerHTML = '';
    data.forEach(order => {
        let row = table.insertRow();
        row.insertCell().textContent = order.productName;
        row.insertCell().textContent = order.quantity;
        row.insertCell().textContent = order.productPrice;
        row.insertCell().textContent = order.quantity * order.productPrice;
        row.insertCell().textContent = order.deliveryCompany;
        row.insertCell().textContent = order.deliveryDate;
        let deliveryStatusCell = row.insertCell();
        if (order.status) {
            let deliveryButton = document.createElement('button');
            deliveryButton.textContent = 'Teslim Edildi Olarak İşaretle';
            if (typeof order.id !== 'undefined') {
                deliveryButton.onclick = function() { updateOrderStatus(order.id); };
            } else {
                console.error('Sipariş ID tanımlı değil', order);
            }
            deliveryStatusCell.appendChild(deliveryButton);
        }
    });
}




function loadProducts() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/products/", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var products = JSON.parse(xhr.responseText);
            var productsDropdown = document.getElementById('selectedProduct');
            productsDropdown.innerHTML = ''; // Mevcut seçenekleri temizle
            products.forEach(function(product) {
                var option = document.createElement('option');
                option.value = product.id;
                option.textContent = product.name + ' - Fiyat: ' + product.price;
                productsDropdown.appendChild(option);
            });
        }
    };
    xhr.send();
}


function updateOrderStatus(orderId) {
    fetch(`/api/orders/update/${orderId}`, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Sipariş durumu güncellenirken bir hata oluştu');
            }
            fetchOrders();
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
}

function closeEditModal() {
    document.getElementById('orderHistoryModal').style.display = 'none';
}


var modal = document.getElementById('orderModal');
var btn = document.getElementById('createOrderButton');
var span = document.getElementsByClassName('close')[0];

btn.onclick = function() {
    modal.style.display = 'block';
    loadProducts();
}

span.onclick = function() {
    modal.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}
document.getElementById('orderForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var selectedProductId = document.getElementById('selectedProduct').value;
    var deliveryCompany = document.getElementById('deliveryCompany').value;
    var deliveryDate = document.getElementById('deliveryDate').value;
    var quantity = parseInt(document.getElementById('quantity').value, 10);
    var pricePerUnit = parseFloat(document.getElementById('pricePerUnit').value);

    var orderData = {
        productId: selectedProductId,
        quantity: quantity,
        deliveryCompany: deliveryCompany,
        deliveryDate: deliveryDate,
        pricePerUnit: pricePerUnit
    };

    fetch('/api/orders/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Stok miktarı yetersiz');
            }
            return response.json();
        })
        .then(data => {
            console.log("Response Data:", data);
            modal.style.display = 'none';
            fetchOrders();
        })
        .catch((error) => {
            console.error('Error:', error);
            alert(error.message);
        });
});

document.getElementById('OrderHistoryButton').addEventListener('click', function() {
    fetchDeliveredOrders();
    document.getElementById('orderHistoryModal').style.display = 'block';
});

function closeOrderHistoryModal() {
    document.getElementById('orderHistoryModal').style.display = 'none';
}

function fetchDeliveredOrders() {
    fetch('/api/orders/delivered')
        .then(response => response.json())
        .then(data => {
            displayDeliveredOrders(data);
        })
        .catch(error => console.error('Error:', error));
}

function displayDeliveredOrders(data) {
    const table = document.getElementById('deliveredOrdersTable').getElementsByTagName('tbody')[0];
    table.innerHTML = '';
    data.forEach(order => {
        let row = table.insertRow();
        row.insertCell().textContent = order.productName;
        row.insertCell().textContent = order.quantity;
        row.insertCell().textContent = order.deliveryCompany;
        row.insertCell().textContent = order.deliveryDate;
    });
}





