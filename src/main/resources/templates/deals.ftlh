<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Список сделок</h4>
<form action="/" method="get">
    Поиск по наимаенованию: <input type="text" name="name"><br>
    <input type="submit" value="Поиск">
</form>
<#list deals as deal>
    <div>
        <p>${deal.id} \\
            ${deal.name} \\
            ${deal.whatNeed} \\
            <#--${deal.deliveryAdress} \\-->
            ${deal.author} |
            <a href="/deal-info/${deal.id}">Подробнее</a>
        </p>
    </div>
    <#else>
    <h3>Пока нет сделок</h3>
</#list>
<hr>
<a href="/deal-create/">создать новую сделку</a>
</body>
</html>