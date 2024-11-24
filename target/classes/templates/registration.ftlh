<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <style>
        /* Основные стили */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: 'Manrope', sans-serif;
            background-color: #F3F3F4;
            color: #20252B;
        }

        /* Контейнер для формы */
        .registration-container {
            width: 400px;
            background-color: #FFFFFF;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            padding: 36px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .registration-header {
            font-size: 26px;
            font-weight: 600;
            margin-bottom: 20px;
        }

        .label {
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
            display: block;
            text-align: left;
        }

        .input {
            box-sizing: border-box;
            padding: 12px 16px;
            background: #F6F6F6;
            border: 0;
            border-bottom: 1px solid #D1D1D3;
            border-radius: 5px;
            font-size: 14px;
            color: #20252B;
            width: 100%;
            margin-bottom: 20px;
            /*box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);*/
        }

        .submit-button {
            font-size: 14px;
            font-weight: 500;
            background: #FEB155;
            border-radius: 5px;
            padding: 12px 20px;
            color: #20252B;
            cursor: pointer;
            border: none;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            margin-top: 10px;
        }

        .submit-button:hover {
            background: #FFC580;
        }

        .back-button {
            font-size: 14px;
            font-weight: 500;
            background: #F6F6F6;
            border-radius: 5px;
            padding: 12px 20px;
            color: #20252B;
            cursor: pointer;
            border: none;
            box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            margin-top: 10px;
        }

        .back-button:hover {
            background: #E0E0E0;
        }
        .deal-links {
            position: absolute;
            bottom: 36px;
            right: 36px;
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            height: 37px;
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
    </style>
</head>
<body>
<div class="registration-container">
    <div class="registration-header">Создание пользователя</div>
    <form action="/registration" method="post">
        <label class="label" for="name">Имя:</label>
        <input id="name" type="text" name="name" class="input" required>

        <label class="label" for="phoneNumber">Телефон:</label>
        <input id="phoneNumber" type="text" name="phoneNumber" class="input" required>

        <label class="label" for="email">Почта:</label>
        <input id="email" type="email" name="email" class="input" required>

        <label class="label" for="password">Пароль:</label>
        <input id="password" type="password" name="password" class="input" required>

        <button type="submit" class="submit-button">Подтвердить</button>
    </form>
    <form action="/login" method="post">
        <button type="submit" class="back-button">Назад</button>
    </form>
</div>
</body>
</html>

