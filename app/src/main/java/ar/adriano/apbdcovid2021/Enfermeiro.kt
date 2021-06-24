package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Enfermeiro (
    var id: Long = -1,
    var nome: String,
    val Morada:String,
    var contacto: String,
    var sexo:String,
    var data: Date,
    var idDestrito: Long,
    var Mail: String,
    var nomeCategoria: String? = null


)  {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaEnfermeiro.NOME_ENFERMEIRO, nome)
            put(TabelaEnfermeiro.CONTACTO, contacto)
            put(TabelaEnfermeiro.MORADA,Morada)
            put(TabelaEnfermeiro.CAMPO_ID_DESTRITO, idDestrito)
            put(TabelaEnfermeiro.DATA,data.time)
            put(TabelaEnfermeiro.SEXO, sexo)
            put(TabelaEnfermeiro.MAIL,Mail)




        }

        return valores
    }



    companion object {
        fun fromCursor(cursor: Cursor): Enfermeiro {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colTitulo = cursor.getColumnIndex(TabelaEnfermeiro.NOME_ENFERMEIRO)
            val  C_Morada = cursor.getColumnIndex(TabelaEnfermeiro.MORADA)
            val colCont = cursor.getColumnIndex(TabelaEnfermeiro.CONTACTO)
            val colIdCateg = cursor.getColumnIndex(TabelaEnfermeiro.CAMPO_ID_DESTRITO)
            val SEXO = cursor.getColumnIndex(TabelaEnfermeiro.SEXO)
            val DataHoje = cursor.getColumnIndex(TabelaEnfermeiro.DATA)
            val mail = cursor.getColumnIndex(TabelaEnfermeiro.MAIL)
            val colNomeCateg = cursor.getColumnIndex(TabelaEnfermeiro.CAMPO_EXTERNO_NOME_DESTRITO)




            val id = cursor.getLong(colId)
            val nome = cursor.getString(colTitulo)
            val CM_Morada = cursor.getString( C_Morada)
            val contacto = cursor.getString(colCont)
            val idCateg = cursor.getLong(colIdCateg)
            val SexoEnf = cursor.getString(SEXO)
            val Data = cursor.getLong(DataHoje)
            val C_Mail = cursor.getString(mail)
            val nomeCategoria = if (colNomeCateg != -1) cursor.getString(colNomeCateg) else null
            return Enfermeiro(id, nome, CM_Morada,contacto, SexoEnf, Date(Data),idCateg,C_Mail, nomeCategoria)
        }
    }
}