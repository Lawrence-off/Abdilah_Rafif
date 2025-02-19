package com.example.example2

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "UserDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE, " +
                    "password TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    // Fungsi untuk hashing password dengan SHA-256
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    // Fungsi untuk menambahkan pengguna baru
    fun insertUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username=?", arrayOf(username))

        if (cursor.count > 0) {
            cursor.close()
            db.close()
            return false // Username sudah ada
        }
        cursor.close()

        val contentValues = ContentValues().apply {
            put("username", username)
            put("password", hashPassword(password)) // Simpan password yang sudah di-hash
        }

        val result = db.insert("users", null, contentValues)
        db.close()
        return result != -1L
    }

    // Fungsi untuk mengecek apakah user ada dengan password yang benar
    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val hashedPassword = hashPassword(password)
        val cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, hashedPassword))

        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Mengecek apakah ada user di database (misalnya untuk cek apakah admin sudah ada)
    fun isUserExists(): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)

        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
}
