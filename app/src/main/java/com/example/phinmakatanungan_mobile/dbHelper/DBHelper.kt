package com.example.phinmakatanungan_mobile.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "tokens_database"
        const val TABLE_NAME = "TOKENS"
        const val COLUMN_ID = "id INTEGER PRIMARY KEY"
        const val COLUMN_ACTIVE_TOKEN = "ACTIVE_TOKEN"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID, $COLUMN_ACTIVE_TOKEN TEXT)"
        db.execSQL(createTableQuery)
        val contentValues = ContentValues().apply {
            put("id", 1)
            put(COLUMN_ACTIVE_TOKEN, null as String?)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addOrUpdateToken(activeToken: String) {
        val CURRENT_ID = "id"
        val db = this.writableDatabase
        try {
            val values = ContentValues().apply {
                put(COLUMN_ACTIVE_TOKEN, activeToken)
            }
            db.update(TABLE_NAME, values, "$CURRENT_ID = ?", arrayOf("1"))
        } finally {
            db.close()
        }
    }
    fun deleteAllTokens() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}