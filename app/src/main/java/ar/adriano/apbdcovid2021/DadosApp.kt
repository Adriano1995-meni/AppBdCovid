package ar.adriano.apbdcovid2021

import androidx.fragment.app.Fragment

class DadosApp {
    companion object{
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment
        var enfermeiroSelecionado  : Enfermeiro?= null
        var DestritoSelecionado : Destritos? = null
        var pessoasSelecionado : Pessoas?=null

    }
}