package ni.edu.uca.listadoprod

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.dataclass.Producto
import ni.edu.uca.listadoprod.dataclass.ProductoAdapter
import java.lang.Exception
import java.text.ParsePosition


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ProductoAdapter
    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()

    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }

    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)

            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error: ${ex.toString()} ",
                    Toast.LENGTH_LONG
                ).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                { producto -> onItemSelected(producto) },
                { position -> onDeleteItem(position) },
                { position -> onUpdateItem(position) })
            limpiar()
        }


    }

    private fun onItemSelected(producto: Producto) {
        with(binding) {
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun onDeleteItem(position: Int) {
        try {

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Eliminar Producto")
            dialog.setMessage("¿Desea eliminar este Producto?")
            dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
                with(binding) {
                    listaProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                }

            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(this, "Eliminación Cancelada", Toast.LENGTH_SHORT).show()

            })
            dialog.show()
        } catch (ex: Exception) {
            Toast.makeText(
                this@MainActivity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    private fun onUpdateItem(position: Int) {
        try {
            with(binding) {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.set(position, prod)

                rcvLista.adapter?.notifyItemChanged(position)
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@MainActivity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}