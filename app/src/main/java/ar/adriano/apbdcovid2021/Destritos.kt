package ar.adriano.apbdcovid2021

import android.content.ContentValues

data class Destritos (var id: Long = -1, var nome: String) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaDestrito.CAMPO_NOME, nome)
        return valores
    }
}