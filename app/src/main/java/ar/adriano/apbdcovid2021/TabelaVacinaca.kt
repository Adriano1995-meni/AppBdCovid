package ar.adriano.apbdcovid2021

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaVacinaca (db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE $NOME_TABLE(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_VACINA TEXT NOT NULL," +
                    "$DATA_DO_SEGUNDA_DOCE DATE NOT NULL," +
                    "$DATA_VALIDACAO DATE NOT NULL," +
                    "$CAMPO_ID_DESTRITO INTEGER NOT NULL," +
                    "$IDPaciente INTEGER NOT NULL," +
                    "FOREIGN KEY($CAMPO_ID_DESTRITO) REFERENCES ${TabelaDestrito.NOME_TABELA}," +
                    "FOREIGN KEY($IDPaciente) REFERENCES ${TabelaPessoas.NOME_TABELA})"
        )
    }


    companion object {
        const val NOME_TABLE = "Vacina"
        const val NOME_VACINA = "nome_Vacina"
        const val DATA_DO_SEGUNDA_DOCE = "DATA_DO_TESTE"
        const val DATA_VALIDACAO ="Data de Validaçao"
        const val CAMPO_ID_DESTRITO = "idDestrito"
        const val IDPaciente = "IDPaciente"
    }


}