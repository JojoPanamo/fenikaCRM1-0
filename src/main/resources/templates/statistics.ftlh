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
        }
        .row {
            display: flex;
            justify-content: space-between;
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
        }
        .stat-block p {
            margin-top: 10px;
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
            font-size: 16px;
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
    <a href="/deals">
        <img src="/icons/Vector.svg" alt="Сделки">
        <p>Сделки</p>
    </a>
    <a href="/logout">
        <img src="/icons/exit.svg" alt="Выход">
        <p>Выход</p>
    </a>
    <a href="/registration">
        <img src="/icons/reg.svg" alt="новый пользователь">
        <p>+ Юзер</p>
    </a>
    <a href="/user-settings">
        <img src="/icons/sett.svg" alt="Настройки пользователя">
        <p>Настр</p>
    </a>
</div>

<!-- Content Area -->
<div class="content">
    <#if isAdmin>
        <!-- Общая статистика по компании -->
        <div class="stats-wrapper">
            <h2>Общая статистика компании</h2>
            <div class="row">
                <div class="stat-block">
                    <div class="number">${completedDealsCount!0}</div>
                    <p>Завершенные сделки</p>
                </div>
                <div class="stat-block">
                    <div class="number">${inProgressOrPaidDealsCount!0}</div>
                    <p>Сделки в работе и оплаченные</p>
                </div>
                <div class="stat-block">
                    <div class="number">${refusedDealsCount!0}</div>
                    <p>Отказанные сделки</p>
                </div>
            </div>

            <div class="row">
                <div class="large-block">
                    <div class="number">${totalPayments!0.0}</div>
                    <p>Общая сумма оплат</p>
                </div>
                <div class="large-block">
                    <div class="number">${companyProfit!0.0}</div>
                    <p>Прибыль компании</p>
                </div>
            </div>
        </div>

        <!-- Статистика по каждому менеджеру -->
        <#list userStatisticsMap?keys as userId>
            <div class="stats-wrapper">
                <h2>Статистика для ${userNamesMap[userId]}</h2>

                <#if userStatisticsMap[userId]??>
                    <div class="row">
                        <div class="stat-block">
                            <div class="number">${userStatisticsMap[userId].completedDeals!0}</div>
                            <p>Завершенные сделки</p>
                        </div>
                        <div class="stat-block">
                            <div class="number">${userStatisticsMap[userId].inProgressOrPaidDeals!0}</div>
                            <p>Сделки в работе и оплаченные</p>
                        </div>
                        <div class="stat-block">
                            <div class="number">${userStatisticsMap[userId].refusedDeals!0}</div>
                            <p>Отказанные сделки</p>
                        </div>
                        <div class="stat-block">
                            <div class="number">${userStatisticsMap[userId].percentage!0}</div>
                            <p>Ваш процент</p>
                        </div>
                    </div>

                    <div class="row">
                        <div class="large-block">
                            <div class="number">${userStatisticsMap[userId].totalPayments!0.0}</div>
                            <p>Общая сумма оплат</p>
                        </div>
                        <div class="large-block">
                            <div class="number">${userStatisticsMap[userId].companyProfit!0.0}</div>
                            <p>Прибыль компании</p>
                        </div>
                    </div>

                    <div class="manager-profit">
                        <div class="number">${userStatisticsMap[userId].managerProfit!0.0}</div>
                        <p>Прибыль менеджера</p>
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
                <div class="stat-block">
                    <div class="number">${completedDealsCount!0}</div>
                    <p>Завершенные сделки</p>
                </div>
                <div class="stat-block">
                    <div class="number">${inProgressOrPaidDealsCount!0}</div>
                    <p>Сделки в работе и оплаченные</p>
                </div>
                <div class="stat-block">
                    <div class="number">${refusedDealsCount!0}</div>
                    <p>Отказанные сделки</p>
                </div>
                <div class="stat-block">
                    <div class="number">${percentage}</div>
                    <p>Ваш процент</p>
                </div>
            </div>

            <div class="row">
                <div class="large-block">
                    <div class="number">${totalPayments!0.0}</div>
                    <p>Общая сумма оплат</p>
                </div>
                <div class="large-block">
                    <div class="number">${companyProfit!0.0}</div>
                    <p>Прибыль компании</p>
                </div>
            </div>

            <div class="manager-profit">
                <div class="number">${managerProfit!0.0}</div>
                <p>Прибыль менеджера</p>
            </div>
        </div>
    </#if>
</div>
</body>
</html>
