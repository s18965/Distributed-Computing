<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="zad1.Model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.Writer" %>
<%@ page import="java.io.IOException" %>
<%@ page import="zad1.Controller.BookService" %>

<html>
<head>
    <title>Dostępne pozycje</title>
</head>
<body>
<%!
    public void output(List<Book> books, Writer writer) throws IOException {
        if (books == null)
            writer.write("<p>Brak danej pozycji<p>");
        else
            for(Book book : books){
                writer.write("<p>Pozycja<p>");
                writer.write("<p>"+book.toString() + "<p><hr>");
            }
    }
%>

<%
        BookService bookService = new BookService();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("<button type=\"button\" onclick=\"location.href='Seach.jsp'\">Powrót</button>");
        String search = request.getParameter("search");
        String filtr = request.getParameter("possibilities");
        String string = filtr + ": " + request.getParameter("search");

        writer.write("<h1>Wyszukane pozycje dla \"" + string + "\": <h1>");

        if (filtr.equals("Tytul")) {
            output(bookService.findByTitle(search), writer);
        } else if (filtr.equals("Wydawca")) {
            output(bookService.findByPublishingHouse(search), writer);
        } else if (filtr.equals("Autor")) {
            output(bookService.findByAuthor(search), writer);
        }
%>
</body>
</html>