package com.example.a2025a1_integrador2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2025a1_integrador2.Adapters.LibrosAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var rvLibros: RecyclerView
    private lateinit var adapter: LibrosAdapter
    private lateinit var agregarLibroLauncher: ActivityResultLauncher<Intent>
    private var libros: MutableList<Libro> = mutableListOf() // Cambiado a local

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras

        Toast.makeText(this, "Bienvenido, ${bundle?.getString("user")}", Toast.LENGTH_SHORT).show()

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Configura la Toolbar como ActionBar

        setupAdapter()

        agregarLibroLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val nuevoLibro = result.data?.getSerializableExtra("nuevoLibro", Libro::class.java)
                if (nuevoLibro != null) {
                    LibroRepository.agregarLibro(nuevoLibro)
                    adapter.actualizarLista(LibroRepository)
                    Log.i("nuevoLibro", LibroRepository.obtenerLibros().last().nombre)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mb_agregar_libro -> {
                val intent: Intent = Intent(this, AgregarLibroActivity::class.java)
                agregarLibroLauncher.launch(intent)
            }
        else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setupAdapter() {
        rvLibros = findViewById(R.id.rvLibros)
        rvLibros.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false) // Los últimos dos argumentos son opcionales
        adapter = LibrosAdapter(LibroRepository.obtenerLibros().toMutableList()) { libroSeleccionado ->
            // Acción al hacer clic en un libro
            Toast.makeText(this, "Seleccionaste: ${libroSeleccionado.nombre}", Toast.LENGTH_SHORT).show()
        }
        rvLibros.adapter = adapter
    }

}