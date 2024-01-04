window.onload = function() {
    loadProducts();
    loadCategories();
};

function loadCategories() {
    fetch('/api/categories')
        .then(response => response.json())
        .then(categories => {
            const categorySelect = document.getElementById('productCategory');
            categories.forEach(category => {
                let option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                categorySelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error:', error));
}

document.getElementById('addEditProductForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var productId = document.getElementById('productId').value;
    var productName = document.getElementById('productName').value;
    var productPrice = document.getElementById('productPrice').value;
    var productStock = document.getElementById('productStock').value;

    var productData = {
        name: productName,
        price: parseFloat(productPrice),
        stockQuantity: parseInt(productStock)
    };

    if (productId) {
        updateProduct(productId, productData);
    } else {
        addProduct(productData);
    }
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


function addProduct(productData) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/products/", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            loadProducts();
        }
    };
    productData.categoryId = document.getElementById('productCategory').value;
    xhr.send(JSON.stringify(productData));
}


function updateProduct(productId, productData) {
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/api/products/" + productId, true);
    xhr.setRequestHeader("Content-Type", "application/json");


    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Ürün başarıyla güncellendi.");
            loadProducts();
        }
    };
    productData.categoryId = document.getElementById('productCategory').value;
    xhr.send(JSON.stringify(productData));
}


function deleteProduct(productId) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/products/" + productId, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Ürün başarıyla silindi.");
            loadProducts();
        }
    };
    xhr.send();
}

function loadProducts() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/products/", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var products = JSON.parse(xhr.responseText);
            console.log(products);
            var productsTable = document.getElementById('productsTable').getElementsByTagName('tbody')[0];
            productsTable.innerHTML = '';
            products.forEach(function(product) {
                var row = productsTable.insertRow();
                row.insertCell(0).innerHTML = product.id;
                row.insertCell(1).innerHTML = product.name;
                row.insertCell(2).innerHTML = product.price;
                row.insertCell(3).innerHTML = product.stockQuantity;
                row.insertCell(4).innerHTML = product.categoryName ? product.categoryName : "Kategori Yok";
                var editButton = '<button onclick="editProduct(' + product.id + ')">Düzenle</button>';
                var deleteButton = '<button onclick="deleteProduct(' + product.id + ')">Sil</button>';
                row.insertCell(5).innerHTML = editButton + ' ' + deleteButton;
            });
        }
    };
    xhr.send();
}


function openEditModal() {
    document.getElementById('editModal').style.display = 'block';
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}


function editProduct(productId) {
    loadEditCategories();
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/products/" + productId, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var product = JSON.parse(xhr.responseText);
            document.getElementById('editProductId').value = product.id;
            document.getElementById('editProductName').value = product.name;
            document.getElementById('editProductPrice').value = product.price;
            document.getElementById('editProductStock').value = product.stockQuantity;
            document.getElementById('editProductCategory').value = product.categoryId;
            openEditModal();
        }
    };
    xhr.send();
}

function loadEditCategories() {
    fetch('/api/categories')
        .then(response => response.json())
        .then(categories => {
            const categorySelect = document.getElementById('editProductCategory');
            categorySelect.innerHTML = ''; // Mevcut seçenekleri temizle
            categories.forEach(category => {
                let option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                categorySelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error:', error));
}


document.getElementById('editProductForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var productId = document.getElementById('editProductId').value;
    var productName = document.getElementById('editProductName').value;
    var productPrice = document.getElementById('editProductPrice').value;
    var productStock = document.getElementById('editProductStock').value;
    var productCategory = document.getElementById('editProductCategory').value; // Kategori ID'sini al

    var productData = {
        name: productName,
        price: parseFloat(productPrice),
        stockQuantity: parseInt(productStock),
        categoryId: parseInt(productCategory)
    };

    updateProduct(productId, productData);
    closeEditModal();
});



