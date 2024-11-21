package com.example.budgetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.database.DatabaseViewModel
import com.example.budgetmanager.database.transaction.Transaction
import com.example.budgetmanager.databinding.EditTransactionDialogBinding
import com.example.budgetmanager.databinding.FourthFragmentBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FourthFragmentBinding
    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseViewModel.initial(requireNotNull(this.activity).application)
        binding = DataBindingUtil.inflate(inflater, R.layout.fourth_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseViewModel.allTransactions.observe(viewLifecycleOwner) {
            val recyclerView = binding.history
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = HistoryAdapter(it, { transaction ->
                showEditTransactionDialog(transaction)
            }, { transaction ->
                showDeleteConfirmationDialog(transaction)
            })
        }
    }

    private fun showEditTransactionDialog(transaction: Transaction) {
        val dialogBinding = EditTransactionDialogBinding.inflate(LayoutInflater.from(context))
        dialogBinding.amountEditText.setText(transaction.amount)
        dialogBinding.categoryEditText.setText(transaction.category)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Transaction")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                val newAmount = dialogBinding.amountEditText.text.toString()
                val newCategory = dialogBinding.categoryEditText.text.toString()

                if (newAmount.isEmpty() || newCategory.isEmpty()) {
                    Toast.makeText(context, "Please enter valid values", Toast.LENGTH_SHORT).show()
                } else {
                    val updatedTransaction = transaction.copy(
                        amount = newAmount,
                        category = newCategory
                    )
                    databaseViewModel.updateTransaction(updatedTransaction)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog(transaction: Transaction) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction?")
            .setPositiveButton("Delete") { _, _ ->
                databaseViewModel.deleteTransaction(transaction)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
