<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM FENIKS</h1><hr>
<h3>Комментарии к сделке № ${dealId.dealId}</h3>
<#list comments as comment>
    <div>
        <p>${dealId.dealId} ${comment.commentId} ${comment.currentDate} ${comment.comment}</p>
    </div>
</#list>
<form action="/saveComment/${dealId.dealId}" method="post">
    Комментарий: <input type="text" name="comment"/><br><br>
    <input type="submit" value="Сохранить"/>
</form>
<hr>
<form action="/comments/${dealId.dealId}/back" method="get">
    <input type="submit" value="Назад"/>
</form>
<br>
<form action="/deals" method="get">
    <input type="submit" value="Назад к сделкам"/>
</form>
</body>
</html>