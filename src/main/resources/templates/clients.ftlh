<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Список клиентов</h4>
<#list clients as clientInfo>
    <div>
        <p>${clientInfo.id} \\
            ${clientInfo.name} \\
            ${clientInfo.whatNeed} \\
            ${clientInfo.deliveryAdress} \\
            ${clientInfo.author} |
            <a href="/clientInfo/${clientInfo.id}"> подробнее</a>
        </p>
    </div>
</#list>
</body>
</html>