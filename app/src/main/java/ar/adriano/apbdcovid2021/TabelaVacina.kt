package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaVacina (db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE $NOME_TABELA(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_VACINA TEXT NOT NULL," +
                    "$DATA_DO_SEGUNDA_DOCE DATE NOT NULL," +
                    "$DATA_VALIDACAO DATE NOT NULL," +
                    "$CAMPO_ID_DESTRITO INTEGER NOT NULL," +
                    "$IDPaciente INTEGER NOT NULL," +
                    "FOREIGN KEY($CAMPO_ID_DESTRITO) REFERENCES ${TabelaDestrito.NOME_TABELA}," +
                    "FOREIGN KEY($IDPaciente) REFERENCES ${TabelaPessoas.NOME_TABELA})")


    }


    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }



    companion object {
        const val NOME_TABELA = "Vacina"
        const val NOME_VACINA = "nome_Vacina"
        const val DATA_DO_SEGUNDA_DOCE = "DATA_DO_TESTE"
        const val DATA_VALIDACAO ="Data de Validaçao"
        const val CAMPO_ID_DESTRITO = "idDestrito"
        const val IDPaciente = "IDPaciente"
    }


}