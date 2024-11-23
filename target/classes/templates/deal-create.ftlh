<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h3>Создание новой сделки</h3>
<form action="deal-create-save" method="post">
    Наименование клиента: <input type="text" name="name"/><br><br>
    Номер телефона клиента: <input type="text" name="numberPhone"/><br><br>
    Что запросил клиент: <input type="text" name="whatNeed"/><br><br>
    Адрес доставки: <input type="text" name="deliveryAdress"/><br><br>
    <label for="whereFrom">Откуда пришел клиент:</label>
    <select id="whereFrom" name="whereFrom">
        <#list whereFromOptions as option>
            <option value="${option}">${option}</option>
        </#list>
    </select>

    <input type="submit" value="Сохранить"/>
</form>
<hr>
<form action="/deals" method="get">
    <input type="submit" value="Назад"/>
</form>
<form action="/logout" method="post">
    <input type="submit" value="Выйти"/>
</form>
</body>
</html>
