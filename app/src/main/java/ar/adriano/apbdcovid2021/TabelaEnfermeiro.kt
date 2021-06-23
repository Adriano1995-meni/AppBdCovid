package ar.adriano.apbdcovid2021

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaEnfermeiro(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE $NOME_TABELA " + "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_ENFERMEIRO TEXT NOT NULL," +
                    "$CONTACTO TEXT NOT NULL," +
                    "$DATA," +
                    "$MAIL," +
                    "$SEXO," +
                    "$MORADA TEXT NOT NULL," +
                    "$CAMPO_ID_DESTRITO INTEGER NOT NULL," +
                    "FOREIGN KEY($CAMPO_ID_DESTRITO) REFERENCES ${TabelaDestrito.NOME_TABELA})"
        )
    }


    companion object {
        const val NOME_TABELA = "Enfermeiro"
        const val NOME_ENFERMEIRO = "nome_Enfermeiro"
        const val CONTACTO = "CONTACTO"
        const val MAIL = "Mail"
        const val CAMPO_ID_DESTRITO = "idDestritos"
        const val SEXO = "Sexo"
        const val MORADA = "Morada"
        const val DATA = "data"




    }

}