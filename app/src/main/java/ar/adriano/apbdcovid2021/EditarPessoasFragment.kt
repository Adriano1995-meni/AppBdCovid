package ar.adriano.apbdcovid2021

import android.database.Cursor
import android.net.Uri
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
import java.text.SimpleDateFormat
import java.util.*

class EditarPessoasFragment: Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private  lateinit var listaPessoasFragment: ListaPessoasFragment

    private lateinit var editTextNome: EditText
    private lateinit var editTextNumeroUtente: EditText
    private lateinit var editTextContacto: EditText
   // private lateinit var editTextMail: EditText
  //  private lateinit var editTextSexo: EditText
    private lateinit var editTextData: EditText
    private lateinit var spinnerDestritos: Spinner
    private lateinit var spinnerEnfermeiro: Spinner


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_editar_pessoa
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_pessoas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextNome = view.findViewById(R.id.editTextInputNomeVacina)
        editTextNumeroUtente = view.findViewById(R.id.editTextInputEmail)
        editTextContacto = view.findViewById(R.id.editTextInputContacto)
        editTextData = view.findViewById(R.id.editTextInputData)
        spinnerDestritos = view.findViewById(R.id.spinnerDestritos)
        spinnerEnfermeiro = view.findViewById(R.id.spinnerEnfermeiros)
       //  editTextMail = view.findViewById(R.id.editTextInputEmail)
       // editTextSexo = view.findViewById(R.id.editTextInputSexo)



        LoaderManager.getInstance(this)
                .initLoader(ID_LOADER_MANAGER_DESTRITOS, null, this)
        LoaderManager.getInstance(this)
                .initLoader(ID_LOADER_MANAGER_ENFERMEIRO, null, this)

        val DataHoje =  SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())

        val datahoje= DataHoje.format(DadosApp.pessoasSelecionado!!.data)

        editTextNome.setText(DadosApp.pessoasSelecionado!!.nome)
        editTextContacto.setText(DadosApp.pessoasSelecionado!!.Contacto)
        editTextNumeroUtente.setText(DadosApp.pessoasSelecionado!!.NumeroUtente)
        editTextData.setText(datahoje.toString())

       // editTextMorada.setText(DadosApp.pessoasSelecionado!!.Morada)
       // editTextMail.setText(DadosApp.pessoasSelecionado!!.NumeroUtente)
     //   editTextSexo.setText(DadosApp.pessoasSelecionado!!.sexo)


    }

    fun navegaListaPessoas() {

    //   findNavController().navigate(R.id.action_editaPessoasFragment_to_listaPessoasFragment)
        listaPessoasFragment = ListaPessoasFragment()
        DadosApp.activity.setFragment(listaPessoasFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()

            return
        }


        val contacto = editTextContacto.text.toString()
        if (contacto.isEmpty()) {
            editTextContacto.setError(getString(R.string.preencha_Contacto))
            editTextContacto.requestFocus()
            return
        }



        val data = editTextData.text.toString()
        if (data.isEmpty()) {
            editTextData.setError(getString(R.string.preencha_Data))
            editTextData.requestFocus()
            return
        }


        val Numero_Utente = editTextNumeroUtente.text.toString()
        if (Numero_Utente.isEmpty()) {
            editTextNumeroUtente.setError(getString(R.string.preencha_Mail))
            editTextNumeroUtente.requestFocus()
            return
        }

        val idDestrito = spinnerDestritos.selectedItemId

        val pessoas = DadosApp.pessoasSelecionado
        pessoas!!.nome= nome
        //pessoas.Morada = morada
        pessoas.Contacto = contacto
     //   pessoas.sexo = sexo
        pessoas.data = Date(data)
    //   pessoas.dataNascimento = Date(data)
        pessoas.NumeroUtente = Numero_Utente
        pessoas.idDestrito = idDestrito

        val uriPessoas = Uri.withAppendedPath(
                ContentProviderEnfermeiros.ENDRECO_PESSOAS,
                pessoas.id.toString()
        )

        val registos = activity?.contentResolver?.update(
                uriPessoas,
                pessoas.toContentValues(),
                null,
                null
        )

        if (registos != 1) {
            Toast.makeText(
                    requireContext(),
                    R.string.erro_alterar_pessoa,
                    Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
                requireContext(),
                R.string.Pessoa_guardado_sucesso,
                Toast.LENGTH_LONG
        ).show()

        navegaListaPessoas()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_pessoas -> guardar()
            R.id.action_cancelar_edita_pessoas ->  navegaListaPessoas()
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
        if(id == NovaPessoaFragment.ID_LOADER_MANAGER_DESTRITOS) {
            return CursorLoader(
                    requireContext(),
                    ContentProviderEnfermeiros.ENDRECO_DESTRITO,
                    TabelaDestrito.TODAS_COLUNAS,
                    null, null,
                    TabelaDestrito.CAMPO_NOME

            )
        }
        else if(id==ID_LOADER_MANAGER_ENFERMEIRO){
            return CursorLoader(
                    requireContext(),
                    ContentProviderEnfermeiros.ENDRECO_ENFERMEIRA,
                    TabelaEnfermeiro.TODAS_COLUNAS,
                    null, null,
                    TabelaEnfermeiro.NOME_ENFERMEIRO

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
        if(loader.id == ID_LOADER_MANAGER_DESTRITOS){
            atualizaSpinnerDestrito(data)
        }

        else if(loader.id == ID_LOADER_MANAGER_ENFERMEIRO){
            atualizaSpinnerEnfermeiro(data)
        }

        atualizaPessoasSelecionada()

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
        atualizaSpinnerDestrito(null)
        atualizaSpinnerEnfermeiro(null)

    }

    private fun atualizaSpinnerDestrito(data: Cursor?) {
        spinnerDestritos.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data,
                arrayOf(TabelaDestrito.CAMPO_NOME),
                intArrayOf(android.R.id.text1),
                0
        )
    }

    private fun atualizaSpinnerEnfermeiro(data: Cursor?) {
        spinnerEnfermeiro.adapter = SimpleCursorAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data,
                arrayOf(TabelaEnfermeiro.NOME_ENFERMEIRO),
                intArrayOf(android.R.id.text1),
                0
        )
    }



    private fun atualizaPessoasSelecionada() {
        val idDestrito = DadosApp.pessoasSelecionado!!.idDestrito
        val idEnfermeiro = DadosApp.pessoasSelecionado!!.idEnfermeio

        val ultimoDestrito = spinnerDestritos.count - 1
        val ultimaEnfermeiro = spinnerEnfermeiro.count - 1
        for (i in 0..ultimoDestrito){
            if(idDestrito == spinnerDestritos.getItemIdAtPosition(i)){
                spinnerDestritos.setSelection(i)
                return
            }
        }

        for (i in 0..ultimaEnfermeiro){
            if(idEnfermeiro == spinnerEnfermeiro.getItemIdAtPosition(i)){
                spinnerEnfermeiro.setSelection(i)
                return
            }
        }
    }

            companion object {
        const val ID_LOADER_MANAGER_DESTRITOS = 1
        const val ID_LOADER_MANAGER_ENFERMEIRO = 2
    }

}
