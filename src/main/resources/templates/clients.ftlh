<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список клиентов</title>
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

        /* Верхнее меню */
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 40px;
            background-color: #F6F6F6;
            border-bottom: 1px solid #D1D1D3;
            width: calc(100% - 70px);
            position: fixed;
            top: 0;
            left: 70px;
            height: 88px;
            box-sizing: border-box;
            z-index: 10;
        }

        .filter-form {
            display: flex;
            align-items: center;
            gap: 10px;
            background: #F6F6F6;
            padding: 12px 16px;
            border-radius: 5px;
        }

        .filter-form select {
            font-size: 14px;
            padding: 8px;
            border: none;
            background-color: #FFFFFF;
        }

        .top-bar .create-deal-btn {
            background: #FEB155;
            border: none;
            padding: 12px 20px;
            font-size: 14px;
            color: #20252B;
            border-radius: 5px;
            cursor: pointer;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
            text-decoration: none;
            width: 85px;
        }

        /* Контент */
        .content {
            margin-left: 90px;
            padding-top: 108px;
            width: calc(100% - 90px);
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Таблица сделок */
        .deals-table {
            width: 100%;
            max-width: 1800px;
            border-collapse: collapse;
            background-color: #FFFFFF;
            border-radius: 5px;
            overflow: hidden;
            border: 1px solid #E0E0E0;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            /*margin-left: 150px;*/
            /*margin-top: 150px;*/
            position: relative;

        }

        .deals-table th, .deals-table td {
            padding: 10px;
            text-align: center;
            border: 1px solid #E0E0E0;
            font-size: 12px;
        }

        .deals-table th {
            background-color: #FFFFFF;
            font-weight: 700;
            color: #20252B;
        }

        .deals-table td {
            font-weight: 400;
            color: #20252B;
        }

        /* Стили для строк-ссылок */
        .deal-row {
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .deal-row:hover {
            background-color: #E0E0E0;
        }

        /* Статусы */
        .status-new { background-color: #FDFABD; }
        .status-in-progress { background-color: #C3EDFF; }
        .status-paid { background-color: #D8FFC6; }
        .status-completed { background-color: #A3E48C; }
        .status-declined { background-color: #FFADAD; }

        .status-cell {
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 12px;
            font-weight: 400;
            text-align: center;
            color: #20252B;
        }
        .filter-form {
            display: flex;
            align-items: center;
            gap: 10px;
            background: #F6F6F6;
            padding: 12px 16px;
            border-radius: 5px;
        }

        .filter-form select {
            font-size: 14px;
            padding: 8px;
            border: none;
            background-color: #FFFFFF;
            border-radius: 5px;
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

<!-- Верхнее меню -->
<div class="top-bar">

    <form class="filter-form" method="get" action="/clients" class="filter-form">
        <input
                id="searchQuery"
                name="searchQuery"
                type="text"
                placeholder="Номер или наименование"
                style="padding: 8px; border-radius: 5px; border: 1px solid #D1D1D3; width: 300px;">
        <button type="submit" class="create-deal-btn">Искать</button>
        <a style="height: 16px; text-align: center" href="/clients" class="create-deal-btn">Сбросить</a>
    </form>

    <#if isAdmin>
        <form class="filter-form" method="get" action="/clients">
            <label for="userId">Фильтр по пользователю:</label>
            <select name="userId" id="userId" onchange="this.form.submit()">
                <option value="" <#if selectedUserId!?string == "">selected</#if>>Все пользователи</option>
                <#list users as user>
                    <option value="${user.userId}" <#if user.userId?string == selectedUserId!?string>selected</#if>>${user.name}</option>
                </#list>
            </select>
        </form>

    </#if>

    <a style="width: 120px" href="/clients/client-create" class="create-deal-btn">+ Новый клиент</a>
</div>

<!-- Content Area -->
<#--<div class="content">-->
<#--    <h4>Список клиентов</h4>-->
<#--    <#list allClients as clients>-->
<#--        ${clients.clientName}<br>-->
<#--        ${clients.phoneNumber}<br>-->
<#--        ${clients.email}<br><br><br>-->

<#--    </#list>-->

<#--</div>-->
<div class="content">
    <h4>Список клиентов</h4>
<table class="deals-table">
    <thead>
    <tr>
        <th class="id-column">ID</th>
        <th class="client-name-column">Наименование</th>
        <th class="phone-number-column">номер телефона</th>
        <th class="email-column">Почта</th>
<#--        <th class="status-column">Статус</th>-->
<#--        <th class="sum-column">Предпологаемая сумма</th>-->
        <#if isAdmin>
            <th class="manager-column">Ответственный</th>
        </#if>
    </tr>
    </thead>
    <tbody>
    <#if allClients?? && allClients?size != 0>
        <#list allClients?reverse as clients>
            <tr class="deal-row" onclick="window.location.href='clients/client-info/${clients.clientId}'">
                <td>#&nbsp;${clients.clientId!''}</td>
                <td>${clients.clientName!''}</td>
                <td>${clients.phoneNumber!''}</td>
                <td>${clients.email!''}</td>
<#--                <td>-->
<#--                    <#if deal.lastStatus??>-->
<#--                        <span class="<#if deal.lastStatus == 'Новая заявка'>status-new<#elseif deal.lastStatus == 'В работе'>status-in-progress<#elseif deal.lastStatus == 'Оплачен'>status-paid<#elseif deal.lastStatus == 'Завершен'>status-completed<#elseif deal.lastStatus == 'Отказ'>status-declined</#if>" style="border-radius: 5px;-->
<#--            border: 1px solid #D1D1D3; padding: 4px; border-radius: 3px">-->
<#--                    ${deal.lastStatus}-->
<#--                </span>-->
<#--                    <#else>-->
<#--                        <span>Статус не установлен</span>-->
<#--                    </#if>-->
<#--                </td>-->
<#--                <td>${deal.thinkSum!0} руб.</td>-->
                <#if isAdmin>
                    <td>${clients.user.name!''}</td>
                </#if>
            </tr>
        </#list>
    <#else>
        <tr>
            <td colspan="7">Пока нет клиентов</td>
        </tr>

    </#if>
    </tbody>
</table>
</div>

<script>
    // function searchClient(query) {
    //     const xhr = new XMLHttpRequest();
    //     xhr.open('GET', `/clients/search?query=` + encodeURIComponent(query), true);
    //
    //     xhr.onload = function () {
    //         if (xhr.status === 200) {
    //             const data = JSON.parse(xhr.responseText);
    //             updateClientTable(data);
    //         } else {
    //             console.error('Ошибка загрузки данных:', xhr.status);
    //         }
    //     };
    //
    //     xhr.onerror = function () {
    //         console.error('Ошибка при выполнении запроса');
    //     };
    //
    //     xhr.send();
    // }

    <#--function updateClientTable(clients) {-->
    <#--    const tableBody = document.querySelector('.deals-table tbody');-->
    <#--    tableBody.innerHTML = ''; // Очищаем старые данные-->

    <#--    if (clients.length === 0) {-->
    <#--        const noResultsRow = document.createElement('tr');-->
    <#--        noResultsRow.innerHTML = `<td colspan="4">Клиенты не найдены</td>`;-->
    <#--        tableBody.appendChild(noResultsRow);-->
    <#--        return;-->
    <#--    }-->

    <#--    &lt;#&ndash;clients.forEach(clients => {&ndash;&gt;-->
    <#--    &lt;#&ndash;    const row = document.createElement('tr');&ndash;&gt;-->
    <#--    &lt;#&ndash;    row.classList.add('deal-row');&ndash;&gt;-->
    <#--    &lt;#&ndash;    row.innerHTML = `&ndash;&gt;-->
    <#--    &lt;#&ndash;    <td>#&nbsp;${clients.clientId!''}</td>&ndash;&gt;-->
    <#--    &lt;#&ndash;        <td>${clients.clientName!''}</td>&ndash;&gt;-->
    <#--    &lt;#&ndash;        <td>${clients.phoneNumber!''}</td>&ndash;&gt;-->
    <#--    &lt;#&ndash;        <td>${clients.email!''}</td>&ndash;&gt;-->
    <#--    &lt;#&ndash;`;&ndash;&gt;-->
    <#--        row.onclick = () => window.location.href = `/clients/client-info/` + clients.clientId;-->
    <#--        tableBody.appendChild(row);-->
    <#--    });-->
    //         }

</script>



</body>
</html>
