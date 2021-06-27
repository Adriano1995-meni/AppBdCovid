package ar.adriano.apbdcovid2021

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoEnfermeiroFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        DadosApp.novoEnfermeiroFragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_enfermeiro


        return inflater.inflate(R.layout.fragment_novo_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        }


    fun navegaListaLivros() {
        // todo: navegar para a lista de livros
    }

    fun guardar() {
        // todo: guardar livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_enfermeiro-> guardar()
            R.id.action_cancelar_novo_livro -> navegaListaLivros()
            else -> return false
        }

        return true
    }
}


