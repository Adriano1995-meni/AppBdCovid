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
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*

class EliminarDestritosFragment: Fragment() {


    private lateinit var textViewNome: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosDestritosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_eliminar_enfermeirros


        return inflater.inflate(R.layout.fragment_elimina_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNome)


        val destritos =  DadosDestritosApp.DestritoSelecionado!!


        textViewNome.setText(destritos.nome)



    }

    fun navegaListaDestritos() {
        findNavController().navigate(R.id.action_eliminarDestritos_Fragment_to_lista_destritos_Fragment)
    }

    fun elimina() {
        val uriDestritos = Uri.withAppendedPath(
            ContentProviderDestritos.ENDRECO_DESTRITO,
            DadosApp.EnfermeiroSelecionado!!.id.toString()
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
            R.string.enfermeiro_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaDestritos()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_enfermeiro -> elimina()
            R.id.action_cancelar_eliminar_enfermeiro -> navegaListaDestritos()
            else -> return false
        }

        return true
    }

}