package ar.adriano.apbdcovid2021

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaDestrito(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE " + NOME_TABELA + "(" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_NOME + " TEXT NOT NULL" +
                    ")"
        )
    }

    companion object {
        const val NOME_TABELA = "Destito"
        const val CAMPO_NOME = "nome_Destrito"
    }

}