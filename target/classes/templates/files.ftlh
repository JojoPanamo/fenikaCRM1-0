<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Файлы сделки</title>
    <style>
        /* Основные стили */
        body {
            display: flex;
            flex-direction: row;
            margin: 0;
            font-family: 'Manrope', sans-serif;
            background-color: #F3F3F4;
            color: #20252B;
        }

        input[type="text"], input[type="file"] {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
        }

        input[type="file"] {
            width: auto;
        }

        /* Sidebar */
        .sidebar {
            width: 70px;
            height: 100vh;
            background-color: #20252B;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding-top: 20px;
            position: fixed;
            top: 0;
            left: 0;
        }

        .sidebar a {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 20px;
            text-decoration: none;
            color: #C8C8C8;
            font-size: 8px;
        }

        .sidebar img {
            width: 40px;
            height: 25px;
            margin-bottom: 5px;
            filter: brightness(0) invert(1);
        }

        /* Контент */
        .content {
            margin-left: 90px;
            padding-top: 40px;
            width: calc(100% - 90px);
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }

        .files-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
            width: 100%;
            max-width: 900px;
            background: #FFFFFF;
            padding: 40px;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 200px;
        }

        .files-header {
            font-size: 26px;
            font-weight: 600;
            color: #20252B;
            margin-bottom: 20px;
        }

        .header-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .file-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .file-name {
            color: #1A73E8;
            text-decoration: none;
            font-weight: bold;
            flex-shrink: 0;
            width: 200px;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }

        .file-name:hover {
            text-decoration: underline;
        }

        .file-comment-field {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 520px;
        }

        .file-date {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-size: 12px;
            font-weight: 600;
            color: #20252B;
            text-align: center;
            line-height: 1.4;
            white-space: nowrap;
            width: 61px;
        }

        .delete-button {
            background: none;
            border: none;
            color: #FFADAD;
            font-size: 14px;
            cursor: pointer;
            font-weight: bold;
            margin-left: 0;
            padding-left: 0;
        }

        .delete-button:hover {
            color: #FF5252;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
            width: 100%;
        }

        .file-upload-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .file-upload-row .file-date {
            font-size: 14px;
            font-weight: 600;
            color: #20252B;
        }

        .form-container button {
            padding: 8px 12px;
            font-size: 14px;
            background: #82C562;
            color: #FFFFFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            align-self: flex-start; /* Перемещение кнопки в левый угол */
        }

        .form-container button:hover {
            background: #A3E48C;
        }
    </style>
</head>
<body>
<div class="sidebar">
    <a href="/statistics"><img src="/icons/home.svg" alt="Статистика"><p>Статистика</p></a>
    <a href="/deals"><img src="/icons/Vector.svg" alt="Сделки"><p>Сделки</p></a>
    <#if isAdmin>
        <a href="/registration"><img src="/icons/reg.svg" alt="Добавить"><p>Добавить<br>менеджера</p></a>
        <a href="/user-settings"><img src="/icons/sett.svg" alt="Настройки"><p>Настройки<br>процентов</p></a>
    </#if>
    <a href="/logout"><img src="/icons/exit.svg" alt="Выход"><p>Выход</p></a>
</div>
<div class="content">
    <div class="files-container">
        <div class="files-header">
            <div class="header-container">
                <form action="/files/${dealId}/back" method="get">
                    <button class="delete-button" type="submit">
                        <img src="/icons/back.svg" alt="Назад" style="width: 18px; height: 32px;">
                    </button>
                </form>
                <span style="margin-left: 30px; padding-bottom: 10px;">Файлы сделки №${dealId}</span>
            </div>
        </div>
        <#list allFiles as file>
            <div class="file-row">
                <a class="file-name" href="/files/download/${file.fileId}">${file.fileName}</a>
                <div class="file-date">
                    <div>${file.currentDate?substring(0, 10)}</div>
                    <div>${file.currentDate?substring(11)}</div>
                </div>
                <div class="file-comment-field">${file.fileComment}</div>
                <form action="/files/delete/${file.fileId}" method="post">
                    <button class="delete-button" type="submit">Х</button>
                </form>
            </div>
        </#list>
        <form action="/files/upload/${dealId}" method="post" enctype="multipart/form-data" class="form-container">
            <div class="file-upload-row">
                <input type="file" name="file" required />
                <div class="file-date">Сегодня</div>
                <input type="text" name="fileComment" placeholder="Комментарий к файлу" />
            </div>
            <button type="submit">Загрузить</button>
        </form>
    </div>
</div>
</body>
</html>
