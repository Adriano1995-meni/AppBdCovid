package ar.adriano.apbdcovid2021

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

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
            ActulizaMenusListaEnfermeiros(false)
        }
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true

            else -> {
                if( DadosApp.listaEnfermeiroFragment!!.processaOpcaoDeMenu(item)){
                    return true
                }else
                    return super.onOptionsItemSelected(item)
            }
        }
    }

    fun ActulizaMenusListaEnfermeiros(mostraBotoesAlterarEliminar:Boolean){
        menu.findItem(R.id.action_Alterar).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_Eliminar).setVisible(mostraBotoesAlterarEliminar)

    }
}
