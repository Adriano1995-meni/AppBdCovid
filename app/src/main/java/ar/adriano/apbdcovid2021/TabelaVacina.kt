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
                    "$NOME_FABRICANTE TEXT NOT NULL," +
                  //  "$DATA_DO_SEGUNDA_DOCE DATE NOT NULL," +
                  // "$DATA_VALIDACAO DATE NOT NULL," +
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
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        val ultimaColuna = columns.size - 1
        var posColPessoas = -1
        var posColNomeDestrito = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_DESTRITO) {
                posColNomeDestrito = i


            } else if (columns[i] == CAMPO_EXTERNO_NOME_PESSOAS) {
                posColPessoas = i
            }
        }

        if (posColNomeDestrito == -1 && posColPessoas==-1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeDestrito) {
                "${TabelaDestrito.NOME_TABELA}.${TabelaDestrito.CAMPO_NOME} AS ${CAMPO_EXTERNO_NOME_DESTRITO}"
            } else if (i == posColPessoas) {
                "${TabelaPessoas.NOME_TABELA}.${TabelaPessoas.NOME_PESSOA} AS ${CAMPO_EXTERNO_NOME_PESSOAS}"
            }
            else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "${NOME_TABELA} INNER JOIN ${TabelaDestrito.NOME_TABELA} ON ${TabelaDestrito.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_ID_DESTRITO}" +
                " INNER JOIN ${TabelaPessoas.NOME_TABELA} ON ${TabelaPessoas.NOME_TABELA}.${BaseColumns._ID}=${IDPaciente}"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }




    companion object {
        const val NOME_TABELA = "Vacina"
        const val NOME_VACINA = "nome_Vacina"

     //   const val DATA_VALIDACAO ="Data de Validaçao"
        const val NOME_FABRICANTE = "nome_frabricante"
        const val CAMPO_ID_DESTRITO = "idDestritos"
        const val IDPaciente = "IDPacientes"
        const val CAMPO_EXTERNO_NOME_DESTRITO= "campo_externo_destrito"
        const val CAMPO_EXTERNO_NOME_PESSOAS= "campo_externo_pessoas"


        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_VACINA,

             //   DATA_VALIDACAO,
            NOME_FABRICANTE,
                IDPaciente,
               CAMPO_ID_DESTRITO,
            CAMPO_EXTERNO_NOME_PESSOAS,
           CAMPO_EXTERNO_NOME_DESTRITO
        )

    }


}