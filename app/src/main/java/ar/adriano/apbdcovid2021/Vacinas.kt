package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

 data class Vacinas (
    var id: Long=-1,
    var nome:String,
    var data_da_Proxima_Doce: Date,
   // ,var data_Validade:Date,
    var idPaciente: Long,
   var idDestrito: Long
){

    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaVacina.NOME_VACINA,nome)
       valores.put(TabelaVacina.DATA_DO_SEGUNDA_DOCE,data_da_Proxima_Doce.time)
        valores.put(TabelaVacina.IDPaciente,idPaciente)
      //  valores.put(TabelaVacina.DATA_VALIDACAO,data_Validade.time)
        valores.put(TabelaVacina.CAMPO_ID_DESTRITO,idDestrito)



        return valores

    }

    companion object {
        fun fromCursor(cursor: Cursor): Vacinas {
            val CorId = cursor.getColumnIndex(BaseColumns._ID)
            val CorNome = cursor.getColumnIndex(TabelaVacina.NOME_VACINA)
          val  CorTest = cursor.getColumnIndex(TabelaVacina.DATA_DO_SEGUNDA_DOCE)
           // val  CorDataValidade = cursor.getColumnIndex(TabelaVacina.DATA_VALIDACAO)
           val  CampIdPaciente = cursor.getColumnIndex(TabelaVacina.IDPaciente)
           val CampoIdCidade = cursor.getColumnIndex(TabelaVacina.CAMPO_ID_DESTRITO)


            val id = cursor.getLong(CorId)
            val Nome = cursor.getString(CorNome)
            val Teste = cursor.getLong(CorTest)
            val id_Pacinete = cursor.getLong(CampIdPaciente)
           // var CM_DataValidade=cursor.getLong(CorDataValidade)
            val IdCidade= cursor.getLong(CampoIdCidade)



            return Vacinas (id, Nome,
                    Date(Teste),
                    // ,Date(CM_DataValidade),
                   id_Pacinete,
                    IdCidade
            )


        }
    }

}

