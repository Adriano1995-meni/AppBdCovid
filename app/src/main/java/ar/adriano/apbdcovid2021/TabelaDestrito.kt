package ar.adriano.apbdcovid2021

import android.database.sqlite.SQLiteDatabase

class TabelaDestrito(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE destritos(_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)")
    }
}
