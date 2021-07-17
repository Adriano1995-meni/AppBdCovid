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


class EliminarEnfermeirosFragment :  Fragment() {

    private  lateinit var listaEnfermeirosFragment: ListaEnfermeirosFragment
    private lateinit var textViewNome: TextView
    private lateinit var textViewMorada: TextView
    private lateinit var textViewContacto: TextView
    private lateinit var textViewSexo: TextView
    private lateinit var textViewData: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewDestrito: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_eliminar_enfermeirros


        return inflater.inflate(R.layout.fragment_elimina_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNome)
        textViewMorada = view.findViewById(R.id.textViewNumeroUtente)
        textViewData = view.findViewById(R.id.textViewData)
        textViewContacto = view.findViewById(R.id.textViewContacto)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewSexo = view.findViewById(R.id.textViewSexo)
        textViewDestrito = view.findViewById(R.id.textViewDestrito)






        val enfermeiros = DadosApp.enfermeiroSelecionado!!

        val DataHoje =  SimpleDateFormat("dd/MM/YYYY",Locale.getDefault())

        val datahoje= DataHoje.format(enfermeiros.data)
        textViewNome.setText(enfermeiros.nome)
        textViewMorada.setText(enfermeiros.Morada)

        textViewEmail.setText(enfermeiros.Mail)
        textViewContacto.setText(enfermeiros.contacto)

        textViewSexo.setText(enfermeiros.sexo)
        textViewData.text = datahoje.toString()
        textViewDestrito.setText(enfermeiros.nomeCategoria)


    }

    fun navegaListaEnfermeiros() {
     //   findNavController().navigate(R.id.action_eliminaEnfermeiroFragment_to_ListaEnfermeirosFragment)
        listaEnfermeirosFragment = ListaEnfermeirosFragment()
        DadosApp.activity.setFragment(listaEnfermeirosFragment)
    }

    fun elimina() {
        val uriEnfermeiros = Uri.withAppendedPath(
                ContentProviderEnfermeiros.ENDRECO_ENFERMEIRA,
                DadosApp.enfermeiroSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
                uriEnfermeiros,
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
        navegaListaEnfermeiros()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_enfermeiro -> elimina()
            R.id.action_cancelar_eliminar_enfermeiro -> navegaListaEnfermeiros()
            else -> return false
        }

        return true
    }

}