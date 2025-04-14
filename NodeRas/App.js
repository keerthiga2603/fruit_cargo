import React from "react";
import Product from "./components/Product";
import './App.css';  // Importing CSS file in React


function App() {
    return (
        <div>
            <h1 style={{ textAlign: "center" }}>FRUIT CARGO</h1>
            <Product />
        </div>
    );
}

export default App;
