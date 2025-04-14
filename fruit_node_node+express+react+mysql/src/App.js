import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [vendors, setVendors] = useState([]);
  const [selectedVendorId, setSelectedVendorId] = useState(null);
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);

  // 🟢 Load all vendors
  useEffect(() => {
    axios.get('http://localhost:5000/vendors')
      .then(res => setVendors(res.data))
      .catch(err => console.error('Error loading vendors', err));
  }, []);

  // 🟢 Load products when a vendor is selected
  const handleVendorClick = (vendorId) => {
    setSelectedVendorId(vendorId);
    axios.get(`http://localhost:5000/products/${vendorId}`)
      .then(res => setProducts(res.data))
      .catch(err => console.error('Error loading products', err));
  };

  // 🟢 Add product to cart
  const handleAddToCart = (product) => {
    const quantity = 1;
    const cartItem = {
      product_id: product.id,
      product_name: product.name,
      vendor_id: product.vendor_id,
      quantity: quantity,
      price: product.price
    };

    axios.post('http://localhost:5000/cart/add', cartItem)
      .then(() => {
        alert(`${product.name} added to cart!`);
        loadCart(); // refresh cart
      })
      .catch(err => console.error('Error adding to cart', err));
  };

  // 🟢 Load cart items
  const loadCart = () => {
    axios.get('http://localhost:5000/cart')
      .then(res => setCart(res.data))
      .catch(err => console.error('Error loading cart', err));
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>🍎 Fruit Shop</h1>

      {/* Vendor Selection */}
      <h2>🧺 Select a Shop Vendor</h2>
      {vendors.map(vendor => (
        <button
          key={vendor.id}
          onClick={() => handleVendorClick(vendor.id)}
          style={{ margin: '10px', padding: '10px', background: '#bde0fe', borderRadius: '8px' }}
        >
          {vendor.store_name}
        </button>
      ))}

      {/* Product Display */}
      {selectedVendorId && (
        <>
          <h2>🍍 Products from Vendor #{selectedVendorId}</h2>
          <ul>
            {products.map(product => (
              <li key={product.id} style={{ marginBottom: '10px' }}>
                <strong>{product.name}</strong> – ₹{product.price} (Qty: {product.quantity})
                <button
                  onClick={() => handleAddToCart(product)}
                  style={{ marginLeft: '10px', padding: '5px 10px' }}
                >
                  Add to Cart
                </button>
              </li>
            ))}
          </ul>
        </>
      )}

      {/* Cart Display */}
      <h2>🛒 Cart</h2>
      <ul>
        {cart.map(item => (
          <li key={item.id}>
            {item.product_name} – {item.quantity} × ₹{item.price} = ₹{item.total_price}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
