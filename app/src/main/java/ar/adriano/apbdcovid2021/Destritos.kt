package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Destritos (var id: Long = -1, var nome: String) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaDestrito.CAMPO_NOME, nome)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Destritos {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaDestrito.CAMPO_NOME)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)

            return Destritos(id, nome)
        }
    }

}