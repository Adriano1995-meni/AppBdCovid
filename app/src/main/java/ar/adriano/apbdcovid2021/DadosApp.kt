package ar.adriano.apbdcovid2021

import androidx.fragment.app.Fragment

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        var listaEnfermeiroFragment: ListaEnfermeirosFragment?=null
        lateinit var fragment: Fragment
        var novoEnfermeiroFragment:NovoEnfermeiroFragment?=null
        var enfermeiroSelecionado  : Enfermeiro?= null




    }
}