package ni.edu.uca.listadoprod.dataclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.listadoprod.R
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding

class ProductoAdapter(
    val listProd: List<Producto>,
    private val onClickListener: (Producto) -> Unit,
    private val onClickDelete: (Int) -> Unit,
    private val onClickUpdate: (Int) -> Unit
) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    inner class ProductoHolder(val binding: ItemlistaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun cargar(
            producto: Producto, onClickListener: (Producto) -> Unit,
            onClickDelete: (Int) -> Unit,
            onClickUpdate: (Int) -> Unit
        ) {
            with(binding) {
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = producto.precio.toString()
                itemView.setOnClickListener { onClickListener(producto) }
                binding.btnEliminar.setOnClickListener { onClickDelete(adapterPosition) }
                binding.btnEditar.setOnClickListener{onClickUpdate(adapterPosition)}
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position], onClickListener, onClickDelete, onClickUpdate)


    }


    override fun getItemCount(): Int = listProd.size


}








