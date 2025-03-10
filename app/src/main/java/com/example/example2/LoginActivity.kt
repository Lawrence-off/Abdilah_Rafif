package com.example.example2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val dbHelper = DatabaseHelper(this)

        // 🔥 Cek apakah ada user di database
        if (!dbHelper.isUserExists()) {
            startActivity(Intent(this, Register::class.java))
            finish() // Tutup LoginActivity agar tidak bisa kembali ke sini
            return
        }

        val usernameEditText = findViewById<EditText>(R.id.editTextText)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val registerButton = findViewById<TextView>(R.id.buttonRegister)

        loginButton.setOnClickListener {

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        registerButton.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}
