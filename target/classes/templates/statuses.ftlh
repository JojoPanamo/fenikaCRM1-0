<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM FENIKS</h1><hr>
<h3>Статус сделки № ${dealId.id}</h3>
<#list allStatuses as allStatus>
    <div>
        <p> ${allStatus.currentDate} ${allStatus.statusChoose} ${allStatus.statusComment}</p>
    </div>
</#list>
<form action="/saveStatus/${dealId.id}" method="post">
    Комментарий: <input type="text" name="statusComment"/><br><br>
    Выберете статус: <select name="statusChoose">
        <#list statusList as status>
            <option value="${status}">${status}</option>
        </#list>
    </select><br><br>

    <input type="submit" value="Сохранить"/>
</form>
</body>
</html>