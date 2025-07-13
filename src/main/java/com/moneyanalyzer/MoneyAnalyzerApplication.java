package com.moneyanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyAnalyzerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoneyAnalyzerApplication.class, args);
    }
}
/*
/ --- Basic API URL Overview ---
// Authentication
// POST    /api/auth/register
// POST    /api/auth/login
// POST    /api/auth/logout
// GET     /api/auth/me

// Account APIs
// GET     /api/accounts
// POST    /api/accounts
// GET     /api/accounts/{id}
// PUT     /api/accounts/{id}
// DELETE  /api/accounts/{id}

// Transaction APIs
// GET     /api/transactions
// POST    /api/transactions
// GET     /api/transactions/{id}
// PUT     /api/transactions/{id}
// DELETE  /api/transactions/{id}

// Category APIs
// GET     /api/categories
// POST    /api/categories
// GET     /api/categories/{id}
// PUT     /api/categories/{id}
// DELETE  /api/categories/{id}

// Analytics APIs
// GET     /api/analytics/summary
// GET     /api/analytics/monthly?month=YYYY-MM
// GET     /api/analytics/by-category
// GET     /api/analytics/spending-trend

// Budget APIs (optional)
// GET     /api/budgets
// POST    /api/budgets
// PUT     /api/budgets/{id}
// DELETE  /api/budgets/{id}

// Recurring Transaction APIs (optional)
// GET     /api/recurrings
// POST    /api/recurrings
// PUT     /api/recurrings/{id}
// DELETE  /api/recurrings/{id}

 */