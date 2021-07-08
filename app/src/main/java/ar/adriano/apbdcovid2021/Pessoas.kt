package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Pessoas (
        var id: Long=-1,
        var nome:String,
        var Morada:String,
        var sexo: String,

        var dataNascimento: Date,
        var Contacto: String,
        var NumeroUtente: String,
        var data: Date,
        var idEnfermeio: Long,
        var idDestrito: Long
 ){

     fun toContentValues(): ContentValues {
         val valores = ContentValues()
         valores.put(TabelaPessoas.NOME_PESSOA,nome)
         valores.put(TabelaPessoas.SEXO,sexo)
        valores.put(TabelaPessoas.MORADA,Morada)
         valores.put(TabelaPessoas.NUMERO_UTENTE,NumeroUtente)
         valores.put(TabelaPessoas.DATA_NASCIMENTO,dataNascimento.time)
         valores.put(TabelaPessoas.CONTACTO,Contacto)
         valores.put(TabelaPessoas.DATA,data.time)
         valores.put(TabelaPessoas.IDENFERMEIRO,idEnfermeio)
         valores.put(TabelaPessoas.CAMPO_ID_DESTRITO,idDestrito)

         return valores

     }

     companion object {
         fun fromCursor(cursor: Cursor): Pessoas {
             val CorId = cursor.getColumnIndex(BaseColumns._ID)
             val CorNome = cursor.getColumnIndex(TabelaPessoas.NOME_PESSOA)
             val CorSexo = cursor.getColumnIndex(TabelaPessoas.SEXO)
             val  CorMorada = cursor.getColumnIndex(TabelaPessoas.MORADA)
            val CorNif = cursor.getColumnIndex(TabelaPessoas.NUMERO_UTENTE)
            val  CorDNac = cursor.getColumnIndex(TabelaPessoas.DATA_NASCIMENTO)
             val  CorCont = cursor.getColumnIndex(TabelaPessoas.CONTACTO)
             val  CorData = cursor.getColumnIndex(TabelaPessoas.DATA)
             val  CampId = cursor.getColumnIndex(TabelaPessoas.IDENFERMEIRO)
             val CampoIdCidade = cursor.getColumnIndex(TabelaPessoas.CAMPO_ID_DESTRITO)

             val id = cursor.getLong(CorId)
             val Nome = cursor.getString(CorNome)
             val Morada = cursor.getString(CorMorada)
             val Sexo = cursor.getString(CorSexo)
             val NifId = cursor.getString(CorNif)
             val Born = cursor.getLong(CorDNac)
             val contacto = cursor.getString(CorCont)
             val C_Data = cursor.getLong(CorData)
             val CidEnfermeio = cursor.getLong(CampId)
             val IdidDestrito= cursor.getLong(CampoIdCidade)


             return Pessoas(id,
                 Nome ,Morada,
                 Sexo ,
                 Date(Born)  ,
                 contacto,
                 NifId,
                    Date(C_Data) ,
                    CidEnfermeio,
                     IdidDestrito
             )
         }
     }

 }

