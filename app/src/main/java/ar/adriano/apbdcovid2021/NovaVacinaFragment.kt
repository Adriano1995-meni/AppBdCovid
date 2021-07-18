package ar.adriano.apbdcovid2021

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.google.android.material.snackbar.Snackbar
import java.util.*

class NovaVacinaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    private  lateinit var listaVacinasFragment: ListaVacinasFragment

    private lateinit var editTextNomeVacina: EditText
    private lateinit var editTextFabricante: EditText
   // private lateinit var editTextValidade: EditText
    private lateinit var spinnerDestritos: Spinner
    private lateinit var spinnerPessoas: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_vacina


        return inflater.inflate(R.layout.fragment_nova_vacina, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextNomeVacina = view.findViewById(R.id.editTextInputNomeVacina)
       editTextFabricante = view.findViewById(R.id.editTextInputNomeFabricante)
       // editTextValidade = view.findViewById(R.id.editTextInputDataValidacao)
        spinnerDestritos = view.findViewById(R.id.spinnerDestritos)
        spinnerPessoas = view.findViewById(R.id.spinnerPessoas)


        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_DESTRITOS, null, this)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_PESSOAS, null, this)

    }


    fun navegaListaVacinas() {

        // findNavController().navigate(R.id.action_NovoEnfermeirosFragment_to_action_ListaEnfermerosFragment)
        listaVacinasFragment = ListaVacinasFragment()
        DadosApp.activity.setFragment(listaVacinasFragment)
    }

    fun guardar() {
        val Nome_Vacina = editTextNomeVacina.text.toString()
        if (Nome_Vacina.isEmpty()) {
            editTextNomeVacina.setError(getString(R.string.preencha_Nome))
            editTextNomeVacina.requestFocus()

            return
        }


        val Fabricante = editTextFabricante.text.toString()
        if (Fabricante.isEmpty()) {
            editTextFabricante.setError(getString(R.string.preencha_Contacto))
            editTextFabricante.requestFocus()
            return
        }


/*
        val Validacao = editTextValidade.text.toString()
        if (Validacao.isEmpty()) {
            editTextValidade.setError(getString(R.string.preencha_Data))
            editTextValidade.requestFocus()
            return
        }

*/


        val idDestrito = spinnerDestritos.selectedItemId
        val idPessoas = spinnerPessoas.selectedItemId

        val vacinas = Vacinas(nome = Nome_Vacina, nome_frabricante = Fabricante,
         //   data_Validade = Date(Validacao),
            idDestrito = idDestrito,idPaciente= idPessoas)

        val uri = activity?.contentResolver?.insert(
            ContentProviderEnfermeiros.ENDRECO_VACINA,
            vacinas.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNomeVacina,
                getString(R.string.erro_inserir_enfermeiro),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        Toast.makeText(
            requireContext(),
            R.string.enfermeiro_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()

        navegaListaVacinas()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_vacina-> guardar()
            R.id.action_cancelar_novo_vacina -> navegaListaVacinas()
            else -> return false
        }

        return true
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        if(id == ID_LOADER_MANAGER_DESTRITOS) {
            return CursorLoader(
                requireContext(),
                ContentProviderEnfermeiros.ENDRECO_DESTRITO,
                TabelaDestrito.TODAS_COLUNAS,
                null, null,
                TabelaDestrito.CAMPO_NOME

            )
        }
        else if(id== ID_LOADER_MANAGER_PESSOAS){
            return CursorLoader(
                requireContext(),
                ContentProviderEnfermeiros.ENDRECO_PESSOAS,
                TabelaPessoas.TODAS_COLUNAS,
                null, null,
                TabelaPessoas.NOME_PESSOA

            )
        }

        return null!!

    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        //   atualizaSpinnerDestritos(data)

        if(loader.id == ID_LOADER_MANAGER_DESTRITOS){
            atualizaSpinnerDestritos(data)
        }

        else if(loader.id == ID_LOADER_MANAGER_PESSOAS){
            atualizaSpinnerPessoas(data)
        }

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        atualizaSpinnerDestritos(null)
        atualizaSpinnerPessoas(null)

    }



    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */


    private fun atualizaSpinnerDestritos(data: Cursor?) {
        spinnerDestritos.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaDestrito.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaSpinnerPessoas(data: Cursor?) {
        spinnerPessoas.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaPessoas.NOME_PESSOA),
            intArrayOf(android.R.id.text1),
            0
        )
    }





    companion object {
        const val ID_LOADER_MANAGER_DESTRITOS = 1
        const val ID_LOADER_MANAGER_PESSOAS = 2
    }



}