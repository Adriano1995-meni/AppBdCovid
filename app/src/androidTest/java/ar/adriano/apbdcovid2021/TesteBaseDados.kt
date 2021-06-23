package ar.adriano.apbdcovid2021

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
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

        val id = tabelaDestritos.insert(Destritos(nome = "Drama").toContentValues())

        Assert.assertNotEquals(-1, id)

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
        Assert.assertEquals(1, AlterarRegistos)
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


}


