<!DOCTYPE html>
<html>
<head>
    <title>Статистика пользователя</title>
</head>
<body>
<h1>Статистика пользователя</h1>
<p>Количество завершенных сделок: ${completedDealsCount}</p>
<p>Количество сделок в работе и оплаченных: ${inProgressOrPaidDealsCount}</p>
<p>Количество отказанных сделок: ${refusedDealsCount}</p>
<p>Общая сумма поступлений: ${totalPayments}</p>
<p>Прибыль компании: ${companyProfit}</p>
<p>Прибыль менеджера: ${managerProfit}</p>

<form action="/logout" method="post">
    <input type="submit" value="Выйти"/>
</form>
<form action="/deals" method="get">
    <input type="submit" value="сделки"/>
</form>
</body>
</html>
