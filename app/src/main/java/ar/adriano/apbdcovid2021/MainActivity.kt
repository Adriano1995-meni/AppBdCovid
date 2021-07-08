package ar.adriano.apbdcovid2021

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private  lateinit var menu: Menu

    var menuAtual = R.menu.menu_lista_enfermeiro
        set(value) {
            field = value
            invalidateOptionsMenu()
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
      DadosApp.activity=this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
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

            else -> when(menuAtual) {
                R.menu.menu_lista_enfermeiro -> (DadosApp.fragment as ListaEnfermeirosFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_enfermeiro ->( DadosApp.fragment as NovoEnfermeiroFragment).processaOpcaoMenu(item)
                R.menu.menu_editar_enfermeiros->(DadosApp.fragment as EditaEnfermeirosFragment).processaOpcaoMenu(item)
                R.menu.menu_eliminar_enfermeirros -> (DadosApp.fragment as EliminarEnfermeirosFragment).processaOpcaoMenu(item)
                else -> false
            }
        }




        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }




    fun ActulizaMenusListaPessoas(mostraBotoesAlterarEliminar:Boolean){
        menu.findItem(R.id.action_Alterar).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_Eliminar).setVisible(mostraBotoesAlterarEliminar)

    }
}
