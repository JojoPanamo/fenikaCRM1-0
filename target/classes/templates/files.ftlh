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

        input[type="text"] {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            margin-left: 20px;
            width: 500px;
            height: 23px;

        }

        /* Скрываем стандартное поле выбора файла */
        input[type="file"] {
            display: none;
        }

        /* Стилизованный контейнер, если файл не выбран */
        .file-container {
            box-sizing: border-box;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            padding: 8px 12px;
            gap: 10px;
            margin-left: 0;
            margin-right: 20px;
            width: 225px;
            height: 41px;
            border: 1px dashed #B5B5B5;
            border-radius: 5px;
            cursor: pointer;
        }

        /* Текст внутри контейнера */
        .file-container span {
            width: 102px;
            height: 21px;
            font-family: 'Manrope', sans-serif;
            font-style: normal;
            font-weight: 600;
            font-size: 14px;
            line-height: 150%;
            display: flex;
            align-items: center;
            text-align: center;
            text-decoration-line: underline;
            color: #B5B5B5;
        }

        /* Стиль для выбранного имени файла */
        .file-name {
            color: #20252B;
            font-size: 14px;
            font-weight: bold;
            flex-shrink: 0;
            width: 225px;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
            margin-right: 20px;
            text-decoration: underline;
        }

        .file-name:hover {
            text-decoration: underline;
        }

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

        /*.files-header {*/
        /*    font-size: 26px;*/
        /*    font-weight: 600;*/
        /*    color: #20252B;*/
        /*    margin-bottom: 20px;*/
        /*}*/

        .file-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .file-comment-field {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 500px;
            margin-left: 20px;
            margin-right: 10px;
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

        .save-button {
            padding: 8px 12px;
            font-size: 14px;
            background: #FEB155;
            color: #20252B;
            border-radius: 5px;
            cursor: pointer;
            align-self: flex-start;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
            border: 1px solid #D1D1D3;
            width: 107px;
            margin-top: 30px;
        }

        /*.save-button:hover {*/
        /*    background: #A3E48C;*/
        /*}*/
        input[type="text"]::placeholder,
        input[type="number"]::placeholder {
            color: #B5B5B5; /* Цвет плейсхолдера */
        }
        .files-header {
            font-size: 26px;
            font-weight: 600;
            color: #20252B;
            margin-bottom: 20px;
        }

        .header-container {
            display: flex; /* Включаем Flexbox */
            align-items: center; /* Центрируем по вертикали */
            gap: 10px; /* Расстояние между кнопкой и текстом */
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
            <button class="delete-button" type="submit" style="background: none; border: none; padding: 0; cursor: pointer; margin-left: 0;">
                <img src="/icons/back.svg" alt="Назад" style="width: 18px; height: 32px;">
            </button>
        </form>
        <span style="
                margin-left: 30px;
                padding-bottom: 10px;
                ">Файлы сделки №${dealId}
        </span>
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
        <br>
        <form action="/files/upload/${dealId}" method="post" enctype="multipart/form-data" class="form-container">
            <div class="file-row">
                <label class="file-container" id="file-label">
                    <span>Выбрать файл</span>
                    <input type="file" id="file-upload" name="file" required>
                </label>
                <span class="file-name" id="selected-file-name" style="display: none;"></span>
                <div class="file-date">Сегодня</div>
                <input type="text" name="fileComment" placeholder="Комментарий к файлу" required>
            </div>
            <button type="submit" class="save-button">Сохранить</button>
        </form>
    </div>
</div>
<script>
    document.getElementById('file-upload').addEventListener('change', function () {
        const fileInput = this;
        const fileLabel = document.getElementById('file-label');
        const fileNameElement = document.getElementById('selected-file-name');

        if (fileInput.files.length > 0) {
            const fileName = fileInput.files[0].name;

            fileNameElement.textContent = fileName;
            fileNameElement.style.display = 'inline-block';
            fileLabel.style.display = 'none';
        } else {
            fileLabel.style.display = 'flex';
            fileNameElement.style.display = 'none';
        }
    });
</script>
</body>
</html>
