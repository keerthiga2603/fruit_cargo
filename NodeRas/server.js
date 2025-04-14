const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql2');
const cors = require('cors');  // Import CORS

const app = express();
app.use(bodyParser.json());

// ✅ Enable CORS to allow frontend to access the backend
app.use(cors());  // This will allow all origins by default

// ✅ Connect to MySQL
const db = mysql.createConnection({
    host: 'localhost',
    port: 2345,  // Use your MySQL port
    user: 'Rasika',  // Your MySQL username
    password: 'Rasi',  // Your MySQL password
    database: 'RASIKA'  // Your existing database
});

// ✅ Handle database connection errors
db.connect(err => {
    if (err) {
        console.error('Database connection failed:', err);
        process.exit(1); // Exit the app if DB connection fails
    }
    console.log('✅ Connected to MySQL Database');
});

// ✅ CREATE - Add a new entry to nodepro
app.post('/nodepro', (req, res) => {
    const { name, description, price } = req.body;

    // Input validation
    if (!name || !description || !price) {
        return res.status(400).send({ message: 'All fields (name, description, price) are required!' });
    }

    const query = 'INSERT INTO nodepro (name, description, price) VALUES (?, ?, ?)';

    db.query(query, [name, description, price], (err, result) => {
        if (err) {
            console.error('Error inserting data:', err);
            return res.status(500).send({ message: 'Error inserting data', error: err });
        }
        res.status(201).send({ message: 'Nodepro added successfully!', id: result.insertId });
    });
});

// ✅ READ - Get all entries from nodepro
app.get('/nodepro', (req, res) => {
    const query = 'SELECT * FROM nodepro';

    db.query(query, (err, results) => {
        if (err) {
            console.error('Error fetching data:', err);
            return res.status(500).send({ message: 'Error fetching data', error: err });
        }
        res.status(200).send(results);
    });
});

// ✅ UPDATE - Modify an entry in nodepro
app.put('/nodepro/:id', (req, res) => {
    const { id } = req.params;
    const { name, description, price } = req.body;

    // Input validation
    if (!name || !description || !price) {
        return res.status(400).send({ message: 'All fields (name, description, price) are required!' });
    }

    const query = 'UPDATE nodepro SET name = ?, description = ?, price = ? WHERE id = ?';

    db.query(query, [name, description, price, id], (err, result) => {
        if (err) {
            console.error('Error updating data:', err);
            return res.status(500).send({ message: 'Error updating data', error: err });
        }

        if (result.affectedRows === 0) {
            return res.status(404).send({ message: `Nodepro with ID ${id} not found` });
        }

        res.status(200).send({ message: 'Nodepro updated successfully!' });
    });
});

// ✅ DELETE - Remove an entry from nodepro
app.delete('/nodepro/:id', (req, res) => {
    const { id } = req.params;

    const query = 'DELETE FROM nodepro WHERE id = ?';

    db.query(query, [id], (err, result) => {
        if (err) {
            console.error('Error deleting data:', err);
            return res.status(500).send({ message: 'Error deleting data', error: err });
        }

        if (result.affectedRows === 0) {
            return res.status(404).send({ message: `Nodepro with ID ${id} not found` });
        }

        res.status(200).send({ message: 'Nodepro deleted successfully!' });
    });
});

// Start the server
app.listen(3000, () => {
    console.log('✅ Server is running on port 3000');
});
