<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Информация о сделке №${deal.dealId}</h4>
<#--<b> ID клиента: </b>${deal.id}<br>-->
<b> Наименование клиента: </b>${deal.name}<br>
<b> Номер теелфона клиента: </b>${deal.numberPhone}<br>
<b> Что запросил клиент: </b>${deal.whatNeed}<br>
<b> Адрес доставки: </b>${deal.deliveryAdress}<br>
<b> Ответственный: </b>${deal.author}<br>
<b> <a href="/comments/${deal.dealId}">Комментарии</a><br>
    <b> <a href="/files/${deal.dealId}">Вложения</a><br>
        <b> <a href="/payments/${deal.dealId}">Оплаты</a><br>
            <b> <a href="/statuses/${deal.dealId}">Статус</a><br>
<#--<hr>-->
<#--<h3>Удалить сделку</h3>-->
<#--<form action="deal/delete/${deal.id}" method="post">-->
<#--    <input type="submit" value="Удалить"/>-->
<#--</form>-->
</body>
</html>