<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM FENIKS</h1><hr>
<h3>Статус сделки № ${dealId.dealId}</h3>
<#list allStatuses as allStatus>
    <div>
        <p> ${allStatus.dealId} ${allStatus.statusId} ${allStatus.currentDate} ${allStatus.statusChoose} ${allStatus.statusComment}</p>
    </div>
</#list>
<form action="/saveStatus/${dealId.dealId}" method="post">
    Комментарий: <input type="text" name="statusComment"/><br><br>
    Выберете статус: <select name="statusChoose">
        <#list statusList as status>
            <option value="${status}">${status}</option>
        </#list>
    </select><br><br>

    <input type="submit" value="Сохранить"/>
</form>
<hr>
<form action="/statuses/${dealId.dealId}/back" method="get">
    <input type="submit" value="Назад"/>
</form>
<br>
<form action="/statuses/${dealId.dealId}/todeals" method="get">
    <input type="submit" value="Назад к сделкам"/>
</form>
</body>
</html>