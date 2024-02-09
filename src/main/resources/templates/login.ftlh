<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Аторизуйтесь</h4>
<form action="/login" method="get">
    Почта: <input type="email" name="username"><br>
    Пароль: <input type="password" name="password"><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход">
</form>
<hr>
    <a href="/registration">Зарегистрироваться</a>
</body>
</html>