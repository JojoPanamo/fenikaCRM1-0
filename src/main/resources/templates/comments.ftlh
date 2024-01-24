<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM FENIKS</h1><hr>
<h3>Комментарии к сделке № ${dealId.id}</h3>
<#list comments as comment>
    <div>
        <p><#--${dealId.id} ${comment.commentId}--> ${comment.currentDate} ${comment.comment}</p>
    </div>
</#list>
<form action="/saveComment/${dealId.id}" method="post">
    Комментарий: <input type="text" name="comment"/><br><br>
    <input type="submit" value="Сохранить"/>
</form>
</body>
</html>