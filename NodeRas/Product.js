import React, { useEffect, useState } from "react";
import axios from "axios";

const Product = () => {
    const [products, setProducts] = useState([]);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState("");
    const [editId, setEditId] = useState(null); // Track ID for editing
    const [errorMessage, setErrorMessage] = useState("");

    // ✅ Fetch Products from Backend
    const fetchProducts = async () => {
        try {
            const response = await axios.get("http://localhost:3000/nodepro");
            setProducts(response.data);
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    // ✅ Add or Update Product
    const handleSubmit = async () => {
        if (!name || !description || !price) {
            setErrorMessage("All fields are required!");
            return;
        }
        setErrorMessage("");

        try {
            if (editId) {
                // Update existing product
                await axios.put(`http://localhost:3000/nodepro/${editId}`, { name, description, price });
                alert("Product updated successfully!");
            } else {
                // Create new product
                await axios.post("http://localhost:3000/nodepro", { name, description, price });
                alert("Product added successfully!");
            }
            fetchProducts(); // Refresh products list
            resetForm(); // Reset form fields
        } catch (error) {
            console.error("Error adding/updating product:", error);
        }
    };

    // ✅ Set form for editing a product
    const editProduct = (product) => {
        setName(product.name);
        setDescription(product.description);
        setPrice(product.price);
        setEditId(product.id);
    };

    // ✅ Delete a product
    const deleteProduct = async (id) => {
        if (window.confirm("Are you sure you want to delete this product?")) {
            try {
                await axios.delete(`http://localhost:3000/nodepro/${id}`);
                alert("Product deleted successfully!");
                fetchProducts();
            } catch (error) {
                console.error("Error deleting product:", error);
            }
        }
    };

    // ✅ Reset form after adding or updating
    const resetForm = () => {
        setName("");
        setDescription("");
        setPrice("");
        setEditId(null);
    };

    return (
        <div style={{ padding: "20px", maxWidth: "600px", margin: "auto", textAlign: "center" }}>
            <h2>{editId ? "Update" : "Add"} Product</h2>
            {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
            <div>
                <input type="text" placeholder="Product Name" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div>
                <textarea placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} />
            </div>
            <div>
                <input type="number" placeholder="Price" value={price} onChange={(e) => setPrice(e.target.value)} />
            </div>
            <button onClick={handleSubmit}>{editId ? "Update Product" : "Add Product"}</button>

            <h2>Product List</h2>
            <table border="1" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((product) => (
                        <tr key={product.id}>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.description}</td>
                            <td>${parseFloat(product.price).toFixed(2)}</td>
                            <td>
                                <button onClick={() => editProduct(product)}>Edit</button>
                                <button onClick={() => deleteProduct(product.id)}>Delete</button> {/* Pass product ID here */}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Product;
