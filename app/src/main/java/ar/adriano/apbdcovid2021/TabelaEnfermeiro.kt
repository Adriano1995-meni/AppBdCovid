package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaEnfermeiro(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE $NOME_TABELA " + "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_ENFERMEIRO TEXT NOT NULL," +
                    "$CONTACTO TEXT NOT NULL," +
                    "$DATA  DATA NOT NULL," +
                    "$MAIL  TEXT NOT NULL," +
                    "$SEXO  TEXT NOT NULL," +
                    "$MORADA TEXT NOT NULL," +
                    "$CAMPO_ID_DESTRITO INTEGER NOT NULL," +
                    "FOREIGN KEY($CAMPO_ID_DESTRITO) REFERENCES ${TabelaDestrito.NOME_TABELA})"
        )
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

        var posColNomeDestrito = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_DESTRITO) {
                posColNomeDestrito = i
                break
            }
        }

        if (posColNomeDestrito == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeDestrito) {
                "${TabelaDestrito.NOME_TABELA}.${TabelaDestrito.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_DESTRITO"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaDestrito.NOME_TABELA} ON ${TabelaDestrito.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_DESTRITO"
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
        const val NOME_TABELA = "Enfermeiro"
        const val NOME_ENFERMEIRO = "nome_Enfermeiro"
        const val CONTACTO = "CONTACTO"
        const val MAIL = "Mail"
        const val CAMPO_ID_DESTRITO = "idDestritos"
        const val SEXO = "Sexo"
        const val MORADA = "Morada"
        const val DATA = "data"
        const val  CAMPO_EXTERNO_NOME_DESTRITO = "EXTERNO_NO_ENFERMEIRO"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, NOME_ENFERMEIRO, CONTACTO, MAIL,CAMPO_ID_DESTRITO,
                DATA,
            SEXO,
            MORADA,
            CAMPO_EXTERNO_NOME_DESTRITO)



    }

}