package com.example.todotaskspruebafinaltd.repo

import android.content.ContentValues
import android.content.Context
import com.example.todotaskspruebafinaltd.db.UserDatabaseHelper
import com.example.todotaskspruebafinaltd.model.User

class UserRepository(context: Context) {

    private val dbHelper = UserDatabaseHelper(context)

    fun insert(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UserDatabaseHelper.COLUMN_USERNAME, user.username)
        }
        val id = db.insert(UserDatabaseHelper.TABLE_USER, null, values)
        user.id = id.toInt() // Set the id of the user object
        return id
    }

    fun delete(user: User) {
        val db = dbHelper.writableDatabase
        db.delete(UserDatabaseHelper.TABLE_USER, "${UserDatabaseHelper.COLUMN_ID} = ?", arrayOf(user.id.toString()))
    }

    fun getAllUsers(): List<User> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(UserDatabaseHelper.TABLE_USER, null, null, null, null, null, null)
        val users = mutableListOf<User>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_ID))
                val username = getString(getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USERNAME))
                val user = User(id, username)
                users.add(user)
            }
        }
        cursor.close()
        return users
    }

}