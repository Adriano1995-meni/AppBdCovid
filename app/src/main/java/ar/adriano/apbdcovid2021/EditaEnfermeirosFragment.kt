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
import androidx.navigation.fragment.findNavController
import ar.adriano.apbdcovid2021.NovoEnfermeiroFragment.Companion.ID_LOADER_MANAGER_DESTRITOS
import com.google.android.material.snackbar.Snackbar
import java.util.*


class EditaEnfermeirosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var editTextNome: EditText
    private lateinit var editTextMorada: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextMail: EditText
    private lateinit var editTextSexo: EditText
    private lateinit var editTextData: EditText
    private lateinit var spinnerDestritos: Spinner


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_editar_enfermeiros
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextNome = view.findViewById(R.id.editTextInputNome)
        editTextMorada = view.findViewById(R.id.editTextInputMorada)
        editTextContacto = view.findViewById(R.id.editTextInputContacto)
        editTextMail = view.findViewById(R.id.editTextInputEmail)
        editTextSexo = view.findViewById(R.id.editTextInputSexo)
        editTextData = view.findViewById(R.id.editTextInputData)
        spinnerDestritos = view.findViewById(R.id.spinnerDestritos)


        LoaderManager.getInstance(this)
                .initLoader(NovoEnfermeiroFragment.ID_LOADER_MANAGER_DESTRITOS, null, this)


        editTextNome.setText(DadosApp.enfermeiroSelecionado!!.nome)
        editTextContacto.setText(DadosApp.enfermeiroSelecionado!!.contacto)
        editTextMorada.setText(DadosApp.enfermeiroSelecionado!!.Morada)
        editTextMail.setText(DadosApp.enfermeiroSelecionado!!.Mail)
        editTextSexo.setText(DadosApp.enfermeiroSelecionado!!.sexo)
        editTextData.setText(DadosApp.enfermeiroSelecionado!!.data.toString())

    }


    fun navegaListaEnfermeiro() {

        findNavController().navigate(R.id.action_NovoEnfermeirosFragment_to_action_ListaEnfermerosFragment)

    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_Nome))
            editTextNome.requestFocus()

            return
        }
        val morada = editTextMorada.text.toString()
        if (morada.isEmpty()) {
            editTextMorada.setError(getString(R.string.preencha_Morada))
            editTextMorada.requestFocus()
            return
        }

        val contacto = editTextContacto.text.toString()
        if (contacto.isEmpty()) {
            editTextContacto.setError(getString(R.string.preencha_Contacto))
            editTextContacto.requestFocus()
            return
        }

        val sexo = editTextSexo.text.toString()
        if (sexo.isEmpty()) {
            editTextSexo.setError(getString(R.string.preencha_sexo))
            editTextSexo.requestFocus()
            return
        }

        val data = editTextData.text.toString()
        if (data.isEmpty()) {
            editTextData.setError(getString(R.string.preencha_Data))
            editTextData.requestFocus()
            return
        }


        val mail = editTextMail.text.toString()
        if (mail.isEmpty()) {
            editTextMail.setError(getString(R.string.preencha_Mail))
            editTextMail.requestFocus()
            return
        }

        val idDestrito = spinnerDestritos.selectedItemId

        val enfermeiro = DadosApp.enfermeiroSelecionado!!
         enfermeiro.nome= nome
        // enfermeiro.Morada = morada
        enfermeiro.contacto = contacto
        enfermeiro.sexo = sexo
        enfermeiro.data = Date(data)
        enfermeiro.Mail = mail
        enfermeiro.idDestrito = idDestrito

        val uriEnfermeiro = Uri.withAppendedPath(
                ContentProviderEnfermeiros.ENDRECO_ENFERMEIRA,
                enfermeiro.id.toString()
        )

        val registos = activity?.contentResolver?.update(
                uriEnfermeiro,
                enfermeiro.toContentValues(),
                null,
                null
        )

        if (registos != 1) {
            Toast.makeText(
                    requireContext(),
                    R.string.erro_alterar_enfermeiro,
                    Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
                requireContext(),
                R.string.enfermeiro_guardado_sucesso,
                Toast.LENGTH_LONG
        ).show()
        navegaListaEnfermeiro()
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_enfermeiro -> guardar()
            R.id.action_cancelar_edita_enfermiro -> navegaListaEnfermeiro()
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
        return CursorLoader(
                requireContext(),
                ContentProviderEnfermeiros.ENDRECO_DESTRITO,
                TabelaDestrito.TODAS_COLUNAS,
                null, null,
                TabelaDestrito.CAMPO_NOME
        )
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
        atualizaSpinnerDestritos(data)
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
        atualizaDestritosSelecionada()
    }

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


    private fun atualizaDestritosSelecionada() {
        val idCategoria = DadosApp.enfermeiroSelecionado!!.idDestrito

        val ultimaCategoria = spinnerDestritos.count - 1
        for (i in 0..ultimaCategoria) {
            if (idCategoria == spinnerDestritos.getItemIdAtPosition(i)) {
                spinnerDestritos.setSelection(i)
                return
            }
        }
    }




    companion object {
        const val ID_LOADER_MANAGER_DESTRITOS = 0
    }

}
