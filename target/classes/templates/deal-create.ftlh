<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Создание новой сделки</title>
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

        /* Контейнер формы */
        .deal-info-container {
            position: relative;
            width: 900px;
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

        /* Поля формы */
        .deal-info-fields {
            display: flex;
            flex-direction: column;
            gap: 20px;
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
            width: 100%;
            /*box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);*/
        }


        select.input {
            width: 100%;
            height: 48px;
            padding: 12px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            cursor: pointer;
            appearance: auto;
            box-sizing: border-box;
        }

        /* Кнопки */
        .button-row {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

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

        .save-button {
            font-size: 14px;
            font-weight: 500;
            background: #82C562;
            border-radius: 5px;
            padding: 8px 12px;
            cursor: pointer;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
        }

        .save-button:hover {
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
    <div class="deal-info-container">
        <div class="deal-info-header">Создание новой сделки</div>
        <form action="deal-create-save" method="post" class="deal-info-fields">
            <!-- Поле "Что запросил клиент" -->
            <div>
                <div class="label" style="font-size: 12px">Что запросил клиент:</div>
                <input type="text" name="whatNeed" class="input" required>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label" style="font-size: 12px">Наименование клиента:</div>
                    <input type="text" name="name" class="input" required>
                </div>
                <div class="client-field">
                    <div class="label" style="font-size: 12px">Номер телефона клиента:</div>
                    <input type="text" name="numberPhone" class="input" required>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label" style="font-size: 12px">Электронная почта:</div>
                    <input type="text" name="email" class="input">
                </div>
                <div class="client-field">
                    <div class="label" style="font-size: 12px">Адрес доставки:</div>
                    <input type="text" name="deliveryAdress" class="input" required>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label" style="font-size: 12px">Откуда пришел клиент:</div>
                    <select name="whereFrom" class="input" required>
                        <option value="" disabled selected>Выберите источник</option>
                        <#list whereFromOptions as option>
                            <option value="${option}">${option}</option>
                        </#list>
                    </select>
                </div>
                <div class="client-field">
                    <div class="label" style="font-size: 12px">Ответственный:</div>
                    <input type="text" class="input" value="${userName}" readonly>
                </div>
            </div>
            <br>
            <div class="button-row">
                <button type="submit" class="back-button">Сохранить</button>
                <form action="/deals" method="get">
                    <button type="submit" class="cancel-button" formnovalidate >Отменить</button>
                </form>
            </div>
    </div>
</div>
</body>
</html>
