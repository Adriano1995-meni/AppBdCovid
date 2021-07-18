package ar.adriano.apbdcovid2021

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class EliminarDestritosFragment: Fragment() {


    private lateinit var textViewNome: TextView
    private  lateinit var listaDestritoFragment: ListaDestritosFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_eliminar_destritos


        return inflater.inflate(R.layout.fragment_eliminar_destritos_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNomeVacina)


        val destritos =  DadosApp.DestritoSelecionado!!


        textViewNome.setText(destritos.nome)



    }

    fun navegaListaDestritos() {
        listaDestritoFragment = ListaDestritosFragment()
        DadosApp.activity.setFragment(listaDestritoFragment)
    }

    fun elimina() {
        val uriDestritos = Uri.withAppendedPath(
            ContentProviderEnfermeiros.ENDRECO_DESTRITO,
            DadosApp.DestritoSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriDestritos,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_enfermeiro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.Destrito_Elimindo_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaDestritos()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_destritos -> elimina()
            R.id.action_cancelar_eliminar_destritos -> navegaListaDestritos()
            else -> return false
        }

        return true
    }

}