package com.example.phinmakatanungan_mobile.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(private val context: Context) : SQLiteOpenHelper(context, "tokenHolder", null, 2) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create tables
        db.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TOKEN")
        // Recreate tables
        onCreate(db)
    }

    fun insertToken(token: String): Boolean {
        return try {
            val db = this.writableDatabase
            val values = ContentValues().apply {
                put("currentToken", token)
            }
            val insertionResult = db.insert(TABLE_TOKEN, null, values)
            db.close()
            insertionResult != -1L
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "token.db"
        private const val TABLE_TOKEN = "current_token"
        private const val CREATE_TABLE_USERS = "CREATE TABLE $TABLE_TOKEN (id INTEGER PRIMARY KEY, currentToken TEXT)"
    }
}
