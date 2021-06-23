package ar.adriano.apbdcovid2021

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import java.util.*

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

    private fun insereEnfermeiro(tabela: TabelaEnfermeiro, enfermeiro: Enfermeiro): Long {
        val id = tabela.insert(enfermeiro.toContentValues())
        Assert.assertNotEquals(-1, id)
        return id
    }


    private fun inserePessoas(tabela: TabelaPessoas, pessoas: Pessoas): Long {
        val id = tabela.insert(pessoas.toContentValues())
        Assert.assertNotEquals(-1, id)

        return id
    }



    private fun getDestritosBaseDados(tabela: TabelaDestrito, id: Long): Destritos {
        val cursor = tabela.query(
            TabelaDestrito.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        Assert.assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Destritos.fromCursor(cursor)
    }

    private fun getEnfemeiroBaseDados(tabela: TabelaEnfermeiro, id: Long): Enfermeiro {
        val cursor = tabela.query(
            TabelaEnfermeiro.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        Assert.assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Enfermeiro.fromCursor(cursor)
    }


    private fun getPessoasBaseDados(tabela: TabelaPessoas, id: Long): Pessoas {
        val cursor = tabela.query(
            TabelaPessoas.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        Assert.assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pessoas.fromCursor(cursor)
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

        val destritos = Destritos(nome = "Viseu")
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




    @Test
    fun consegueInserirEnfermeiros() {
        val db = getBdPessoasOpenHelper().writableDatabase

        // Categoria Actual

        val tabelaDestrito = TabelaDestrito(db)
        val destrito = Destritos(nome = "Porto")
        destrito.id = insereDestritos(tabelaDestrito, destrito)


        val tabelaEnfermeiro = TabelaEnfermeiro(db)
        val enfermeiro = Enfermeiro(
            nome = "Francisco Mendes",
            contacto="Contacto :" +
                    "96558887",
            sexo = "Sexo: M",
            Morada = "Rua Santa Catarina",
            data = Date(1990,7,5),
            idDestrito = destrito.id,
            Mail = "@adrigmail.com")
        enfermeiro.id = insereEnfermeiro(tabelaEnfermeiro, enfermeiro)
        assertEquals(enfermeiro, getEnfemeiroBaseDados(tabelaEnfermeiro, enfermeiro.id))

        db.close()
    }



    @Test
    fun consegueAlterarEnfermeio() {
        val db = getBdPessoasOpenHelper().writableDatabase


        // Categoria Actual

        val tabelaDestrito = TabelaDestrito(db)
        val destrito = Destritos(nome = "Castelo Branco")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        // Altera Para

        val AlterarDestrito = Destritos(nome = "Viseu")
        AlterarDestrito.id = insereDestritos(tabelaDestrito, AlterarDestrito)

        val tabelaEnfermeiro = TabelaEnfermeiro(db)
        val enfermeiro = Enfermeiro(
            nome = "João  Almeida",
            contacto="Contacto :" +
                    "925572030",
            data = Date(2021,4,15),
            sexo = "Sexo:M",
            Morada = "Edifício Atrium, Praça Dom João",
            idDestrito = destrito.id,
            Mail = "@adrigmail.com")

        enfermeiro.id = insereEnfermeiro(tabelaEnfermeiro, enfermeiro)

        enfermeiro.contacto = "962227161"
        enfermeiro.idDestrito = AlterarDestrito.id
       // enfermeiro.nomeCategoria = AlterarDestrito.nome // só é necessário nos testes


        val registosAlterados = tabelaEnfermeiro.update(
            enfermeiro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(enfermeiro.id.toString())
        )

        Assert.assertEquals(1, registosAlterados)
        assertEquals(enfermeiro, getEnfemeiroBaseDados(tabelaEnfermeiro, enfermeiro.id))

        db.close()
    }




    @Test
    fun ConsegueEliminarEnfermeiro() {

        val db = getBdPessoasOpenHelper().writableDatabase

        val tabelaDestrito = TabelaDestrito(db)
        val destrito = Destritos(nome = "Viana de Castelo")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        val tabelaEnfermeiro = TabelaEnfermeiro(db)
        val enfermeiro = Enfermeiro(
            nome = "Armando correia  Co",
            contacto="Contacto :" +
                    "925888721",
            sexo ="Sexo :M",
            Morada = "Avenida General Humberto Delgado",
            data = Date(2020,7,15),
            idDestrito = destrito.id ,
            Mail = "fpv@gmail"
            //   nomeCategoria =  destrito.nome
        )
        enfermeiro.id = insereEnfermeiro(tabelaEnfermeiro, enfermeiro)

        // Eliminar Enfermeiro

        val EliminadosEnfermeiro = tabelaEnfermeiro.delete(
            "${BaseColumns._ID}=?",
            arrayOf(enfermeiro.id.toString())
        )
        Assert.assertEquals(1, EliminadosEnfermeiro)


        db.close()
    }




    @Test
    fun consegueLerEnfermeiro() {
        val db = getBdPessoasOpenHelper().writableDatabase

        val tabelaDestrito = TabelaDestrito(db)
        val destrito = Destritos(nome = "Aveiro")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        val tabelaEnfermeiro = TabelaEnfermeiro(db)
        val enfermeiro = Enfermeiro(
            nome = "Natalia Perez",
            contacto="Contacto :" +
                    "96666717",
            data = Date(2021,3,15),
            sexo = "sexo:F",
            Morada = " Viela Arrocheiras de Cima",
            idDestrito = destrito.id ,
            Mail = "jfd@gmail.com"
            //nomeCategoria = destrito.nome

        )
        enfermeiro.id = insereEnfermeiro(tabelaEnfermeiro, enfermeiro)

        assertEquals(enfermeiro, getEnfemeiroBaseDados(tabelaEnfermeiro, enfermeiro.id))

        db.close()
    }



    @Test
    fun consegueInserirPessoas() {

        val db = getBdPessoasOpenHelper().writableDatabase

        val tabelaDestrito = TabelaDestrito(db)
        val destrito= Destritos(nome = "Portalegre")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        val tabelaEnfermeiro = TabelaEnfermeiro(db)
        val enfermeiro = Enfermeiro(
            nome = " Elisio Shanchs",
            contacto="Contacto :" +
                    "962024715",
            sexo = " Sexo:M",
            Morada = "Avenida da Liberdade",
            data = Date(2020,7,15),
            idDestrito = destrito.id,
            Mail = "nataperez@gmail.com"
        //    nomeCategoria =  destrito.nome

        )
        enfermeiro.id = insereEnfermeiro(tabelaEnfermeiro, enfermeiro)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoas =  Pessoas(
            nome ="Junior Carlos Britos",
            sexo ="Sexo:M",
            Morada = "Avenida da Liberdade",
            NumeroUtente = "254789657",
            dataNascimento =Date(1995,7,5),
            Contacto="Contacto :" +
                    "925557788",
            dataTeste =Date(2020,10,15),
            idDestrito = destrito.id,
            idEnfermeio =enfermeiro.id)

        pessoas.id= inserePessoas(tabelaPessoas,pessoas)
        assertEquals(pessoas, getPessoasBaseDados(tabelaPessoas, pessoas.id))

        db.close()

    }





    @Test
    fun consegueAlterarPaciente() {

        val db = getBdPessoasOpenHelper().writableDatabase

        val tabelaDestrito = TabelaDestrito(db)
        val destrito= Destritos(nome = "Santarem")
        destrito.id = insereDestritos(tabelaDestrito, destrito)

        val tabelaEnfermeiro = TabelaEnfermeiro(db)
        val enfermeiro = Enfermeiro(
            nome = "Amilcar Morreira",
            contacto="Contacto :" +
                    "912224715",
            sexo = "Sexo: M",
            Morada = "Beco do Sacrifício",
            data = Date(2020,7,15),
            idDestrito = destrito.id,
            Mail = "meni@gmail.com")
            //nomeCategoria =  destrito.nome)

        enfermeiro.id = insereEnfermeiro(tabelaEnfermeiro, enfermeiro)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoas =  Pessoas(
            nome ="Carlos Matus Da Silva",
            sexo ="Sexo: M",
            Morada = "Beco do Sacrifício",
            NumeroUtente = "251789657",
            dataNascimento =Date(1995,7,5),
            Contacto="Contacto: " +
                    "923557788",
            dataTeste =Date(2020,10,15),
            idDestrito = destrito.id,
            idEnfermeio =enfermeiro.id)


        pessoas.id= inserePessoas(tabelaPessoas,pessoas)

        pessoas.Contacto = "967557744"
        pessoas.idEnfermeio= enfermeiro.id
        pessoas.dataTeste=Date(2021,3,16)
        pessoas.dataNascimento=Date(1987,7,18)


        val AlteradosPacientes = tabelaPessoas.update(
            pessoas.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pessoas.id.toString())
        )

        Assert.assertEquals(1, AlteradosPacientes)

        db.close()
    }






}


