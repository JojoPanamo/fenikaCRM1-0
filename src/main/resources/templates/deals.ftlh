<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Список сделок</h4>
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
</#list>
<hr>
<a href="/deal-create/">создать новую сделку</a>
</body>
</html>