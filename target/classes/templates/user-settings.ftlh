<h1>Настройки пользователей</h1>
<table>
    <tr>
        <th>Имя</th>
        <th>Email</th>
        <th>Процент менеджера</th>
        <th>Обновить</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>
                <!-- Отдельная форма для каждого пользователя -->
                <form action="/update-percentage" method="post">
                    <input type="number" name="percentage" value="${user.percentage}" min="0" max="100">
                    <input type="hidden" name="userId" value="${user.userId}">
            </td>
            <td>
                <button type="submit">Сохранить</button>
                </form>
            </td>
        </tr>
    </#list>
</table>
<form action="/statistics" method="get">
    <input type="submit" value="Статистика"/>
</form>
