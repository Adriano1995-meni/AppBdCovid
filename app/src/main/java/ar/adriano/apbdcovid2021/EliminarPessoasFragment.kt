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
import java.text.SimpleDateFormat
import java.util.*

class EliminarPessoasFragment :  Fragment() {

    private  lateinit var listaPessoasFragment: ListaPessoasFragment

    private lateinit var textViewNome: TextView
    //private lateinit var textViewMorada: TextView
    private lateinit var textViewContacto: TextView
   // private lateinit var textViewSexo: TextView
    private lateinit var textViewData: TextView
    private lateinit var textViewNumeroUtente: TextView
    private lateinit var textViewDestrito: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_eliminar_pessoa


        return inflater.inflate(R.layout.fragment_elimina_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewNomeVacina)
    //    textViewMorada = view.findViewById(R.id.textViewNumeroUtente)
        textViewData = view.findViewById(R.id.textViewDataSegundaDoce)
        textViewContacto = view.findViewById(R.id.textViewContacto)
        textViewNumeroUtente = view.findViewById(R.id.textViewEmail)
        //textViewSexo = view.findViewById(R.id.textViewSexo)
        textViewDestrito = view.findViewById(R.id.textViewDestrito)






        val pessoas = DadosApp.pessoasSelecionado!!

        val DataHoje =  SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())

        val datahoje= DataHoje.format(pessoas.data)
        textViewNome.setText(pessoas.nome)
       // textViewMorada.setText(pessoas.Morada)

        textViewNumeroUtente.setText(pessoas.NumeroUtente)
        textViewContacto.setText(pessoas.Contacto)

       // textViewSexo.setText(pessoas.sexo)
        textViewData.text = datahoje.toString()
        textViewDestrito.setText(pessoas.CampoExternoDestrito)

    }

    fun navegaListaPessoas() {
       // findNavController().navigate(R.id.action_liminarPessoasFragment_to_listaPessoasFragment)
        listaPessoasFragment = ListaPessoasFragment()
        DadosApp.activity.setFragment(listaPessoasFragment)
    }

    fun elimina() {
        val uriEnfermeiros = Uri.withAppendedPath(
                ContentProviderEnfermeiros.ENDRECO_PESSOAS,
                DadosApp.pessoasSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
                uriEnfermeiros,
                null,
                null
        )

        if (registos != 1) {
            Toast.makeText(
                    requireContext(),
                    R.string.erro_eliminar_pessoa,
                    Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
                requireContext(),
                R.string.Pessoa_Elimindo_sucesso,
                Toast.LENGTH_LONG
        ).show()
        navegaListaPessoas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_pessoas -> elimina()
            R.id.action_cancelar_eliminar_pessoas -> navegaListaPessoas()
            else -> return false
        }

        return true
    }

}