package cg.tutorials.practiselistview

import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cg.tutorials.practiselistview.databinding.ActivityMainBinding
import cg.tutorials.practiselistview.databinding.CustomDialogBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityMainBinding
        val array = mutableListOf<String>("hii","hello")
       lateinit var arrayAdapter:ArrayAdapter<String>
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        arrayAdapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        binding.listView.adapter = arrayAdapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this@MainActivity, "item selected $position", Toast.LENGTH_SHORT).show()
            array.set(position,"updated array")
            arrayAdapter.notifyDataSetChanged()
        }
        binding.listView.setOnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this).apply {
                setTitle("HELLO!!")
                setMessage("Are you sure to delete this item")
                setPositiveButton("Yes") { _, _ ->
                    array.removeAt(position)
                    arrayAdapter.notifyDataSetChanged()
                }
                setNegativeButton("No"){ _ ,_ ->
                }
                show()
            }
            return@setOnItemLongClickListener true
        }
        binding.floatingBtn.setOnClickListener{
            val dailogBinding = CustomDialogBinding.inflate(layoutInflater)
            val dialog = Dialog(this).apply{
                setContentView(dailogBinding.root)
                setCancelable(true)
                window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT)
                show()
            }
            dailogBinding.addBtn.setOnClickListener{
                if (dailogBinding.etDialog.text.trim().toString().isEmpty()){
                    dailogBinding.etDialog.error="Enter value"
                }
                else{
                    array.add(dailogBinding.etDialog.text.trim().toString())
                    arrayAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dailogBinding.cancelBtn.setOnClickListener{
                dialog.dismiss()
            }
        }
    }
}