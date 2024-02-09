<!DOCTYPE html>
<html>
<head>
    <title>Welcome to CRM Fenika</title>
</head>
<body>
<h1>CRM FENIKS</h1><hr>
<h3>Файлы сделки № ${dealId}</h3>
<#list allFiles as file>
    <div>
        <p>${file.fileName} ${file.fileId} ${file.dealId}</p>
        <a href="/files/download/${file.fileId}">Скачать</a>
        <form action="/files/delete/${file.fileId}" method="post">
            <input type="hidden" name="_method" value="delete">
            <button type="submit">Удалить</button>
        </form>
    </div>
</#list>
<form action="/files/upload/${dealId}" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/><br><br>
    <input type="submit" value="Загрузить"/>
</form>
<hr>
<form action="/files/${dealId}/back" method="get">
    <input type="submit" value="Назад"/>
</form>
<br>
<form action="/files/${dealId}/todeals" method="get">
    <input type="submit" value="Назад к сделкам"/>
</form>
</body>
</html>