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
    Номер теелфона клиента: <input type="text" name="numberPhone"/><br><br>
    Что запросил клиент: <input type="text" name="whatNeed"/><br><br>
    Адрес доставки: <input type="text" name="deliveryAdress"/><br><br>
    Комментарий: <input type="text" name="comment"/><br><br>
    Ответственный: <select name="author">
        <#list authors as author>
            <option value="${author}">${author}</option>
        </#list>
    </select><br><br>
    <input type="submit" value="Сохранить"/>
</form>
</body>
</html>