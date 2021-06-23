package ar.adriano.apbdcovid2021

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdPessoasOpenHelper() = BdRegistaPessoasOpenHelper(getAppContext())


    private fun insereDestritos(tabela: TabelaDestrito, destritos: Destritos): Long {
        val id = tabela.insert(destritos.toContentValues())
        Assert.assertNotEquals(-1, id)

        return id
    }

    private fun getDestritosBaseDados(tabela: TabelaDestrito, id: Long): Destritos {
        val cursor = tabela.query(
            TabelaDestrito.TODAS_COLUNAS,
            "${TabelaDestrito.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        Assert.assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Destritos.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase( BdRegistaPessoasOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdRegistaPessoasOpenHelper(getAppContext())
        val db = openHelper.readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirDestrito() {
        val db = getBdPessoasOpenHelper().writableDatabase
        val tabelaDestritos = TabelaDestrito(db)

        val destritos = Destritos(nome = "Drama")
        destritos.id = insereDestritos(tabelaDestritos,destritos )

        assertEquals(destritos, getDestritosBaseDados(tabelaDestritos, destritos.id))
        db.close()
    }




    @Test
    fun ConsegueAlterarDestritos() {

        // Alterar
        val db = getBdPessoasOpenHelper().writableDatabase

        val tabelaDestrito = TabelaDestrito(db)
        val destrito = Destritos(nome = "Lisboa")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        // Alterar para
        destrito.nome = "Vila Real"

        val AlterarRegistos = tabelaDestrito.update(
            destrito.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(destrito.id.toString())
        )
         assertEquals(1, AlterarRegistos)
        assertEquals(destrito, getDestritosBaseDados(tabelaDestrito, destrito.id))

        db.close()
    }



    @Test
    fun ConsegueEliminarDestritos() {

        val db =getBdPessoasOpenHelper().writableDatabase
        val tabelaDestrito = TabelaDestrito(db)

        val destrito = Destritos(nome = "Guarda")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        val registosEliminados = tabelaDestrito.delete(

            "${BaseColumns._ID}=?",
            arrayOf(destrito.id.toString())
        )
        Assert.assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueLerDestritos() {
        val db = getBdPessoasOpenHelper().writableDatabase
        val tabelaDestritos = TabelaDestrito(db)
        val destritos = Destritos(nome = "Vila Nova De Gaia")
        destritos.id = insereDestritos(tabelaDestritos, destritos)

        assertEquals(destritos, getDestritosBaseDados(tabelaDestritos, destritos.id))

        db.close()
    }



}


