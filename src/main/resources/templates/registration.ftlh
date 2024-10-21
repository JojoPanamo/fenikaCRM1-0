<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Регистрация пользователя</h4>
<form action="/registration" method="post">
    Имя: <input type="text" name="name"><br>
    Телефон: <input type="text" name="phoneNumber"><br>
    Почта: <input type="email" name="email" required><br>
    Пароль: <input type="password" name="password"><br>
<#--    <input type="hidden" name="_csrf" value="${_csrf.token}">-->
    <input type="submit" value="Подтвердить">
</form>
</body>
</html>