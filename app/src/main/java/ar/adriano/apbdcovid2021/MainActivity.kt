package ar.adriano.apbdcovid2021

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(),View.OnClickListener{

    private  lateinit var menu: Menu
   // var menuAtual = R.menu.menu_lista_enfermeiro
    private  lateinit var BotaoEnserirEnfermeiro: Button
    private  lateinit var BotaoEnserirDestritos:Button
    private  lateinit var BotaoEnserirPessoas: Button


    private  lateinit var listaEnfermeirosFragment: ListaEnfermeirosFragment
    private  lateinit var listaPessoasFragment: ListaPessoasFragment
    private  lateinit var listaDestritosFragment: ListaDestritosFragment

    var menuAtual = R.menu.menu_lista_enfermeiro
        set(value) {
            field = value
            invalidateOptionsMenu()

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BotaoEnserirEnfermeiro = findViewById(R.id.BotaoEnserirEnfermeiro)
        BotaoEnserirEnfermeiro.setOnClickListener(this)

        BotaoEnserirDestritos = findViewById(R.id.BotaoEnserirDestritos)
        BotaoEnserirDestritos.setOnClickListener(this)

        BotaoEnserirPessoas = findViewById(R.id.BotaoEnserirPessoas)
        BotaoEnserirPessoas.setOnClickListener(this)

        listaEnfermeirosFragment = ListaEnfermeirosFragment()
        listaPessoasFragment=ListaPessoasFragment()
        listaDestritosFragment= ListaDestritosFragment()
       setFragment(listaEnfermeirosFragment)


  setSupportActionBar(findViewById(R.id.toolbar))


    DadosApp.activity=this

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(menuAtual, menu)
        this.menu= menu

        if (menuAtual == R.menu.menu_lista_enfermeiro) {
            ActulizaMenusListaPessoas(false)
        }
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()

                true
            }


           else  -> when(menuAtual) {

                R.menu.menu_lista_enfermeiro -> (DadosApp.fragment as ListaEnfermeirosFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_enfermeiro ->(DadosApp.fragment as NovoEnfermeiroFragment).processaOpcaoMenu(item)
                R.menu.menu_editar_enfermeiros->(DadosApp.fragment as EditaEnfermeirosFragment).processaOpcaoMenu(item)
                R.menu.menu_eliminar_enfermeirros->(DadosApp.fragment as EliminarEnfermeirosFragment).processaOpcaoMenu(item)

               R.menu.menu_lista_destritos->(DadosApp.fragment as ListaDestritosFragment).processaOpcaoMenu(item)
               R.menu.menu_novo_destrito ->(DadosApp.fragment as NovoDestritoFragment).processaOpcaoMenu(item)
               R.menu.menu_editar_destritos->(DadosApp.fragment as EditaDestritosFragment).processaOpcaoMenu(item)
               R.menu.menu_eliminar_destritos->(DadosApp.fragment as EliminarDestritosFragment).processaOpcaoMenu(item)

               R.menu.menu_lista_pessoas->(DadosApp.fragment as ListaPessoasFragment).processaOpcaoMenu(item)
               R.menu.menu_nova_pessoa ->(DadosApp.fragment as NovaPessoaFragment).processaOpcaoMenu(item)
               R.menu.menu_editar_pessoa->(DadosApp.fragment as EditarPessoasFragment).processaOpcaoMenu(item)
               R.menu.menu_eliminar_pessoa->(DadosApp.fragment as EliminarPessoasFragment).processaOpcaoMenu(item)

               else -> false
            }
        }



        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }


    fun ActulizaMenusListaPessoas(mostraBotoesAlterarEliminar:Boolean){
        menu.findItem(R.id.action_Alterar).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_Eliminar).setVisible(mostraBotoesAlterarEliminar)

    }


public fun setFragment(fragment:Fragment){

    val fragmentTransient = supportFragmentManager.beginTransaction()
        fragmentTransient.replace(R.id.frame_Fragmet,fragment)
        //fragmentTransient.commit()
    fragmentTransient.commitAllowingStateLoss ();

}

    override fun onClick(v: View) {
        when (v.id) {

            R.id.BotaoEnserirEnfermeiro -> {
                setFragment(listaEnfermeirosFragment)


            }

            R.id.BotaoEnserirDestritos -> {
                setFragment(listaDestritosFragment)
            }

            R.id.BotaoEnserirPessoas -> {
                setFragment(listaPessoasFragment)

            }
        }

    }

}



/*
 private  lateinit var BotaoEnserirEnfermeiro: Button
    private  lateinit var BotaoEnserirDestritos:Button
    private  lateinit var BotaoEnserirPessoas: Button


    private  lateinit var listaEnfermeirosFragment: ListaEnfermeirosFragment
    private  lateinit var listaPessoasFragment: ListaPessoasFragment
    private  lateinit var listaDestritosFragment: ListaDestritosFragment
    }

*/



