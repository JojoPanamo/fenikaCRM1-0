<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Оплаты сделки</title>
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

        input[type="text"]{
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 520px;
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

        .payments-container {
            display: flex;
            flex-direction: column;
            gap: 15px; /* Уменьшенный отступ между строками */
            width: 100%;
            max-width: 900px;
            background: #FFFFFF;
            padding: 40px;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 200px;
        }

        .payments-header {
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

        .payment-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .payment-row1 {
            display: flex;
            align-items: center;
            gap: 10px;
            width: 100%;
        }

        .payment-field {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 95px;
            text-align: center;
        }

        .payment-comment-field {
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 520px;
        }

        .payment-date {
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

        .payment-date-day {
            margin-bottom: 2px; /* Отступ между датой и временем */
        }

        .payment-date-time {
            font-size: 12px;
            font-weight: 500;
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
        .delete-button {
            background: none; /* Убираем фон */
            border: none; /* Убираем рамку */
            color: #BABBBE; /* Устанавливаем цвет текста */
            font-size: 14px; /* Размер шрифта */
            cursor: pointer;
            margin-left: 20px; /* Указываем, что это кликабельный элемент */
            font-weight: bold;
        }

        .delete-button:hover {
            color: #20252B; /* Цвет текста при наведении */
            text-decoration: none; /* Убираем подчеркивание при наведении, если нужно */
        }

        .summary {
            display: flex;
            justify-content: space-between;
            font-size: 16px;
            font-weight: 600;
        }

        .form-container {
            display: flex;
            flex-direction: column;
            gap: 15px; /* Уменьшенный отступ для формы */
            width: 100%;
        }

        .form-container input[type="number"]{
            padding: 8px 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            flex-shrink: 0;
            width: 95px;
            text-align: center;
        }
        input[type="text"]::placeholder,
        input[type="number"]::placeholder {
            color: #B5B5B5; /* Цвет плейсхолдера */
        }

        .form-container select {
            padding: 8px 3px;
            font-size: 14px;
            background: #F6F6F6;
            border: 1px solid #D1D1D3;
            border-radius: 5px;
            color: #20252B;
            width: 121px;
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

        /*.back-button {*/
        /*    font-size: 14px;*/
        /*    color: #20252B;*/
        /*    text-decoration: none;*/
        /*    background: #F3F3F4;*/
        /*    padding: 8px 12px;*/
        /*    border-radius: 5px;*/
        /*    border: 1px solid #D1D1D3;*/
        /*    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);*/
        /*}*/
        .deal-links-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            margin-top: 20px;
        }

        .deal-links {
            font-size: 14px;
            color: #20252B;
            text-decoration: none;
            background: #F3F3F4;
            padding: 8px 12px;
            border-radius: 5px;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
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


        .back-button:hover {
            background: #E0E0E0;
        }

        .payment-paid {
            background-color: #D8FFC6;
            /*border-color: #D8FFC6;*/
        }
        .payment-other {
            background-color: #FFADAD;
            /*border-color: #FFADAD;*/
        }

        .deal-links {
            font-size: 14px;
            color: #20252B;
            text-decoration: none;
            background: #F3F3F4;
            padding: 8px 12px;
            border-radius: 5px;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
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
    <div class="payments-container">
        <div class="payments-header">
            <div class="header-container">
                <form action="/payments/${dealId.dealId}/back" method="get">
                    <button class="delete-button" type="submit" style="background: none; border: none; padding: 0; cursor: pointer; margin-left: 0;">
                        <img src="/icons/back.svg" alt="Назад" style="width: 18px; height: 32px;">
                    </button>
                </form>
                <span style="
                margin-left: 30px;
                padding-bottom: 10px;
                ">Оплаты сделки №${dealId.dealId}</span>
            </div>
        </div>

        <#list allPayments?reverse as payment>
            <div class="payment-row">
                <span class="payment-field
                    <#if payment.statusPayments == 'Поступление'>payment-paid
                    <#elseif payment.statusPayments == 'Расход'>payment-other
                    <#elseif payment.statusPayments == 'Налог'>payment-oter</#if>">
                    ${payment.statusPayments}
                </span>
                <div class="payment-date">
                    <div class="payment-date-day">${payment.currentDate?substring(0, 10)}</div>
                    <div class="payment-date-time">${payment.currentDate?substring(11)}</div>
                </div>
                <div class="payment-field">${payment.sum} руб.</div>
                <div class="payment-comment-field">${payment.commentPayments}</div>
                <form action="/payments/delete/${payment.paymentId}" method="post">
                    <button class="delete-button" type="submit" style="color:#FFADAD; margin-left: 0 ">&nbsp;Х&nbsp;</button>
                </form>
            </div>
        </#list>
        <br>
        <br>
<#--        <div class="summary">-->
<#--            <p>Прибыль компании: ${earnedMoney}</p>-->
<#--            <p>Прибыль менеджера: ${moneyOfManager}</p>-->
<#--        </div>-->
        <form action="/savePayment/${dealId.dealId}" method="post" class="form-container">
            <div class="payment-row1">
                    <select name="statusPayments" required>
                        <option value="" disabled selected>Выбрать</option>
                        <#list listOfStatPay as payListStat>
                            <option value="${payListStat}">${payListStat}</option>
                        </#list>
                    </select>
                <div class="payment-date">
                    <div class="payment-date-day">Сегодня</div>
                </div>
                    <input type="number" name="sum" placeholder="Сумма" required />
                    <input type="text" name="commentPayments" placeholder="Комментарий" required />
            </div>
            <div class="deal-links-container">
                <form>
                    <button type="submit" class="delete-button1" style="background: #FEB155; color: #20252B;">Сохранить</button>
                </form>
                Остаток: ${earnedMoney} руб.
            </div>
        </form>


    </div>


</div>
</body>
</html>
