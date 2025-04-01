<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = htmlspecialchars($_POST["name"]);
    $amount = htmlspecialchars($_POST["amount"]);
    $donationType = htmlspecialchars($_POST["donationType"]);
    $message = htmlspecialchars($_POST["message"]);

    // Save to a text file (for testing, later replace with a database)
    $file = "donors.txt";
    $entry = "$name donated $$amount ($donationType) - $message\n";
    file_put_contents($file, $entry, FILE_APPEND | LOCK_EX);

    echo "Success";
} else {
    echo "Invalid Request";
}
?>
