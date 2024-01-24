<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM FENIKS</h1><hr>
<h3>Оплаты сдлки № ${dealId.id}</h3>
<#list allPayments as allPayment>
    <div>
        <p> ${allPayment.currentDate} ${allPayment.statusPayments} ${allPayment.sum} ${allPayment.commentPayments}</p>
    </div>
</#list>
<p>Прибыль компании: ${earnedMoney}</p>
<p>Прибыль менеджера: ${moneyOfManager}</p>
<form action="/savePayment/${dealId.id}" method="post">
    Сумма: <input type="number" name="sum" pattern="\d+(\,\d{1,2})?"/><br><br>
    Комментарий: <input type="text" name="commentPayments"/><br><br>
    Выберете статус: <select name="statusPayments">
        <#list listOfStatPay as payListStat>
            <option value="${payListStat}">${payListStat}</option>
        </#list>
    </select><br><br>

    <input type="submit" value="Сохранить"/>
</form>

</body>
</html>