<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="zad1.Model.Book" %>
<%@ page import="zad1.Controller.BookService" %>
<html>
<head>
    <title>Dostępne pozycje</title>
</head>
<body>
<%
    BookService bookService = new BookService();

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = response.getWriter();
    writer.write("<button type=\"button\" onclick=\"location.href='index.jsp'\">Powrót do menu</button>");
    writer.write("<h1>Dostępne pozycje:<h1>");
    for (Book book : bookService.getAllBooks()) {
        writer.write("<p>"+book.toString()+"<p><hr>");
    }
%>
</body>
</html>