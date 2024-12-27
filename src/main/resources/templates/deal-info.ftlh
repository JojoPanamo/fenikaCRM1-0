<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Информация о сделке</title>
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
            margin-left: 70px;
            padding: 40px;
            width: calc(100% - 70px);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            box-sizing: border-box;
        }

        /* Прямоугольник с информацией */
        .deal-info-container {
            position: relative;
            width: 900px;
            height: 600px;
            background-color: #FFFFFF;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            padding: 36px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Заголовок */
        .deal-info-header {
            font-size: 26px;
            font-weight: 600;
            margin-bottom: 20px;
        }

        /* Последний статус */
        .deal-status {
            position: absolute;
            top: 36px;
            right: 36px;
            display: inline-block;
            padding: 8px 12px;
            font-size: 14px;
            font-weight: 500;
            border-radius: 5px;
            border: 1px solid #D1D1D3;
            background-color: #FFFFFF;
            color: #20252B;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);

        }

        /* Цвета для статусов */
        .status-new {
            background-color: #FDFABD;
        }

        .status-in-progress {
            background-color: #C3EDFF;
        }

        .status-paid {
            background-color: #D8FFC6;
        }

        .status-completed {
            background-color: #A3E48C;
        }

        .status-declined {
            background-color: #FFADAD;
        }

        /* Поля информации */
        .deal-info-fields {
            display: flex;
            flex-direction: column;
            gap: 20px;
            margin-bottom: 20px;
        }

        .field-row {
            display: flex;
            justify-content: space-between;
            gap: 20px;
        }

        .client-field {
            width: calc(50% - 10px);
        }

        .label {
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
        }
        .label1 {
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
            margin-left: 242px;
        }

        .input {
            box-sizing: border-box;
            padding: 12px 16px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.6;
            color: #20252B;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            height: 47px;
            /*margin-left: 262px;*/
            /*box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);*/

        }
        .input1 {
            box-sizing: border-box;
            padding: 12px 16px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.6;
            color: #20252B;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            height: 47px;
            margin-left: 242px;
            width: 197px;
            /*box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);*/

        }
        .input2 {
            box-sizing: border-box;
            padding: 12px 16px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.6;
            color: #20252B;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            height: 47px;
            width: 682px;
            /*box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);*/

        }

        /* Ссылки */
        .deal-links {
            position: absolute;
            bottom: 36px;
            right: 36px;
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            height: 37px;
            width: 350px;
            left: 720px;
        }

        .deal-links a {
            font-size: 14px;
            color: #20252B;
            text-decoration: none;
            background: #F6F6F6;
            padding: 8px 12px;
            border-radius: 5px;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
        }

        .deal-links a:hover {
            background: #E0E0E0;
        }

        /* Кнопка "Назад к сделкам" */
        .back-button {
            position: absolute;
            bottom: 36px;
            left: 36px;
            font-size: 14px;
            font-weight: 500;
            background: #FEB155;
            border-radius: 5px;
            padding: 8px 12px;
            cursor: pointer;
            height: 37px;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
        }

        .back-button:hover {
            background: #FFC580;
        }
        input[type="text"]::placeholder,
        input[type="number"]::placeholder {
            color: #20252B;
            text-align: center;
            font-size: 14px;/* Цвет плейсхолдера */
        }
        .cancel-button {
            position: absolute;
            bottom: 36px;
            left: 36px;
            font-size: 14px;
            font-weight: 500;
            background: none;
            border-radius: 5px;
            padding: 8px 12px;
            cursor: pointer;
            height: 37px;
            border: none;
            box-shadow: none;
            margin-left: 120px;
            color: #BABBBE;
        }

        .cancel-button:hover {
            background: none;
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
    <a href="/clients">
        <img src="/icons/clients.svg" alt="Клиенты">
        <p>Клиенты</p>
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
    <div class="deal-info-container">
<#--        <div class="deal-info-header">-->
<#--            Сделка №${deal.dealId}-->
<#--        </div>-->
        <div class="deal-info-header">
            <div class="header-container">
                <form action="/deal-info/${dealId}/back" method="get">
                    <button class="delete-button" type="submit" style="background: none; border: none; padding: 0; cursor: pointer; margin-left: 0;">
                        <img src="/icons/back.svg" alt="Назад" style="width: 18px; height: 32px;">
                    </button>
                </form>
                <span style="
                margin-left: 30px;
                padding-bottom: 10px;
                ">Сделка №${dealId}
        </span>
            </div>
        </div>

        <a href="/statuses/${deal.dealId}" class="deal-status
            <#if deal.lastStatus == 'Новая заявка'>status-new
            <#elseif deal.lastStatus == 'В работе'>status-in-progress
            <#elseif deal.lastStatus == 'Оплачен'>status-paid
            <#elseif deal.lastStatus == 'Завершен'>status-completed
            <#elseif deal.lastStatus == 'Отказ'>status-declined</#if>" style="text-decoration: none">
            ${deal.lastStatus} >
        </a>

        <div class="client-request">
            <div class="label">Что запросил клиент:</div>
            <div class="input">${deal.whatNeed}</div>
        </div>
        <br>
        <br>

        <div class="deal-info-fields">
            <div class="field-row">
                <div class="client-field">
                    <div class="label">Наименование клиента:</div>
                    <div class="input">${deal.client.clientName}</div>
                </div>
                <div class="client-field">
                    <div class="label">Номер телефона клиента:</div>
                    <div class="input">${deal.client.phoneNumber}</div>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label">Эл. почта:</div>
                    <div class="input">${deal.client.email}</div>

                </div>
                <div class="client-field">
                    <div class="label">Адрес доставки:</div>
                    <div class="input">${deal.deliveryAdress}</div>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label">Откуда:</div>
                    <div class="input">${deal.whereFrom}</div>
                </div>
                <div class="client-field">
                    <div class="label">Ответственный:</div>
                    <div class="input">${deal.user.name}</div>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label">Комментарии:</div>
                    <div class="input2">
                        <a href="/comments/${deal.dealId}" style="text-decoration: none; color: #20252B">${deal.lastComment} ></a>
                    </div>
                </div>
                <div class="client-field">
                    <div class="label1">Предполагаемый бюджет:</div>
                    <div style="display: flex; align-items: center; gap: 10px;">
                        <!-- Форматированное отображение -->
                        <div class="formatted-sum" hidden="hidden">
                            ${deal.thinkSum!0 ?string("###,###.##")} руб.
                        </div>

                        <!-- Поле ввода для изменения суммы -->
                        <form action="/update-think-sum" method="post" style="display: flex; align-items: center; gap: 10px;">
                            <input type="hidden" name="dealId" value="${deal.dealId}">
                            <input
                                    type="number"
                                    name="thinkSum"
                                    value="${deal.thinkSum!0}"
                                    class="input1"
                                    style="color: #20252B"
                                    placeholder="${deal.thinkSum!0} руб.">
<#--                            <button type="submit" class="save-button">Сохранить</button>-->
                            </div>
                </div>
            </div>

        </div>

        <div class="deal-links1">

            <button type="submit" class="back-button">Сохранить</button>
        </div>
<#--            <form action="/deals" method="get">-->
<#--            <button type="submit" class="cancel-button">Отменить</button>-->
        </form>

        <div class="deal-links">
<#--            <a href="/comments/${deal.dealId}">${deal.lastComment} ></a>-->
            <a href="/files/${deal.dealId}">Вложения ></a>
            <a href="/payments/${deal.dealId}">Оплаты ></a>
<#--            <a href="/statuses/${deal.dealId}">Статус ></a>-->
        </div>
    </div>
</div>

</body>
</html>
