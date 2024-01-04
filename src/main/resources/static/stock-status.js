function createStockChart(data) {
    const ctx = document.createElement('canvas');
    document.getElementById('stock-status-container').appendChild(ctx);

    const labels = data.map(item => item.categoryName);
    const stockData = data.map(item => item.totalStock);

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Toplam Stok',
                data: stockData,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}
function createPieChart(data) {
    const pieCtx = document.createElement('canvas');
    document.getElementById('stock-status-container').appendChild(pieCtx);

    const labels = data.map(item => item.categoryName);
    const stockData = data.map(item => item.totalStock);

    new Chart(pieCtx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Kategoriye Göre Stok',
                data: stockData,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        }
    });
}


document.addEventListener('DOMContentLoaded', function() {
    fetchStockStatus();

    function fetchStockStatus() {
        fetch('/api/stock/status')
            .then(response => response.json())
            .then(data => {
                createStockChart(data);
                createPieChart(data);
                const tableBody = document.getElementById('stock-status-table').getElementsByTagName('tbody')[0];
                data.forEach(stock => {
                    let row = tableBody.insertRow();
                    row.innerHTML = `
                         <td>${stock.categoryId}</td>
                         <td>${stock.categoryName}</td>
                         <td>${stock.totalStock}</td>
                         <td>${stock.stockFillRate.toFixed(2)}%</td>
                         <td><button onclick="deleteCategory(${stock.categoryId})">Sil</button></td>
                       `;
                });

            })
            .catch(error => console.error('Error:', error));
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

document.getElementById('newCategoryForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var categoryName = document.getElementById('newCategoryName').value;
    var categoryDescription = document.getElementById('newCategoryDescription').value;

    addCategory({ name: categoryName, description: categoryDescription });
});

function addCategory(categoryData) {
    fetch('/api/categories', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(categoryData)
    })
        .then(response => response.json())
        .then(data => {
            location.reload();
        })
        .catch(error => console.error('Error:', error));
}
function deleteCategory(categoryId) {
    if (!confirm('Bu kategoriyi silmek istediğinizden emin misiniz?')) {
        return;
    }
    fetch(`/api/categories/${categoryId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Kategori silinirken bir hata oluştu.');
        });
}



