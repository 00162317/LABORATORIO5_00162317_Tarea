package me.roberto.pokeapi.main

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import me.roberto.pokeapi.models.Pokemon
import me.roberto.pokeapi.Fragmento.fragment_principal
import me.roberto.pokeapi.Fragmento.fragment_second
import me.roberto.pokeapi.recycler.PokemonAdapter
import me.roberto.pokeapi.recycler.PokemonViewer
import me.roberto.pokeapi.R
import me.roberto.pokeapi.utilities.AppConstants


class MainActivity : AppCompatActivity(), fragment_principal.pokeClickListener {
    val keyUri: AppConstants? = null
    override fun pokePortrait(pokemon: Pokemon) {
        startActivity(Intent(this, PokemonViewer::class.java).putExtra(keyUri.toString(), pokemon.url))
    }

    override fun landScape(pokemon: Pokemon) {
        var fragment = fragment_second.newInstance(pokemon.url)
        supportFragmentManager.beginTransaction().replace(R.id.ll_second, fragment).commit()
    }

    private lateinit var viewAdapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            supportFragmentManager.beginTransaction().replace(
                R.id.ll_principal,
                fragment_principal()
            ).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(
                R.id.ll_principal,
                fragment_principal()
            ).commit()
        }
    }
}
