package com.example.a2025a1_integrador2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var et_user: EditText
    private lateinit var et_pass: EditText
    private lateinit var cb_recordarUsuario: CheckBox
    private lateinit var bt_crearUsuario: Button
    private lateinit var bt_iniciarSesion: Button
    private lateinit var tv_terminosYCondiciones: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        et_user = findViewById(R.id.et_user)
        et_pass = findViewById(R.id.et_pass)
        cb_recordarUsuario = findViewById(R.id.cb_recordarUsuario)
        bt_crearUsuario = findViewById(R.id.bt_crearUsuario)
        bt_crearUsuario.setOnClickListener {
            "TODO"
        }
        bt_iniciarSesion = findViewById(R.id.bt_iniciarSesion)
        bt_iniciarSesion.setOnClickListener {
            val intent: Intent = Intent(this,HomeActivity::class.java)
            if(!et_user.text.isNullOrEmpty()) {
                intent.putExtra("user", et_user.text.toString())
                intent.putExtra("pass", et_pass.text.toString())
                startActivity(intent)
                finish()
            }
            else Toast.makeText(this, "COMPLETAR DATOS!", Toast.LENGTH_SHORT).show()
        }

        tv_terminosYCondiciones = findViewById(R.id.tv_terminosYCondiciones)
        tv_terminosYCondiciones.setOnClickListener {
            val intent: Intent = Intent(this,TermsAndConditionsActivity::class.java)
            startActivity(intent)
        }


    }
}