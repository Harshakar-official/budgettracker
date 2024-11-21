package com.example.budgetmanager.database

import com.example.budgetmanager.database.budget.Budget
import com.example.budgetmanager.database.budget.BudgetDao
import com.example.budgetmanager.database.transaction.Transaction
import com.example.budgetmanager.database.transaction.TransactionDao

class DataBaseRepository(private val transactionDao: TransactionDao, private val budgetDao: BudgetDao) {
    val allTransactions = transactionDao.getAllTransactions()
    val budget = budgetDao.getAllBudgets()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun clear() {
        transactionDao.clear()
    }

    suspend fun insertBudget(budget: Budget) {
        budgetDao.addBudget(budget)
    }

    suspend fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget)
    }

    suspend fun updateBudget(budget: Budget) {
        budgetDao.updateBudget(budget)
    }
}
