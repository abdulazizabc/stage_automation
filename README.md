# aqa-practice

UI-автотесты для staging-приложения [MGKeld](https://stage.app.mgkeld.com) (логин, водители, трейлеры).

Стек: **Java 17**, **Selenium 4**, **JUnit 5**, **Maven**, **WebDriverManager**.

---

## Требования

| Что нужно | Зачем |
|-----------|--------|
| JDK 17+ | компиляция (`pom.xml` → `maven.compiler.release=17`) |
| Maven 3.8+ | сборка и запуск тестов |
| Google Chrome | браузер для тестов |
| IntelliJ IDEA (рекомендуется) | удобный запуск тестов |

> В логах может быть предупреждение **CDP 148** — это несовпадение версии Chrome и Selenium DevTools. На прохождение тестов обычно **не влияет**.

---

## Быстрый старт

### 1. Клонировать и открыть проект

```bash
git clone git@github.com:abdulazizabc/stage_automation.git
cd aqa-practice
```

### 2. Настроить конфиг

Скопируй шаблон и укажи свои данные (если нужно):

```text
src/test/resources/config.example.properties  →  config.local.properties
```

Файл `config.local.properties` в `.gitignore` — в репозиторий не попадёт.

Либо отредактируй `src/test/resources/config.properties` (уже есть рабочие значения для stage).

### 3. Запустить тесты

**Все тесты:**

```bash
mvn test
```

**Один класс (пример):**

```bash
mvn test -Dtest=CreateTrailerTest
```

**В IntelliJ IDEA:** правый клик по классу/методу → *Run*.

---

## Структура проекта

```text
aqa-practice/
├── pom.xml                          # зависимости и плагины Maven
├── README.md                        # эта документация
│
└── src/test/
    ├── resources/
    │   ├── config.properties        # основной конфиг (URL, логин, timeout)
    │   ├── config.example.properties
    │   └── config.local.properties  # опционально, только локально (в .gitignore)
    │
    └── java/
        ├── base/                    # база для всех тестов
        ├── helpers/                 # утилиты (конфиг, логин, тестовые данные)
        ├── pages/                   # Page Object — работа с UI
        │   └── components/          # переиспользуемые части страниц (сайдбар)
        └── tests/                   # сами тесты (сценарии и assert)
            ├── login/
            ├── drivers/
            └── trailers/
```

Папку `src/main/resources/archetype-resources/` **не трогай** — это остаток Maven archetype, к тестам не относится.

---

## Архитектура (как слои связаны)

```text
┌─────────────────────────────────────────────────────────┐
│  tests.*          — что проверяем (JUnit + assert)     │
├─────────────────────────────────────────────────────────┤
│  pages.*          — как взаимодействуем с UI             │
├─────────────────────────────────────────────────────────┤
│  helpers.*        — конфиг, логин, генерация данных      │
├─────────────────────────────────────────────────────────┤
│  base.*           — WebDriver, wait, общие клики/ввод    │
└─────────────────────────────────────────────────────────┘
```

**Правило:** тест **не** содержит `driver.findElement(...)` — только вызовы Page Object и `assert*`.

---

## Пакеты и классы — что где

### `base` — фундамент

| Класс | Назначение |
|-------|------------|
| `BaseTest` | Запуск/остановка Chrome, `WebDriver`, `WebDriverWait` |
| `AuthenticatedTest` | Наследует `BaseTest` + автологин перед каждым тестом |
| `BasePage` | Общие методы: `find`, `click`, `type`, `clearAndType`, dropdown, ожидания |

**Когда наследовать:**

| Сценарий | Базовый класс |
|----------|----------------|
| Тесты страницы логина без авторизации | `BaseTest` |
| Все остальные UI-тесты после входа | `AuthenticatedTest` |

### `helpers` — вспомогательное

| Класс | Назначение |
|-------|------------|
| `ConfigReader` | Читает `config.properties` (+ опционально `config.local.properties`) |
| `LoginHelper` | Логин через `LoginPage` и креды из конфига |
| `TestDataGenerator` | Уникальные unit, VIN, username, license |

### `pages` — Page Object

| Класс | Страница / зона |
|-------|-----------------|
| `LoginPage` | Форма логина, валидации, ссылки |
| `HeaderPage` | Меню профиля, logout |
| `DriverPage` | Список и форма водителя |
| `TrailerPage` | Список и форма трейлера |
| `SidebarComponent` | Навигация: Drivers, Trailers |

Локаторы хранятся в Page Object как константы, например:

```java
public static final By SEARCH_INPUT = By.cssSelector("input[placeholder='Search by Unit#']");
```

### `tests` — тестовые сценарии

| Пакет | Класс | Что проверяет |
|-------|-------|----------------|
| `tests.login` | `LoginPageTest` | UI логина (8 тестов) |
| `tests.login` | `LogoutTest` | Выход из системы |
| `tests.drivers` | `CreateDriverTest` | Создание водителя |
| `tests.trailers` | `CreateTrailerTest` | Создание трейлера + поля в edit |
| `tests.trailers` | `EditTrailerTest` | Редактирование трейлера |

**Всего: 12 тестов.**

---

## Конфигурация

Файл: `src/test/resources/config.properties`

| Ключ | Пример | Описание |
|------|--------|----------|
| `base.url` | `https://stage.app.mgkeld.com` | хост приложения |
| `login.path` | `/#/login` | путь страницы логина |
| `dashboard.path` | `/#/dashboard` | URL после успешного входа |
| `test.username` | `account_admin_amazon` | логин для тестов |
| `test.password` | `***` | пароль для тестов |
| `wait.seconds` | `20` | таймаут явных ожиданий (WebDriverWait) |

Чтение в коде:

```java
ConfigReader.getLoginUrl();
ConfigReader.getUsername();
ConfigReader.getWaitSeconds();
```

---

## Куда писать новый код

### Новый тест (с авторизацией)

1. Создай класс в `src/test/java/tests/<модуль>/`, например `tests.drivers`.
2. Наследуй `AuthenticatedTest`.
3. В `@BeforeEach` создай нужные Page Object.
4. В `@Test` — только шаги сценария и `assert*`.

```java
public class MyTest extends AuthenticatedTest {

    private DriverPage driverPage;

    @BeforeEach
    void initPage() {
        driverPage = new DriverPage(driver);
    }

    @Test
    void myScenario() {
        driverPage.openDriversPage();
        // ...
        assertTrue(driverPage.isDriverVisibleInGrid("expected"));
    }
}
```

### Новый тест без логина (например, публичная страница)

Наследуй `BaseTest`, открой страницу через `LoginPage` или свой Page Object.

### Новая страница / форма

1. Создай класс в `pages/`, наследуй `BasePage`.
2. Вынеси локаторы в `public static final By ...`.
3. Методы — глаголы: `open...`, `enter...`, `click...`, `is...Visible`.
4. Dropdown — через `selectDropdownByDataValue` или `selectListOptionByText` из `BasePage` (всегда внутри `ul[@role='listbox']`).

### Общая навигация (сайдбар, хедер)

Добавляй в `SidebarComponent` или `HeaderPage`, не дублируй в каждом Page Object.

### Новые тестовые данные

Добавь метод в `TestDataGenerator`, не хардкодь случайные строки в тесте.

### Новый URL или креды

Только в `config.properties` / `config.local.properties`, не в тестах.

---

## Соглашения (чтобы не ломать стабильность)

1. **Не** используй `Thread.sleep` — только `WebDriverWait` / методы `BasePage`.
2. **Не** кликай `//li[@data-value='...']` без привязки к открытому `listbox`.
3. После create/save жди **закрытия формы** (см. `waitForDriverFormClosed`, `waitForTrailerFormClosed`).
4. Поиск в таблице — через `searchDriver` / `searchTrailer` (с `clearAndType` и Enter).
5. Имена водителей, unit, username — **уникальные** (`TestDataGenerator` или timestamp).
6. Пакеты — lowercase: `base`, `tests.login`, `pages.components`.

---

## Запуск и отладка

| Задача | Команда / действие |
|--------|---------------------|
| Все тесты | `mvn test` |
| Один класс | `mvn test -Dtest=LoginPageTest` |
| Один метод | `mvn test -Dtest=LoginPageTest#successfulLoginRedirectsToDashboard` |
| Падение по таймауту | увеличь `wait.seconds` в конфиге |
| Падение на локаторе | проверь элемент в DevTools, обнови константу в Page Object |

Логи SLF4J (`No SLF4J providers`) — информационное, на тесты не влияет.

---

## Зависимости (кратко)

| Библиотека | Версия | Роль |
|------------|--------|------|
| selenium-java | 4.31.0 | UI-автоматизация |
| junit-jupiter | 5.12.2 | тестовый фреймворк |
| webdrivermanager | 6.1.0 | автозагрузка ChromeDriver |

---

## Чеклист перед коммитом

- [ ] Тесты зелёные локально (`mvn test` или Run в IDEA)
- [ ] Секреты не в коде — только в `config.properties` / `config.local.properties`
- [ ] `config.local.properties` не добавлен в git
- [ ] Новые локаторы — в Page Object, не в тесте
- [ ] Нет лишних `sleep` и хардкода URL

---

## Полезные ссылки

- [Selenium documentation](https://www.selenium.dev/documentation/)
- [JUnit 5 user guide](https://junit.org/junit5/docs/current/user-guide/)
- Stage: `https://stage.app.mgkeld.com`
