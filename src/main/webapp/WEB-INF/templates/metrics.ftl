<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Метрики</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Статистика выполнения методов (Метрики)</h2>
        <a href="/dashboard" class="btn btn-outline-secondary">На главную</a>
    </div>

    <!-- Форма для поиска метрики по имени -->
    <div class="card mb-4 shadow-sm">
        <div class="card-body">
            <form action="/metrics" method="get" class="d-flex align-items-end">
                <div class="me-3 flex-grow-1">
                    <label for="methodName" class="form-label">Поиск по имени метода:</label>
                    <input type="text" name="methodName" id="methodName" class="form-control"
                           placeholder="Например: ClassName.methodName"
                           value="${searchedMethod!''}">
                </div>
                <div class="me-2">
                    <button type="submit" class="btn btn-primary">Найти</button>
                </div>
                <div>
                    <a href="/metrics" class="btn btn-secondary">Показать все</a>
                </div>
            </form>
        </div>
    </div>

    <!-- Таблица с результатами -->
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <#if metrics?has_content>
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-dark">
                    <tr>
                        <th class="ps-4">Название метода</th>
                        <th>Успешные вызовы (Success)</th>
                        <th>Неуспешные вызовы (Failure)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list metrics as name, stats>
                        <tr>
                            <td class="ps-4"><code>${name}</code></td>
                            <td class="text-success fw-bold">${stats.successCount}</td>
                            <td class="text-danger fw-bold">${stats.failureCount}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            <#else>
                <div class="p-4 text-center text-muted">
                    <#if searchedMethod?? && searchedMethod != "">
                        Метрики для метода <strong>${searchedMethod}</strong> не найдены.
                    <#else>
                        Метрики пока не собраны.
                    </#if>
                </div>
            </#if>
        </div>
    </div>
</div>

</body>
</html>