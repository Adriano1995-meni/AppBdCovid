package ar.adriano.apbdcovid2021

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

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

        tabelaDestritos.insert(Destritos(nome = "Guarda"))

        db.close()
    }
}


