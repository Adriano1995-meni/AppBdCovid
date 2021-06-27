package ar.adriano.apbdcovid2021

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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


    private lateinit var editTextNome: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextMail: EditText
    private lateinit var editTextSexo: EditText
    private lateinit var editTextData: EditText
    private lateinit var spinnerDestritos: Spinner


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextNome = view.findViewById(R.id.editTextInputNome)
        editTextMorada = view.findViewById(R.id.editTextInputMorada)
        editTextContacto = view.findViewById(R.id.editTextInputContacto)
        editTextMail = view.findViewById(R.id.editTextInputEmail)
        editTextSexo = view.findViewById(R.id.editTextInputSexo)
        editTextData = view.findViewById(R.id.editTextInputData)
        spinnerDestritos = view.findViewById(R.id.spinnerDestritos)

        }


    fun navegaListaEnfermeiro() {

        findNavController().navigate(R.id.action_NovoEnfermeirosFragment_to_action_ListaEnfermerosFragment)

    }

    fun guardar() {
        // todo: guardar livro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_enfermeiro-> guardar()
            R.id.action_cancelar_novo_livro -> navegaListaEnfermeiro()
            else -> return false
        }

        return true
    }
}


