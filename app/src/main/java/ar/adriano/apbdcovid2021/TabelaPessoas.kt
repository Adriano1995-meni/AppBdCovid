package ar.adriano.apbdcovid2021

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPessoas(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL(
            "CREATE TABLE ${NOME_TABELA}LE(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NOME_PESSOA TEXT NOT NULL," +
                    "$DATA_NASCIMENTO DATE NOT NULL," +
                    "$CONTACTO TEXT NOT NULL," +
                    "$SEXO TXT NOT NULL," +
                    "$NUMERO_UTENTE TEXT NOT NULL," +
                    "$MORADA TEXT NOT NULL," +
                    "$DATA DATE NOT NULL," +
                    "$CAMPO_ID_DESTRITO INTEGER NOT NULL," +
                    "$IDENFERMEIRO INTEGER NOT NULL," +
                    "FOREIGN KEY($CAMPO_ID_DESTRITO) REFERENCES ${TabelaDestrito.NOME_TABELA})")
                    
    }




    companion object {
        const val NOME_TABELA = "PACIENTE"
        const val NOME_PESSOA = "nome_Pessoa"
        const val SEXO = "SEXO"
        const val NUMERO_UTENTE = "Numero_Utente"
        const val DATA_NASCIMENTO = "DATA_NASCIMENTO"
        const val CONTACTO = "CONTACTO"
        const val DATA = "DATA_DO_TESTE"
        const val MORADA= "Morada"
        const val CAMPO_ID_DESTRITO = "idDestrito"
        const val IDENFERMEIRO = "idEnfermeiro"


}

}