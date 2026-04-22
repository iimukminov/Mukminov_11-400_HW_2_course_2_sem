<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Бенчмарк и перцентиль</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5 mb-5">
    <!-- Шапка страницы -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="mb-0">Бенчмарк</h2>
            <small class="text-muted">Анализ времени выполнения методов</small>
        </div>
        <a href="/dashboard" class="btn btn-outline-secondary">На главную</a>
    </div>

    <!-- Карточка с формой расчета перцентиля -->
    <div class="card mb-4 shadow-sm border-0">
        <div class="card-header bg-white pt-3 pb-2 border-bottom-0">
            <h5 class="mb-0 text-primary">Расчет перцентиля</h5>
        </div>
        <div class="card-body">
            <form action="/benchmarks" method="get" class="d-flex flex-wrap align-items-end gap-3">

                <div class="flex-grow-1" style="min-width: 300px;">
                    <label for="methodName" class="form-label fw-medium">Имя метода:</label>
                    <input class="form-control" list="methodOptions" id="methodName" name="methodName"
                           placeholder="Начните вводить имя метода..."
                           value="${selectedMethod!''}" required autocomplete="off">

                    <!-- Выпадающий список собирается прямо из ключей мапы -->
                    <datalist id="methodOptions">
                        <#if benchmarks?? && benchmarks?has_content>
                            <#list benchmarks?keys as method>
                                <option value="${method}"></option>
                            </#list>
                        </#if>
                    </datalist>
                </div>

                <div style="width: 150px;">
                    <label for="n" class="form-label fw-medium">Перцентиль (N):</label>
                    <div class="input-group">
                        <input type="number" name="n" id="n" class="form-control"
                               min="1" max="100" step="0.1" value="${(nValue!95)?c}" required>
                        <span class="input-group-text">%</span>
                    </div>
                </div>

                <div>
                    <button type="submit" class="btn btn-primary px-4">Рассчитать</button>
                </div>

                <#if selectedMethod?? && selectedMethod != "">
                    <div>
                        <a href="/benchmarks" class="btn btn-outline-secondary" title="Сбросить форму">&#x2715;</a>
                    </div>
                </#if>
            </form>
        </div>
    </div>

    <!-- Блок результатов расчета (появляется только после отправки формы) -->
    <#if percentileResult??>
        <div class="alert alert-success shadow-sm border-0 border-start border-5 border-success mb-4">
            <h5 class="alert-heading mb-2">Расчет успешно выполнен</h5>
            <p class="mb-0">
                Значение <strong>${(nValue!95)?c}-го перцентиля</strong> для метода <br>
                <code class="text-dark bg-white px-2 py-1 rounded border">${selectedMethod}</code><br>
                составляет: <span class="fs-4 fw-bold text-success ms-2">${percentileResult}</span>
            </p>
        </div>
    <#elseif errorMessage??>
        <div class="alert alert-danger shadow-sm border-0 border-start border-5 border-danger mb-4">
            <h5 class="alert-heading">Ошибка расчета</h5>
            <p class="mb-0">${errorMessage}</p>
        </div>
    </#if>

    <!-- Общая статистика времени выполнения -->
    <div class="card shadow-sm border-0">
        <div class="card-header bg-white pt-3 pb-2 border-bottom-0">
            <h5 class="mb-0 text-dark">Вся статистика времени выполнения</h5>
        </div>
        <div class="card-body p-0">
            <#if benchmarks?? && benchmarks?has_content>
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-dark">
                    <tr>
                        <th class="ps-4">Название метода</th>
                        <th>Кол-во вызовов</th>
                        <th>Зафиксированное время (мс)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list benchmarks as name, times>
                        <tr>
                            <td class="ps-4"><code>${name}</code></td>
                            <td><span class="badge bg-secondary">${times?size}</span></td>
                            <td>
                                <!-- times?join(", ") красиво склеивает список чисел в строку -->
                                <span class="text-muted">${times?join(", ")}</span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            <#else>
                <div class="p-4 text-center text-muted">
                    Статистика времени выполнения пока не собрана. Вызовите методы, помеченные аннотацией <code>@Benchmark</code>.
                </div>
            </#if>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>