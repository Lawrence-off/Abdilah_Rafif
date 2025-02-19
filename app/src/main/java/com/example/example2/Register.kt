package com.example.example2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val dbHelper = DatabaseHelper(this)
        val usernameInput = findViewById<EditText>(R.id.RegisTextUsername)
        val passwordInput = findViewById<EditText>(R.id.RegisTextPassword)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.insertUser(username, password)) {
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish() // Tutup activity register agar tidak bisa kembali
            } else {
                Toast.makeText(this, "Username sudah ada!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
