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
        Log.i("vida", "onCreate")
        val bundle = intent.extras

        Toast.makeText(this, "Bienvenido, ${bundle?.getString("user")}", Toast.LENGTH_SHORT).show()

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Configura la Toolbar como ActionBar

        getLibros()
        setupAdapter()

        agregarLibroLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val nuevoLibro = result.data?.getSerializableExtra("nuevoLibro", Libro::class.java)
                if (nuevoLibro != null) {
                    libros.add(nuevoLibro)
                    setupAdapter()
                    adapter.notifyDataSetChanged() // Recarga toda la lista
                    Log.i("vida", "PASO")
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
                intent.putExtra("lastID", libros.last().id)
                agregarLibroLauncher.launch(intent)
            }
        else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun setupAdapter() {
        rvLibros = findViewById(R.id.rvLibros)
        rvLibros.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false) // Los últimos dos argumentos son opcionales
        adapter = LibrosAdapter(libros) { libroSeleccionado ->
            // Acción al hacer clic en un libro
            Toast.makeText(this, "Seleccionaste: ${libroSeleccionado.nombre}", Toast.LENGTH_SHORT).show()
        }
        rvLibros.adapter = adapter
    }

    private fun getLibros(): MutableList<Libro> {
        libros = mutableListOf(
            Libro(1, "Canción de hielo y fuego", "George R. R. Martin"),
            Libro(2, "Harry Potter y el cáliz de fuego", "J. K. Rowling"),
            Libro(3, "Los juegos del hambre en llamas", "Suzanne Collins"),
            Libro(4, "Maze runner", "James Dashner"),
            Libro(5, "El señor de los anillos", "J. R. R. Tolkien"),
            Libro(6, "1984", "George Orwell"),
            Libro(7, "Fahrenheit 451", "Ray Bradbury"),
            Libro(8, "Cien años de soledad", "Gabriel García Márquez"),
            Libro(9, "Don Quijote de la Mancha", "Miguel de Cervantes"),
            Libro(10, "Orgullo y prejuicio", "Jane Austen"),
            Libro(11, "Matar a un ruiseñor", "Harper Lee"),
            Libro(12, "Crimen y castigo", "Fiódor Dostoyevski"),
            Libro(13, "El gran Gatsby", "F. Scott Fitzgerald"),
            Libro(14, "La sombra del viento", "Carlos Ruiz Zafón"),
            Libro(15, "El alquimista", "Paulo Coelho"),
            Libro(16, "El código Da Vinci", "Dan Brown"),
            Libro(17, "La chica del tren", "Paula Hawkins"),
            Libro(18, "El psicoanalista", "John Katzenbach"),
            Libro(19, "El nombre de la rosa", "Umberto Eco"),
            Libro(20, "La catedral del mar", "Ildefonso Falcones"),
            Libro(21, "La ladrona de libros", "Markus Zusak"),
            Libro(22, "El invierno del mundo", "Ken Follett"),
            Libro(23, "La casa de los espíritus", "Isabel Allende"),
            Libro(24, "La isla del tesoro", "Robert Louis Stevenson"),
            Libro(25, "Mujer que mira al hombre que mira al hombre", "Ángeles Mastretta"),
            Libro(26, "La tregua", "Mario Benedetti"),
            Libro(27, "El túnel", "Ernesto Sabato"),
            Libro(28, "Vivir para contarla", "Gabriel García Márquez"),
            Libro(29, "El hombre en busca de sentido", "Viktor Frankl"),
            Libro(30, "Los pilares de la Tierra", "Ken Follett")
        )
        return libros
    }

    override fun onResume() {
        super.onResume()
        Log.i("vida", "onResume")
    }

}