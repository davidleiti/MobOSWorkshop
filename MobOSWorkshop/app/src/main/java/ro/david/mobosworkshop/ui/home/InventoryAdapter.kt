package ro.david.mobosworkshop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.david.mobosworkshop.R
import ro.david.mobosworkshop.model.InventoryItem

class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.InventoryItemViewHolder>() {

    private var items: List<InventoryItem> = listOf()

    fun update(newItems: List<InventoryItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryItemViewHolder =
        LayoutInflater.from(parent.context).inflate(
            R.layout.inventory_list_item,
            parent,
            false
        ).let { view -> InventoryItemViewHolder(view) }

    override fun onBindViewHolder(holder: InventoryItemViewHolder, position: Int) {
        holder.titleView.text = items[position].title
        holder.quantityView.text = items[position].quantity.toString()
    }

    inner class InventoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.item_title)
        val quantityView: TextView = view.findViewById(R.id.item_quantity)
    }
}