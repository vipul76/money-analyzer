# money-analyzer
Personal money analyzing application

| Method | Endpoint             | Description                               |
| ------ | -------------------- | ----------------------------------------- |
| POST   | `/api/auth/register` | Register new user                         |
| POST   | `/api/auth/login`    | Authenticate user & return JWT token      |
| GET    | `/api/auth/me`       | Get current logged-in user info           |
| POST   | `/api/auth/logout`   | Logout (optional, for token invalidation) |


| Method | Endpoint             | Description                  |
| ------ | -------------------- | ---------------------------- |
| GET    | `/api/accounts`      | Get all accounts of the user |
| GET    | `/api/accounts/{id}` | Get single account by ID     |
| POST   | `/api/accounts`      | Create a new account         |
| PUT    | `/api/accounts/{id}` | Update account details       |
| DELETE | `/api/accounts/{id}` | Delete an account            |


| Method | Endpoint                   | Description                                |
| ------ | -------------------------- |--------------------------------------------|
| GET    | `/api/transactions`        | Get all transactions for+++++++++++++ user |
| GET    | `/api/transactions/{id}`   | Get a specific transaction                 |
| POST   | `/api/transactions`        | Create a new transaction                   |
| PUT    | `/api/transactions/{id}`   | Update a transaction                       |
| DELETE | `/api/transactions/{id}`   | Delete a transaction                       |
| GET    | `/api/transactions/filter` | Filter by date range (`?from=...&to=...`)  |


| Method | Endpoint               | Description                         |
| ------ | ---------------------- | ----------------------------------- |
| GET    | `/api/categories`      | List all categories (global + user) |
| GET    | `/api/categories/{id}` | Get category by ID                  |
| POST   | `/api/categories`      | Create new category                 |
| PUT    | `/api/categories/{id}` | Update category                     |
| DELETE | `/api/categories/{id}` | Delete category                     |


| Method | Endpoint            | Description                       |
| ------ | ------------------- | --------------------------------- |
| GET    | `/api/budgets`      | List all budgets for current user |
| GET    | `/api/budgets/{id}` | Get a specific budget             |
| POST   | `/api/budgets`      | Create a new budget               |
| PUT    | `/api/budgets/{id}` | Update budget                     |
| DELETE | `/api/budgets/{id}` | Delete budget                     |


| Method | Endpoint               | Description                        |
| ------ | ---------------------- | ---------------------------------- |
| GET    | `/api/recurrings`      | List all recurring transactions    |
| GET    | `/api/recurrings/{id}` | Get specific recurring transaction |
| POST   | `/api/recurrings`      | Create recurring transaction       |
| PUT    | `/api/recurrings/{id}` | Update recurring transaction       |
| DELETE | `/api/recurrings/{id}` | Delete recurring transaction       |


| Method | Endpoint                               | Description                        |
| ------ | -------------------------------------- | ---------------------------------- |
| GET    | `/api/analytics/summary`               | Get total income, expense, balance |
| GET    | `/api/analytics/monthly?month=YYYY-MM` | Monthly summary                    |
| GET    | `/api/analytics/by-category`           | Income/expense grouped by category |
| GET    | `/api/analytics/spending-trend`        | Time-series trend of spending      |

