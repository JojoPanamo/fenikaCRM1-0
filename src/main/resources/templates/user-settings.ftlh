<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Настройки пользователей</title>
    <style>
        /* Основные стили */
        body {
            margin: 0;
            font-family: 'Manrope', sans-serif;
            background-color: #F3F3F4;
            color: #20252B;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 40px 20px;
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

        h1 {
            margin-bottom: 30px;
        }

        /* Таблица */
        table {
            width: 100%;
            max-width: 800px;
            border-collapse: collapse;
            background: #FFFFFF;
            border: 1px solid #D1D1D3;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        th, td {

            padding: 12px 16px;
            font-size: 14px;
            border: 1px solid #D1D1D3;
            border-collapse: collapse;
            text-align: center;
            
        }

        th {
            /*background: #F6F6F6;*/
            font-weight: 600;
            border-collapse: collapse;
        }

        tr:last-child td {
            border-bottom: none;
        }

        /* Поля ввода */
        input[type="number"] {
            width: 100%;
            max-width: 100px;
            padding: 8px;
            border: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            text-align: center;
        }



        .statistics-button {
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

        .statistics-button:hover {
            background: #FFC580;
        }
    </style>
</head>
<body>
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
            <p>  Добавить<br>менеджера</p>
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
<h1>Настройки пользователей</h1>
<table>
    <thead>
    <tr>
        <th>Имя</th>
        <th>Email</th>
        <th>Процент</th>
        <th>Обновить</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>
                <!-- Отдельная форма для каждого пользователя -->
                <form action="/update-percentage" method="post" style="display: flex; align-items: center; gap: 10px;">
                    <input type="number" name="percentage" value="${user.percentage}" min="0" max="100" required>
                    <input type="hidden" name="userId" value="${user.userId}">
            </td>
            <td>
                <button type="submit" class="statistics-button">Сохранить</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<#--<form action="/statistics" method="get">-->
<#--    <button type="submit" class="statistics-button">Статистика</button>-->
<#--</form>-->
</body>
</html>
