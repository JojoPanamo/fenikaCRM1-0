<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Авторизация</title>
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
        .login-container {
            width: 400px;
            background-color: #FFFFFF;
            border: 1px solid #D1D1D3;
            border-radius: 30px;
            padding: 36px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .login-header {
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

        }

        .submit-button:hover {
            background: #FFC580;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-header">CRM SL</div>
    <form action="/login" method="post">
        <label class="label" for="username">Почта:</label>
        <input id="username" type="email" name="username" class="input" required>

        <label class="label" for="password">Пароль:</label>
        <input id="password" type="password" name="password" class="input" required>
        <br>
        <button type="submit" class="submit-button">Вход</button>
    </form>
</div>
</body>
</html>
