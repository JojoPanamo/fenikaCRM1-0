<!DOCTYPE html>
<html>
<head>
    <title>CRM Fenika - Список сделок</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Список сделок</h4>

<form action="/" method="get">
    Поиск по наименованию: <input type="text" name="name"><br>
    <input type="submit" value="Поиск">
</form>

<#if deals?? && deals?size != 0 >
    <#list deals as deal>
        <div>
            <p>
                ID сделки: ${deal.dealId} <br>
                Наименование: ${deal.name} <br>
                Что требуется: ${deal.whatNeed} <br>
                Автор: ${deal.user.name} |
                <a href="/deal-info/${deal.dealId}">Подробнее</a>
            </p>
        </div>
    </#list>
<#else>
    <h3>Пока нет сделок</h3>
</#if>

<hr>
<form action="/deal-create" method="get">
    <input type="submit" value="Создать новую сделку"/>
</form>
</body>
</html>


