<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Статусы сделки</title>
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

        .statuses-container {
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

        .statuses-header {
            font-size: 26px;
            font-weight: 600;
            color: #20252B;
            margin-bottom: 20px;
        }

        .status-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .status-field {
            padding: 8px 12px;
            background: rgba(181, 181, 181, 0.5);
            border: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: rgba(32, 37, 43, 0.5);
            flex-shrink: 0;
            width: 146px; /* Новая ширина */
            height: 25px; /* Новая высота */
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;

        }

        .status-comment-field {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 582px; /* Новая ширина */
            height: 25px; /* Новая высота */
            display: flex;
            align-items: center;
        }

        .status-date {
            display: flex;
            flex-direction: column;
            align-items: center;
            font-size: 12px;
            font-weight: 600;
            color: #20252B;
            text-align: center;
            line-height: 1.4;
            white-space: nowrap;
            width: 100px;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
            width: 100%;
        }

        .form-container input[type="text"] {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            width: 582px; /* Новая ширина */
            height: 25px; /* Новая высота */
        }

        .form-container select {
            /*padding: 8px 12px;*/
            font-size: 14px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            color: #20252B;
            width: 172px; /* Новая ширина */
            height: 43px; /* Новая высота */
            box-sizing: border-box;
            text-align: center;
        }

        .form-container button {
            padding: 8px 12px;
            font-size: 14px;
            background: #82C562;
            color: #FFFFFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            align-self: flex-end;
        }

        .form-container button:hover {
            background: #A3E48C;
        }

        .deal-links-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            margin-top: 20px;
        }

        .back-button {
            font-size: 14px;
            color: #20252B;
            text-decoration: none;
            background: #F3F3F4;
            padding: 8px 12px;
            border-radius: 5px;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
        }

        .back-button:hover {
            background: #E0E0E0;
        }

        .status-date-day {
            margin-bottom: 2px; /* Отступ между датой и временем */
        }

        .status-date-time {
            font-size: 12px;
            font-weight: 500;
        }

        .payment-row1 {
            display: flex;
            align-items: center;
            gap: 10px;
            width: 100%;
        }

        .delete-button1 {
            background: #FFADAD;
            color: #20252B;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
            border: 1px solid #D1D1D3;
            width: 109px;
        }
        .status-new {
            background-color: #FDFABD;
            color: #20252B;
        }

        .status-in-progress {
            background-color: #C3EDFF;
            color: #20252B;
        }

        .status-paid {
            background-color: #D8FFC6;
            color: #20252B;
        }

        .status-completed {
            background-color: #A3E48C;
            color: #20252B;
        }

        .status-declined {
            background-color: #FFADAD;
            color: #20252B;
        }
        input[type="text"]::placeholder {
             color: #B5B5B5; /* Цвет плейсхолдера */
         }
        .statuses-header {
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
    <div class="statuses-container">
        <div class="statuses-header">
            <div class="header-container">
        <form action="/statuses/${dealId.dealId}/back" method="get">
            <button class="delete-button" type="submit" style="background: none; border: none; padding: 0; cursor: pointer; margin-left: 0;">
                <img src="/icons/back.svg" alt="Назад" style="width: 18px; height: 32px;">
            </button>
        </form>
        <span style="
                margin-left: 30px;
                padding-bottom: 10px;
                ">Статус сделки №${dealId.dealId}
        </span>
            </div>
        </div>
        <#list allStatuses?reverse as status>
            <div class="payment-row1">
                <div class="status-field
            <#if status?is_first>
                <#if status.statusChoose == 'Новая заявка'>status-new
                <#elseif status.statusChoose == 'В работе'>status-in-progress
                <#elseif status.statusChoose == 'Оплачен'>status-paid
                <#elseif status.statusChoose == 'Завершен'>status-completed
                <#elseif status.statusChoose == 'Отказ'>status-declined
                </#if>
            </#if>">
                    ${status.statusChoose}
                </div>
                <div class="status-date">
                    <div class="status-date-day">${status.currentDate?substring(0, 10)}</div>
                    <div class="status-date-time">${status.currentDate?substring(11)}</div>
                </div>
                <div class="status-comment-field">${status.statusComment}</div>
            </div>
        </#list>
        <br>


        <#--        <form action="/saveStatus/${dealId.dealId}" method="post" class="form-container">-->
<#--            <select name="statusChoose" required>-->
<#--                <#list statusList as status>-->
<#--                    <option value="${status}">${status}</option>-->
<#--                </#list>-->
<#--            </select>-->
<#--            <input type="text" name="statusComment" placeholder="Комментарий" required />-->
<#--            <button type="submit">Сохранить</button>-->
<#--        </form>-->

        <form action="/saveStatus/${dealId.dealId}" method="post" class="form-container">
            <div class="payment-row1">
                <select name="statusChoose" required>
                    <option value="" disabled selected>Выбрать</option>
                    <#list statusList as status>
                        <option value="${status}">${status}</option>
                    </#list>
                </select>
                <div class="status-date">
                    <div class="status-date-day">Сегодня</div>
                </div>
                <input type="text" name="statusComment" placeholder="Комментарий" required />

            </div>
            <div class="deal-links-container">
                <form>
                    <button type="submit" class="delete-button1" style="background: #FEB155; color: #20252B;">Сохранить</button>
                </form>
            </div>
        </form>
    </div>
</div>
</body>
</html>
