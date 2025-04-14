const apiBaseUrl = "http://localhost:5000";

// Load Vendors
if (document.getElementById("vendors-list")) {
    fetch(`${apiBaseUrl}/vendors`)
        .then(response => response.json())
        .then(data => {
            let vendorList = "";
            data.forEach(vendor => {
                vendorList += `<button onclick="selectVendor(${vendor.id})">${vendor.name}</button><br>`;
            });
            document.getElementById("vendors-list").innerHTML = vendorList;
        });
}

// Load Products for Selected Vendor
if (document.getElementById("products-list")) {
    const urlParams = new URLSearchParams(window.location.search);
    const vendorId = urlParams.get("vendor");
    fetch(`${apiBaseUrl}/products/${vendorId}`)
        .then(response => response.json())
        .then(data => {
            let productList = "";
            data.forEach(product => {
                productList += `
                    <p>${product.name} - ₹${product.price} (Qty: ${product.quantity})
                    <button onclick="addToCart(${product.id})">Add to Cart</button></p>`;
            });
            document.getElementById("products-list").innerHTML = productList;
        });
}

// Add Product to Cart
function addToCart(productId) {
    fetch(`${apiBaseUrl}/cart`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ product_id: productId, quantity: 1 })
    }).then(() => alert("Item added to cart!"));
}

// Load Cart Items
if (document.getElementById("cart-items")) {
    fetch(`${apiBaseUrl}/cart`)
        .then(response => response.json())
        .then(data => {
            let cartContent = "";
            data.forEach(item => {
                cartContent += `<p>${item.name} - ₹${item.price} x ${item.quantity}</p>`;
            });
            document.getElementById("cart-items").innerHTML = cartContent;
        });
}

// Navigate to Vendor's Products Page
function selectVendor(vendorId) {
    window.location.href = `products.html?vendor=${vendorId}`;
}
