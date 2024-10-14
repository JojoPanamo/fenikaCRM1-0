<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRM Fenika - Список сделок</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Список сделок</h4>

<!-- Форма поиска по наименованию сделки -->
<form action="/" method="get">
    Поиск по наименованию: <input type="text" name="name" placeholder="Введите наименование сделки"><br>
    <input type="submit" value="Поиск">
</form>

<hr>

<!-- Проверка, есть ли сделки -->
<#if deals?? && (deals?size > 0)>
    <#list deals as deal>
        <div>
            <p>
                ID сделки: ${deal.dealId} <br>
                Наименование: ${deal.name} <br>
                Что требуется: ${deal.whatNeed} <br>
                Автор: ${deal.author} <br>
                <a href="/deal-info/${deal.dealId}">Подробнее</a>
            </p>
        </div>
        <hr>
    </#list>
<#else>
    <h3>Пока нет сделок</h3>
</#if>

<!-- Кнопка для создания новой сделки -->
<form action="/deal-create/" method="get">
    <input type="submit" value="Создать новую сделку"/>
</form>
</body>
</html>

