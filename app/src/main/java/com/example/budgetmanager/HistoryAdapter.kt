package com.example.budgetmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.database.transaction.Transaction

class HistoryAdapter(
    private val pastTransactions: List<Transaction>,
    private val onLongPress: (Transaction) -> Unit,
    private val onDelete: (Transaction) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return HistoryViewHolder(layout, onLongPress, onDelete)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val pastTransaction = pastTransactions[position]
        holder.bind(pastTransaction)
    }

    override fun getItemCount(): Int = pastTransactions.size

    class HistoryViewHolder(
        layout: View,
        private val onLongPress: (Transaction) -> Unit,
        private val onDelete: (Transaction) -> Unit
    ) : RecyclerView.ViewHolder(layout) {
        private val categoryName: TextView = layout.findViewById(R.id.categ_name)
        private val date: TextView = layout.findViewById(R.id.date_val)
        private val time: TextView = layout.findViewById(R.id.time_val)
        private val amount: TextView = layout.findViewById(R.id.amount_val)
        private val comment: TextView = layout.findViewById(R.id.comment)
        private val deleteButton: ImageButton = layout.findViewById(R.id.delete_button)

        fun bind(transaction: Transaction) {
            categoryName.text = transaction.category
            amount.text = transaction.amount
            date.text = transaction.date
            time.text = transaction.time
            comment.text = transaction.comment

            itemView.setOnLongClickListener {
                onLongPress(transaction)
                true
            }

            deleteButton.setOnClickListener {
                onDelete(transaction)
            }
        }
    }
}
