<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        background-color: #9a00ff12;
        margin: 0;
        padding: 0;
    }

    h1 {
        text-align: center;
        margin-top: 20px;
    }

    form {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 5px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
    }

    label {
        display: block;
        font-weight: bold;
        margin-top: 10px;
    }

    input[type="text"] {
        width: 95%;
        padding: 10px;
        margin-top: 5px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 3px;
        font-size: 16px;
    }

    input[type="submit"] {
        background-color: #007bff;
        color: #fff;
        border: none;
        padding: 10px 20px;
        border-radius: 3px;
        font-size: 18px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background-color: #0056b3;
    }
    </style>
</head>
<body>
<h1>Add Book</h1>
<form action="${createLink(controller: 'bookViews', action: 'saveBook')}" method="post">
    <label for="title">Title:</label>
    <input type="text" name="title" required><br>

    <label for="author">Author:</label>
    <input type="text" name="author" required><br>

    <label for="yearPublished">Year Published:</label>
    <input type="text" name="yearPublished" required><br>

    <label for="genre">Genre:</label>
    <input type="text" name="genre" required><br>

    <input type="submit" value="Add Book">
</form>
</body>
</html>
