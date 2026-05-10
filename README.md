# SpectraStats

<p align="center">
  <img src="https://img.shields.io/badge/Frontend-React%20%2B%20Vite-61DAFB?style=for-the-badge&logo=react" alt="Frontend" />
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot" alt="Backend" />
  <img src="https://img.shields.io/badge/Language-Java%20%2F%20JavaScript-F7DF1E?style=for-the-badge&logo=java" alt="Language" />
</p>

<p align="center">
  <b>SpectraStats</b> — это веб-дашборд для просмотра игровой статистики игроков.
  Проект объединяет удобный поиск, страницы с общей и детальной статистикой,
  а также backend-часть, которая обслуживает API и работу с данными.
</p>

---

## О проекте

SpectraStats — это full-stack приложение, созданное для отображения статистики игроков в удобном и наглядном виде.

В проекте:
- пользователь вводит ник игрока и получает подсказки;
- можно перейти на страницу общего обзора статистики;
- можно открыть детальные категории статистики;
- backend отвечает за API и обработку данных;
- архитектура разделена на две логические части: `frontend` и `backend`.

---

## Возможности

- **Поиск игрока** с подсказками во время ввода.
- **Переход на страницу игрока** по нажатию Enter или выбору из списка.
- **Общая страница статистики** игрока.
- **Детальная статистика по типам**.
- **API для управления состоянием игрока**:
  - подключение;
  - отключение;
  - обновление статистики;
  - получение подсказок по нику.
- **Разделение логики** между frontend и backend.
- **Портфолио-friendly структура**, которая хорошо показывает навыки full-stack разработки.

---

## Стек

### Frontend
- React
- Vite
- React Router
- Axios
- use-debounce
- Lucide React
- modern-normalize

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Validation
- Caffeine
- PostgreSQL
- Lombok

---

## Архитектура

Проект построен как отдельные frontend и backend части:

- **Frontend** отвечает за интерфейс, навигацию и отображение статистики.
- **Backend** предоставляет API для получения подсказок, подключения/отключения игрока и обновления статистики.
- Между ними идёт обычное HTTP-взаимодействие через API.

### Основные маршруты
- `/` — главная страница поиска
- `/player/:player` — страница игрока
- `/player/:player/:type` — детальная статистика
- `/player/:player/notFound` — страница, если игрок не найден

---

## API

Backend использует API в стиле:

- `POST /api/player/connect/{player}`
- `POST /api/player/disconnect/{player}`
- `POST /api/player/statistic`
- `GET /api/player/suggest/{nick}`

---
