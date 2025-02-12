package com.example.example2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Pindahkan ke baris sendiri

        val nama = findViewById<EditText>(R.id.editTextText)
        val pass = findViewById<EditText>(R.id.editTextTextPassword)
        val buttonClick = findViewById<Button>(R.id.buttonLogin)

        buttonClick.setOnClickListener {
            if (nama.text.toString() == "user" && pass.text.toString() == "123") {
                Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Menutup LoginActivity setelah login berhasil
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
