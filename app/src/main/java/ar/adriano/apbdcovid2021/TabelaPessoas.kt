package ar.adriano.apbdcovid2021

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE ${NOME_TABELA}(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_PESSOA TEXT NOT NULL," +
                    //"$DATA_NASCIMENTO DATE NOT NULL," +
                    "$CONTACTO TEXT NOT NULL," +
                  // "$SEXO TXT NOT NULL," +
                    "$NUMERO_UTENTE TEXT NOT NULL," +
                  //  "$MORADA TEXT NOT NULL," +
                    "$DATA DATE NOT NULL," +
                    "$CAMPO_ID_DESTRITO INTEGER NOT NULL," +
                    "$IDENFERMEIRO INTEGER NOT NULL," +
                    "FOREIGN KEY($CAMPO_ID_DESTRITO) REFERENCES ${TabelaDestrito.NOME_TABELA},"+
                  "FOREIGN KEY($IDENFERMEIRO) REFERENCES ${TabelaEnfermeiro.NOME_TABELA})")



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
        var posColEnfermeiro = -1
        var posColNomeDestrito = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_DESTRITO) {
                posColNomeDestrito = i


            } else if (columns[i] == CAMPO_EXTERNO_NOME_ENFERMEIRO) {
                posColEnfermeiro = i
            }
        }

        if (posColNomeDestrito == -1 && posColEnfermeiro==-1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeDestrito) {
                "${TabelaDestrito.NOME_TABELA}.${TabelaDestrito.CAMPO_NOME} AS ${CAMPO_EXTERNO_NOME_DESTRITO}"
            } else if (i == posColEnfermeiro) {
                "${TabelaEnfermeiro.NOME_TABELA}.${TabelaEnfermeiro.NOME_ENFERMEIRO} AS ${CAMPO_EXTERNO_NOME_ENFERMEIRO}"
            }
            else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "${NOME_TABELA} INNER JOIN ${TabelaDestrito.NOME_TABELA} ON ${TabelaDestrito.NOME_TABELA}.${BaseColumns._ID}=${CAMPO_ID_DESTRITO}" +
                " INNER JOIN ${TabelaEnfermeiro.NOME_TABELA} ON ${TabelaEnfermeiro.NOME_TABELA}.${BaseColumns._ID}=${IDENFERMEIRO}"

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
        const val NOME_TABELA = "PESSOAS"
        const val NOME_PESSOA = "nome_Pessoa"
      // const val SEXO = "SEXO"
        const val NUMERO_UTENTE = "Numero_Utente"
      // const val DATA_NASCIMENTO = "DATA_NASCIMENTO"
        const val CONTACTO = "CONTACTO"
        const val DATA = "DATA_DO_TESTE"
       // const val MORADA= "Morada"
        const val CAMPO_ID_DESTRITO = "idDestrito"
        const val IDENFERMEIRO = "idEnfermeiro"

        const val CAMPO_EXTERNO_NOME_DESTRITO= "campo_externo_destritos"
        const val CAMPO_EXTERNO_NOME_ENFERMEIRO= "campo_externo_enfermeiros"


        val TODAS_COLUNAS = arrayOf(BaseColumns._ID,
          NOME_PESSOA,
          DATA,
                //DATA_NASCIMENTO,
          CONTACTO,
          NUMERO_UTENTE,
          //SEXO,
         //  DATA,
          //  MORADA,
            CAMPO_ID_DESTRITO,
            IDENFERMEIRO,
                CAMPO_EXTERNO_NOME_ENFERMEIRO,
                CAMPO_EXTERNO_NOME_DESTRITO
        )
}

}