const express = require("express");
const path = require("path");

const mysql = require("mysql2");
const cors = require("cors");



const app = express();
app.use(cors());
app.use(express.json());

// Database Connection
const db = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "Mysql26", // Add your MySQL password here
    database: "fruitshop"
});

db.connect(err => {
    if (err) {
        console.error("Database connection failed:", err);
        return;
    }
    console.log("Connected to MySQL database");
});

// Get all vendors
app.get("/vendors", (req, res) => {
    db.query("SELECT * FROM vendors", (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).send("Error retrieving vendors");
            return;
        }
        res.json(results);
    });
});

// Get products for a specific vendor
app.get("/products/:vendorId", (req, res) => {
    const vendorId = req.params.vendorId;
    db.query("SELECT * FROM products WHERE vendor_id = ?", [vendorId], (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).send("Error retrieving products");
            return;
        }
        res.json(results);
    });
});

// Add item to cart
app.post("/cart", (req, res) => {
    const { product_id, quantity } = req.body;
    db.query("INSERT INTO cart (product_id, quantity) VALUES (?, ?)", [product_id, quantity], (err, result) => {
        if (err) {
            console.error(err);
            res.status(500).send("Error adding to cart");
            return;
        }
        res.send("Item added to cart successfully");
    });
});

// Get all cart items
app.get("/cart", (req, res) => {
    db.query(
        "SELECT cart.id, products.name, products.price, cart.quantity FROM cart JOIN products ON cart.product_id = products.id",
        (err, results) => {
            if (err) {
                console.error(err);
                res.status(500).send("Error retrieving cart items");
                return;
            }
            res.json(results);
        }
    );
});
app.use(express.static(path.join(__dirname, "public")));

// Start the server
app.listen(5000, () => {
    console.log("Server is running on port 5000");
});
