package com.industri.fintechbilling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter RecyclerView untuk menampilkan daftar barang faktur transaksi.
 */
class InvoiceItemAdapter(
    private val itemList: List<InvoiceItem>
) : RecyclerView.Adapter<InvoiceItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvItemId: TextView = itemView.findViewById(R.id.tvItemId)
        val tvItemQtyPrice: TextView = itemView.findViewById(R.id.tvItemQtyPrice)
        val tvItemSubtotal: TextView = itemView.findViewById(R.id.tvItemSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_invoice_product, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvItemName.text = item.itemName
        holder.tvItemId.text = "Kode: ${item.itemId}"
        val formattedUnitPrice = CurrencyFormatter.formatRupiah(item.unitPrice)
        holder.tvItemQtyPrice.text = "${item.qty} x $formattedUnitPrice"
        holder.tvItemSubtotal.text = CurrencyFormatter.formatRupiah(item.subtotal)
    }

    override fun getItemCount(): Int = itemList.size
}
