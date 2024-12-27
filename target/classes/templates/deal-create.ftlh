<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Создание новой сделки</title>
    <style>
        body {
            display: flex;
            flex-direction: row;
            margin: 0;
            font-family: 'Manrope', sans-serif;
            background-color: #F3F3F4;
            color: #20252B;
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
            margin-left: 70px;
            padding: 40px;
            width: calc(100% - 70px);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            box-sizing: border-box;
        }

        .deal-info-container {
            position: relative;
            width: 900px;
            background-color: #FFFFFF;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            padding: 36px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .deal-info-header {
            font-size: 26px;
            font-weight: 600;
            margin-bottom: 20px;
        }

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

        .button-row {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

        .back-button {
            font-size: 14px;
            font-weight: 500;
            background: #FEB155;
            border-radius: 5px;
            padding: 8px 12px;
            cursor: pointer;
            border: 1px solid #D1D1D3;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
        }

        .back-button:hover {
            background: #FFC580;
        }

        .cancel-button {
            font-size: 14px;
            font-weight: 500;
            background: none;
            border-radius: 5px;
            padding: 8px 12px;
            cursor: pointer;
            border: none;
            box-shadow: none;
            margin-left: 120px;
            color: #BABBBE;

        }

        .cancel-button:hover {
            background: none;
        }

        .suggestions {
            position: absolute;
            background: white;
            border: 1px solid #D1D1D3;
            border-radius: 5px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            z-index: 10;
            width: 100%;
            max-height: 150px;
            overflow-y: auto;
        }

        .suggestions div {
            padding: 10px;
            cursor: pointer;
        }

        .suggestions div:hover {
            background: #f0f0f0;
        }

        .star-required {
            color: red;
            font-weight: normal;
        }
        .input-wrapper {
            position: relative;
            width: 100%; /* Контейнер занимает всю ширину */
        }

        .input {
            width: 100%; /* Поле ввода занимает всю ширину контейнера */
            box-sizing: border-box;
        }

        .suggestions {
            position: absolute;
            top: 100%; /* Отображается сразу под полем ввода */
            left: 0;
            width: 100%; /* Подсказки во всю ширину поля ввода */
            background: white;
            border: none;
            border-radius: 5px;
            /*box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);*/
            max-height: 150px;
            overflow-y: auto;
            z-index: 10;
            display: none; /* Скрыто по умолчанию */
        }

        .suggestions div {
            padding: 10px;
            cursor: pointer;
        }

        .suggestions div:hover {
            background: none;
        }

    </style>
</head>
<body>
<div class="sidebar">
    <a href="/statistics"><img src="/icons/home.svg" alt="Статистика"><p>Статистика</p></a>
    <a href="/deals"><img src="/icons/Vector.svg" alt="Сделки"><p>Сделки</p></a>
    <a href="/clients"><img src="/icons/clients.svg" alt="Клиенты"><p>Клиенты</p></a>
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
            <div>
                <div class="label">
                    <span class="star-required">*</span>
                    Что запросил клиент:
                </div>
                <input type="text" name="whatNeed" class="input" required>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label">
                        <span class="star-required">*</span>
                        Наименование клиента:</div>
                    <input type="hidden" id="clientId" name="clientId" value="">
                    <div class="input-wrapper">
                        <input type="text" id="clientName" name="clientName" class="input" oninput="searchClient(this.value)" required>
                        <div id="suggestions" class="suggestions"></div>
                    </div>
                </div>
                <div class="client-field">
                    <div class="label"><span class="star-required">*</span> Номер телефона клиента:</div>
                    <input type="text" id="phoneNumber" name="phoneNumber" class="input" required>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label">Электронная почта:</div>
                    <input type="text" id="email" name="email" class="input">
                </div>
                <div class="client-field">
                    <div class="label"><span class="star-required">*</span> Адрес доставки:</div>
                    <input type="text" name="deliveryAdress" class="input" required>
                </div>
            </div>
            <div class="field-row">
                <div class="client-field">
                    <div class="label"><span class="star-required">*</span> Откуда пришел клиент:</div>
                    <select name="whereFrom" class="input" required>
                        <option value="" disabled selected>Выберите источник</option>
                        <#list whereFromOptions as option>
                            <option value="${option}">${option}</option>
                        </#list>
                    </select>
                </div>
                <div class="client-field">
                    <div class="label">Ответственный:</div>
                    <input type="text" class="input" value="${userName}" readonly>
                </div>
            </div>
            <div class="button-row">
                <button type="submit" class="back-button">Сохранить</button>


        </form>
        <form action="/deals" method="get">
            <button type="submit" class="cancel-button" formnovalidate>Отменить</button>
        </form>
    </div>
    </div>
</div>
<script>
    function searchClient(query) {
        const inputField = document.getElementById('clientName');
        const suggestions = document.getElementById('suggestions');

        if (!query.trim()) {
            suggestions.innerHTML = '';
            suggestions.style.display = 'none'; // Скрываем подсказки, если поле пустое
            return;
        }

        const xhr = new XMLHttpRequest();
        xhr.open('GET', `/clients/search?query=` + encodeURIComponent(query), true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                const data = JSON.parse(xhr.responseText);
                suggestions.innerHTML = '';

                // if (data.length === 0) {
                //     const noResults = document.createElement('div');
                //     noResults.textContent = 'Клиенты не найдены';
                //     suggestions.appendChild(noResults);
                //     suggestions.style.display = 'block'; // Показываем пустой список
                //     return;
                // }

                data.forEach(client => {
                    const option = document.createElement('div');
                    option.textContent = (client.clientName || '') + " (" + (client.phoneNumber || '') + ")";
                    option.onclick = () => selectClient(client);
                    suggestions.appendChild(option);
                });

                suggestions.style.display = 'block'; // Показываем подсказки, если есть результаты
            } else {
                console.error('Ошибка загрузки данных:', xhr.status);
            }
        };

        xhr.onerror = function () {
            console.error('Ошибка при выполнении запроса');
        };

        xhr.send();
    }

    function selectClient(client) {
        document.getElementById('clientName').value = client.clientName || '';
        document.getElementById('phoneNumber').value = client.phoneNumber || '';
        document.getElementById('email').value = client.email || '';
        document.getElementById('suggestions').style.display = 'none'; // Скрыть подсказки после выбора
    }


</script>

</body>
</html>
