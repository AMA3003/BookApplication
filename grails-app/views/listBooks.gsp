<!DOCTYPE html>
<html>
<head>
    <title>List of Books</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
        margin: 0;
        padding: 0;
    }

    h1 {
        text-align: center;
        margin-top: 20px;
    }

    table {
        width: 80%;
        margin: 0 auto;
        border-collapse: collapse;
        background-color: #007bff;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
    }

    th, td {
        padding: 10px;
        text-align: center;
    }

    th {
        background-color: #007bff;
        color: #fff;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tr:nth-child(odd) {
        background-color: #fff;
    }

    .delete-button {
        background-color: #ff0000;
        color: #ffffff;
        border: none;
        padding: 5px 10px;
        border-radius: 3px;
        font-size: 14px;
        cursor: pointer;
    }

    .delete-button:hover {
        background-color: #cc0000;
    }

    .update-button {
        background-color: #007bff;
        color: #ffffff;
        border: none;
        padding: 5px 10px;
        border-radius: 3px;
        font-size: 14px;
        cursor: pointer;
    }

    .update-button:hover {
        background-color: #007bff;
    }

    </style>
</head>
<body>
<h1>List of Books</h1>
<table>
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Year Published</th>
        <th>Genre</th>
        <th>CreatedDate</th>
        <th>lastModifiedDate</th>
    </tr>
    <g:each in="${bookList}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.yearPublished}</td>
            <td>${book.genre}</td>
            <td>${book.CreatedDate}</td>
            <td>${book.lastModifiedDate}</td>
            <td>
                <form action="${createLink(controller: 'bookViews', action: 'deleteBook', params: [title: book.title])}" method="post">
                    <input type="submit" class="delete-button" value="Delete" onclick="return confirm('Are you sure you want to delete this book?')">
                </form>
            </td>
            <td>
                <form action="${createLink(controller: 'bookViews', action: 'updateBook', params: [book:book])}" method="put">
                    <input type="submit" class="update-button" value="Update" onclick="return confirm('Are you sure you want to Update this book?')">
                </form>
            </td>
        </tr>
    </g:each>
</table>
</body>
</html>
