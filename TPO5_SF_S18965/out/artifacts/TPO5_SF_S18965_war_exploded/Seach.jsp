<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wyszukaj książkę</title>
</head>
<body>
<button type="button" onclick="location.href='index.jsp'">Powrót do menu</button>
<h1>Proszę podać dane</h1>
<form action="/TPO5_SF_S18965_war_exploded/findBook" method="get">
    <select id="possibilities" name="possibilities" size="1">
        <option name="Autor" > Autor </option>
        <option name="Wydawca"> Wydawca </option>
        <option name="Tytul"> Tytul </option>
    </select>
    <input type="text" id="search"name="search">
    <input type="submit" value="szukaj">
</form>
</body>
</html>
