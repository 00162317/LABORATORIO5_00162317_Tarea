package me.roberto.pokeapi.recycler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.roberto.pokeapi.Fragmento.fragment_second
import me.roberto.pokeapi.R
import me.roberto.pokeapi.utilities.AppConstants

class PokemonViewer : AppCompatActivity() {
    val keyUri: AppConstants? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_element_pokemon)

        val uri:String = this.intent.extras.getString(keyUri.toString())
        var fragment = fragment_second.newInstance(uri)
        supportFragmentManager.beginTransaction().replace(R.id.ll_second, fragment).commit()
    }
}