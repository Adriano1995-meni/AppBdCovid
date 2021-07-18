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

class EliminarVacinasFragment:  Fragment() {

    private lateinit var listaVacinasFragment: ListaVacinasFragment

    private lateinit var textViewNomeVacinas: TextView
    private lateinit var textViewNomeFabricante: TextView
   // private lateinit var textViewDataValidacao: TextView
 //   private lateinit var textViewDestrito: TextView
   // private lateinit var textViewPessoas: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_eliminar_vacina


        return inflater.inflate(R.layout.fragment_eliminar_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomeVacinas = view.findViewById(R.id.textViewNome_Vacina)
        textViewNomeFabricante = view.findViewById(R.id.textViewNome_Fabricante)
      //  textViewDataValidacao= view.findViewById(R.id.textViewData_Validacao)
    //    textViewPessoas = view.findViewById(R.id.textViewPessoas)
   //     textViewDestrito = view.findViewById(R.id.textViewDestrito)


        val vacinas = DadosApp.vacinasSelecionado!!

        val DataValidade = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())

        ///val dataValidade = DataValidade.format(vacinas.data_Validade)

        textViewNomeVacinas.setText(vacinas.nome)
        textViewNomeFabricante.setText(vacinas.nome_frabricante)
        //textViewDataValidacao.text = dataValidade.toString()
    //    textViewDestrito.setText(vacinas.idDestrito.toString())
      //  textViewPessoas.setText(vacinas.idPaciente.toString())


    }

    fun navegaListaVacinas() {
        // findNavController().navigate(R.id.action_liminarPessoasFragment_to_listaPessoasFragment)
        listaVacinasFragment = ListaVacinasFragment()
        DadosApp.activity.setFragment(listaVacinasFragment)
    }

    fun elimina() {
        val uriEnfermeiros = Uri.withAppendedPath(
            ContentProviderEnfermeiros.ENDRECO_VACINA,
            DadosApp.vacinasSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriEnfermeiros,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_vacina,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.Vacina_Elimindo_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaVacinas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_vacina -> elimina()
            R.id.action_cancelar_eliminar_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }
}