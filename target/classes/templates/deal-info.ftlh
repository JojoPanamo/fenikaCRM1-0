<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Информация о сделке №${deal.id}</h4>
<#--<b> ID клиента: </b>${deal.id}<br>-->
<b> Наименование клиента: </b>${deal.name}<br>
<b> Номер теелфона клиента: </b>${deal.numberPhone}<br>
<b> Что запросил клиент: </b>${deal.whatNeed}<br>
<b> Адрес доставки: </b>${deal.deliveryAdress}<br>
<b> Ответственный: </b>${deal.author}<br>
<b> <a href="/comments/${deal.id}">Комментарии</a><br>
    <b> <a href="/files/${deal.id}">Вложения</a><br>
        <b> <a href="/payments/${deal.id}">Оплаты</a><br>
            <b> <a href="/statuses/${deal.id}">Статус</a><br>
<#--<hr>-->
<#--<h3>Удалить сделку</h3>-->
<#--<form action="deal/delete/${deal.id}" method="post">-->
<#--    <input type="submit" value="Удалить"/>-->
<#--</form>-->
</body>
</html>