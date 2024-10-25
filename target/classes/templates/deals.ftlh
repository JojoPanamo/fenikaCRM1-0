<!DOCTYPE html>
<html>
<head>
    <title>CRM Fenika - Список сделок</title>
</head>
<body>
<h1>CRM Феникс</h1>
<h4>Список сделок</h4>

<!-- Форма для выбора статусов -->
<form action="/deals" method="get">
    <label for="statusFilter">Фильтровать по статусу:</label>
    <select name="statusFilter" id="statusFilter">
        <option value="Новая заявка,В работе,Оплачен" <#if selectedStatuses?seq_contains("Новая заявка") && selectedStatuses?seq_contains("В работе") && selectedStatuses?seq_contains("Оплачен")>selected</#if>>Новые, В работе, Оплаченные</option>
        <option value="В работе" <#if selectedStatuses?seq_contains("В работе") && selectedStatuses?size == 1>selected</#if>>В работе</option>
        <option value="Оплачен" <#if selectedStatuses?seq_contains("Оплачен") && selectedStatuses?size == 1>selected</#if>>Оплачен</option>
        <option value="Завершен" <#if selectedStatuses?seq_contains("Завершен") && selectedStatuses?size == 1>selected</#if>>Завершен</option>
        <option value="Новая заявка" <#if selectedStatuses?seq_contains("Новая заявка") && selectedStatuses?size == 1>selected</#if>>Новая заявка</option>
        <option value="Отказ" <#if selectedStatuses?seq_contains("Отказ") && selectedStatuses?size == 1>selected</#if>>Отказ</option>

    </select>
    <input type="submit" value="Применить фильтр">
</form>

<!-- Список сделок -->
<#if deals?? && deals?size != 0>
    <#list deals as deal>
        <div>
            <p>
                ID сделки: ${deal.dealId} <br>
                Наименование: ${deal.name} <br>
                Что требуется: ${deal.whatNeed} <br>
                Автор: ${deal.user.name} <br>
                Статус: <#if deal.lastStatus??>
                    ${deal.lastStatus}
                <#else>
                    Статус не установлен
                </#if> <br>
<#--                Прибыль компании: ${deal.companyProfit} <br>-->
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
<form action="/logout" method="post">
    <input type="submit" value="Выйти"/>
</form>
<form action="/statistics" method="get">
    <input type="submit" value="Статистика"/>
</form>
</body>
</html>
