<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Прибыль менеджера</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
        }
        input[type="text"] {
            width: 100px;
        }
        button {
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<h1>Прибыль менеджера за месяц</h1>
<table>
    <thead>
    <tr>
        <th>Неделя</th>
        <th>Заработок менеджера</th>
        <th>Выплаченная сумма</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <#list profits as profit>
        <tr>
            <td>${profit.week}</td>
            <td>${profit.managerProfit} руб.</td>
            <td>
                <form action="/weekly-manager-profit/update" method="post">
                    <input type="hidden" name="userId" value="${profit.user.userId}">
                    <input type="hidden" name="year" value="${profit.year}">
                    <input type="hidden" name="month" value="${profit.month}">
                    <input type="hidden" name="week" value="${profit.week}">
                    <input type="text" name="paidAmount" value="${profit.paidAmount}">
                    <button type="submit">Сохранить</button>
                </form>
            </td>
            <td>${profit.paidAmount} руб.</td>
        </tr>
    </#list>
    </tbody>
</table>

<#--<a href="/weekly-manager-profit/${currentYear}/${previousMonth}">Предыдущий месяц</a>-->
<#--<a href="/weekly-manager-profit/${currentYear}/${nextMonth}">Следующий месяц</a>-->
</body>
</html>
