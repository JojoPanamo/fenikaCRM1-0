<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Статистика пользователя</title>
    <style>
        /* Main Container */
        body {
            display: flex;
            flex-direction: row;
            margin: 0;
            font-family: 'Manrope', sans-serif;
            background-color: #F0F2F5;
            color: #20252B;
        }
        h1 {
            font-size: 34px;
            font-weight: 600;
            color: #20252B;
            margin: 20px 0;
        }
        h2 {
            font-size: 34px;
            font-weight: 400;
            color: #20252B;
            margin: 20px 0;
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
            box-sizing: border-box;
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
        /* Filter Container */
        .stats-filter {
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            width: 250px;
            height: 34px;
            margin: 20px auto;
            gap: 30px;
        }

        /* Button Style */
        .filter-options {
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            padding: 4px 35px;
            gap: 30px;
            width: 392px;
            height: 34px;
            border: 1px solid #20252B;
            border-radius: 30px;
        }

        .filter-button {
            font-family: 'Manrope', sans-serif;
            font-style: normal;
            font-weight: 300;
            font-size: 14px;
            color: #20252B;
            background: none;
            border: none;
            cursor: pointer;
            padding: 4px 10px;
            transition: font-weight 0.2s;
        }

        .filter-button.active {
            font-weight: 600;
        }

        .filter-button:hover {
            font-weight: 500;
        }


        /* Content Area */
        .content {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: calc(100% - 70px);
        }

        /* Layout Containers */
        .stats-wrapper {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 20px;
            width: 850px;
            margin-left: 10px;
        }
        .row {
            display: flex;
            justify-content: center;
            gap: 20px;
            width: 100%;
        }


        /* Stat Blocks */
        .stat-block {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: #20252B;
            border-radius: 30px;
            width: 200px;
            height: 200px;
            color: #FFFFFF;
            padding: 10px;
        }
        .stat-block p {
            margin-top: 10px;
            margin-left: 20px;
            margin-right: 20px;
            font-size: 16px;
            font-weight: 300;
            text-align: center;
        }

        .stat-block1 {
            display: flex;
            flex-direction: column;
            align-items: center;
            /*justify-content: center;*/
            background: #20252B;
            border-radius: 30px;
            width: 200px;
            height: 200px;
            color: #FFFFFF;
            padding: 10px;
        }
        .stat-block1 p {
            margin-top: 0px;
            margin-left: 30px;
            margin-right: 30px;
            margin-bottom: 35px;
            padding-bottom: 10px;
            padding-top: 10px;
            font-size: 16px;
            font-weight: 300;
            text-align: center;
        }
        .stat-block,
        .stat-block1,
        .stat-block2,
        .stat-block4 {
            width: 200px; /* Единая ширина */
            height: 200px; /* Единая высота */
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: #20252B;
            border-radius: 30px;
            color: #FFFFFF;
            padding: 10px;
            box-sizing: border-box;
        }
        .stat-block3{
            width: 200px; /* Единая ширина */
            height: 200px; /* Единая высота */
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: #F0F2F5;
            border-radius: 30px;
            color: #20252B;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #20252B;
        }
        .stat-block2 p {
            margin-top: 10px;
            margin-left: 20px;
            margin-right: 20px;
            font-size: 14px;
            font-weight: 300;
            text-align: center;
        }
        .stat-block3 p {
            margin-top: 10px;
            margin-left: 20px;
            margin-right: 20px;
            font-size: 16px;
            font-weight: 300;
            text-align: center;
            /*border: 1px solid #20252B;*/
            /*border-color: #20252B;*/
            /*border-width: 5px;*/
        }
        .stat-block4 {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: #20252B;
            border-radius: 30px;
            width: 200px;
            height: 200px;
            color: #FFFFFF;
            padding: 0px;
            margin-top: 10px;
            margin-bottom: 10px;
            margin-right: 224px;
            margin-left: 10px;
        }
        .stat-block4 p {
            margin-top: 10px;
            margin-left: 20px;
            margin-right: 20px;
            font-size: 16px;
            font-weight: 300;
            text-align: center;
        }

        .number {
            font-size: 80px;
            font-weight: 600;
            background: linear-gradient(180deg, #FFFFFF 0%, rgba(255, 255, 255, 0.2) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            text-fill-color: transparent;
        }
        .number1 {
            font-size: 80px;
            font-weight: 600;
            background: linear-gradient(180deg, #FFADAD 0%, rgba(255, 0, 0, 0.2) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            text-fill-color: transparent;
        }
        .number4 {
            font-size: 80px;
            font-weight: 600;
            background: linear-gradient(180deg, #20252B 0%, rgba(255, 255, 255, 0.2) 100%);
            -webkit-background-clip: text;
            /*-webkit-text-fill-color: transparent;*/
            background-clip: text;
            text-fill-color: transparent;
        }


        /* Profit and Payments Blocks */
        .large-block {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background: #20252B;
            border-radius: 30px;
            width: 417px;
            height: 200px;
            color: #FFFFFF;
        }
        .large-block p {
            font-size: 14px;
            font-weight: 300;
            text-align: center;
            margin-top: 10px;
        }

        /* Manager Profit Block */
        .manager-profit {
            width: 850px;
            height: 200px;
            background: #20252B;
            border-radius: 30px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: #FFFFFF;
        }
        .manager-profit .number {
            font-size: 100px;
            font-weight: 600;
            background: linear-gradient(180deg, #FBFE55 0%, rgba(51, 157, 1, 0.2) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            text-fill-color: transparent;
        }
        .manager-profit p {
            font-size: 16px;
            font-weight: 300;
            color: #FFFFFF;
            text-align: center;
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

<!-- Content Area -->
<div class="content">
    <#if isAdmin>
        <!-- Общая статистика по компании -->
        <div class="stats-wrapper">
            <h2>Общая статистика компании</h2>
<#--            <div class="stats-filter">-->
<#--                <div class="filter-options">-->
<#--                    <button class="filter-button" onclick="showMonthlyStatistics()">Месяц</button>-->
<#--                    <button class="filter-button" onclick="showYearlyStatistics()">Год</button>-->
<#--                </div>-->
<#--            </div>-->

            <div class="row">
                <div class="stat-block2">
                    <div class="number">${totalDealsCount!0}</div>
                    <p>ВСЕГО СДЕЛОК</p>
                </div>
                <div class="stat-block2">
                    <div class="number">${completedDealsCount!0}</div>
                    <p>ЗАВЕРШЕННЫЕ СДЕЛКИ</p>
                </div>
                <div class="stat-block2">
                    <div class="number">${inProgressOrPaidDealsCount!0}</div>
                    <p>СДЕЛКИ В РАБОТЕ И ОПЛАЧЕННЫЕ</p>
                </div>
                <div class="stat-block2">
                    <div class="number1">${refusedDealsCount!0}</div>
                    <p>СДЕЛКИ <br>В ОТКАЗЕ</p>
                </div>
            </div>
            <div class="stats-wrapper">
                <div class="row">
                    <div class="large-block">
                        <div class="number">${totalPayments!0.0}</div>
                        <p>ОБЩАЯ СУММА ОПЛАТ</p>
                    </div>
                    <div class="large-block">
                        <div class="number">${companyProfit!0.0}</div>
                        <p>ПРИБЫЛЬ СО СДЕЛОК</p>
                    </div>
                </div>
            </div>
            <br>
                <h2>Статистика по источникам клиентов</h2>

                <!-- Первый ряд -->
                <div class="row source-stats">
                    <#list dealsCountBySource?keys[0..3] as source>
                        <div class="stat-block3">
                            <div class="number4">${dealsCountBySource[source]!0}</div>
                            <p>${source}</p>
                        </div>
                    </#list>
                </div>

                <!-- Второй ряд -->
                <div class="row source-stats">
                    <#list dealsCountBySource?keys[4..4] as source>
                        <div class="stat-block3">
                            <div class="number4">${dealsCountBySource[source]!0}</div>
                            <p>${source}</p>
                        </div>
                    </#list>
                    <#list dealsCountBySource?keys[5..5] as source>
                        <div class="stat-block3">
                            <div class="number4">${dealsCountBySource[source]!0}</div>
                            <p>${source}</p>
                        </div>
                    </#list>
                </div>
            <br>
            </div>
        <br>

        <!-- Статистика по каждому менеджеру -->
        <#list userStatisticsMap?keys as userId>
            <div class="stats-wrapper">
                <br>
                <h2>Статистика для ${userNamesMap[userId]}</h2>

                <#if userStatisticsMap[userId]??>
                    <div class="row">
                        <div class="stat-block2">
                            <div class="number">${userStatisticsMap[userId].completedDeals!0}</div>
                            <p>ЗАВЕРШЕННЫЕ СДЕЛКИ</p>
                        </div>
                        <div class="stat-block2">
                            <div class="number">${userStatisticsMap[userId].inProgressOrPaidDeals!0}</div>
                            <p>СДЕЛКИ В РАБОТЕ И ОПЛАЧЕННЫЕ</p>
                        </div>
                        <div class="stat-block2">
                            <div class="number1">${userStatisticsMap[userId].refusedDeals!0}</div>
                            <p>СДЕЛКИ  <br>В ОТКАЗЕ</p>
                        </div>
                        <div class="stat-block2">
                            <div class="number">${userStatisticsMap[userId].percentage!0}</div>
                            <p>ВАШ  <br>ПРОЦЕНТ</p>
                        </div>
                    </div>

                    <div class="row">
                        <div class="large-block">
                            <div class="number">${userStatisticsMap[userId].totalPayments!0.0}</div>
                            <p>ОБЩАЯ СУММА ОПЛАТ</p>
                        </div>
                        <div class="large-block">
                            <div class="number">${userStatisticsMap[userId].companyProfit!0.0}</div>
                            <p>ПРИБЫЛЬ СО СДЕЛОК</p>
                        </div>
                    </div>

                    <div class="manager-profit">
                        <div class="number">${userStatisticsMap[userId].managerProfit!0.0}</div>
                        <p>ПРИБЫЛЬ МЕНЕДЖЕРА</p>
                    </div>
                <#else>
                    <p>Нет доступной статистики для данного пользователя.</p>
                </#if>
            </div>
        </#list>
    <#else>
        <!-- Статистика для текущего пользователя -->
        <div class="stats-wrapper">
            <h2>Ваша статистика</h2>
            <div class="row">
                <div class="stat-block2">
                    <div class="number">${completedDealsCount!0}</div>
                    <p>ЗАВЕРШЕННЫЕ СДЕЛКИ</p>
                </div>
                <div class="stat-block2">
                    <div class="number">${inProgressOrPaidDealsCount!0}</div>
                    <p>СДЕЛКИ В РАБОТЕ И ОПЛАЧЕННЫЕ</p>
                </div>
                <div class="stat-block2">
                    <div class="number1">${refusedDealsCount!0}</div>
                    <p>СДЕЛКИ  <br>В ОТКАЗЕ</p>
                </div>
                <div class="stat-block2">
                    <div class="number">${percentage}</div>
                    <p>ВАШ  <br>ПРОЦЕНТ</p>
                </div>
            </div>

            <div class="row">
                <div class="large-block">
                    <div class="number">${totalPayments!0.0}</div>
                    <p>ОБЩАЯ СУММА ОПЛАТ</p>
                </div>
                <div class="large-block">
                    <div class="number">${companyProfit!0.0}</div>
                    <p>ПРИБЫЛЬ СО СДЕЛОК</p>
                </div>
            </div>

            <div class="manager-profit">
                <div class="number">${managerProfit!0.0}</div>
                <p>ПРИБЫЛЬ МЕНЕДЖЕРА</p>
            </div>
        </div>
    </#if>
</div>
</body>
</html>
