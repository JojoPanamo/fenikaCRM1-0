<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Комментарии к сделке</title>
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

        /* Блок комментариев */
        .comments-container {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            padding: 36px;
            gap: 15px;
            width: 900px;
            background: #FFFFFF;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }

        .comments-header {
            font-size: 26px;
            font-weight: 600;
            color: #20252B;
            display: flex;
            justify-content: space-between;
            width: 100%;
        }

        .comments-list {
            display: flex;
            flex-direction: column;
            gap: 10px;
            width: 100%;
        }

        .comments-list.hidden-comments .comment-row {
            display: none;
        }

        .comments-list.hidden-comments .comment-row:nth-last-child(-n+10) {
            display: flex;
        }

        .comment-row {
            display: flex;
            align-items: center;
            gap: 15px;
            width: 100%;
        }

        .comment-date {
            font-size: 12px;
            font-weight: 600;
            color: #20252B;
            white-space: nowrap;
        }
        input[type="text"]::placeholder,
        input[type="number"]::placeholder {
            color: #B5B5B5; /* Цвет плейсхолдера */
        }

        .comment-text {
            flex-grow: 1;
            padding: 8px 12px;
            background: #F6F6F6;
            border: 1px solid #D1D1D3;
            border-radius: 10px;
            font-size: 14px;
            font-weight: 500;
            color: #20252B;
            word-wrap: break-word;
            overflow-wrap: break-word;
            white-space: pre-wrap;
            overflow: hidden;
            text-align: left;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            width: 100%;
        }

        .form-container input[type="text"] {
            width: calc(100% - 33px);
            padding: 12px 16px;
            font-size: 14px;
            background: #F6F6F6;
            border: 1px solid #D1D1D3;
            border-radius: 10px;
            color: #20252B;
        }

        .form-buttons {
            display: flex;
            justify-content: space-between;
            width: 100%;
            margin-top: 20px;
        }

        .form-buttons button {
            padding: 10px 15px;
            font-size: 14px;
            font-weight: 500;
            background: #FEB155;
            border-radius: 5px;
            border: 1px solid #D1D1D3;
            cursor: pointer;
        }

        .form-buttons button:hover {
            background: #FFC580;
        }

        .show-all-button {
            background: none;
            border: none;
            color: #20252B;
            font-size: 14px;
            cursor: pointer;
            text-decoration: underline;
            padding: 0;
            align-self: flex-end;
        }
        .comments-header {
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
<!-- Sidebar -->
<div class="sidebar">
    <a href="/statistics">
        <img src="/icons/home.svg" alt="Статистика">
        <p>Статистика</p>
    </a>
    <a href="/deals">
        <img src="/icons/Vector.svg" alt="Сделки">
        <p>Сделки</p>
    </a>
    <#if isAdmin>
        <a href="/registration">
            <img src="/icons/reg.svg" alt="новый пользователь">
            <p>Добавить<br>менеджера</p>
        </a>
        <a href="/user-settings">
            <img src="/icons/sett.svg" alt="Настройки пользователя">
            <p>Настройки<br>процентов</p>
        </a>
    </#if>
    <a href="/logout">
        <img src="/icons/exit.svg" alt="Выход">
        <p>Выход</p>
    </a>
</div>

<!-- Content -->
<div class="content">
    <div class="comments-container">
        <div class="comments-header">
            <div class="header-container">
            <form action="/payments/${dealId.dealId}/back" method="get">
                <button class="delete-button" type="submit" style="background: none; border: none; padding: 0; cursor: pointer; margin-left: 0;">
                    <img src="/icons/back.svg" alt="Назад" style="width: 18px; height: 32px;">
                </button>
            </form>
            <span style="
                margin-left: 30px;
                padding-bottom: 10px;
                ">Комментарии к сделке #${dealId.dealId}
            </span>
            </div>
        </div>

        <div class="comments-list hidden-comments" id="commentsList">
            <#list comments?reverse as comment>
                <div class="comment-row">
                    <div class="comment-date">
                        <div>${comment.currentDate?substring(0, 10)}</div>
                        <div>${comment.currentDate?substring(11)}</div>
                    </div>
                    <div class="comment-text">${comment.comment}</div>
                </div>
            </#list>
        </div>

        <button class="show-all-button" id="toggleComments">v Развернуть</button>

        <form action="/saveComment/${dealId.dealId}" method="post" class="form-container">
            <input type="text" name="comment" placeholder="Напишите комментарий" required/>
            <div class="form-buttons">
                <button type="submit">Сохранить</button>
<#--                <a href="/comments/${dealId.dealId}/back" style="align-self: flex-start; text-decoration: none;">-->
<#--                    <button type="button" style="background: #F3F3F4; color: #20252B;">< Назад</button>-->
<#--                </a>-->

            </div>
        </form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', () => {
        const commentsList = document.getElementById('commentsList');
        const toggleButton = document.getElementById('toggleComments');

        toggleButton.addEventListener('click', () => {
            commentsList.classList.toggle('hidden-comments');
            toggleButton.textContent = commentsList.classList.contains('hidden-comments')
                ? 'v Развернуть'
                : '^ Свернуть';
        });
    });
</script>
</body>
</html>
