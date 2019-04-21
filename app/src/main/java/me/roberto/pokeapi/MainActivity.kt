package me.nelsoncastro.pokeapi

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import me.nelsoncastro.pokeapi.models.Pokemon
import me.nelsoncastro.pokeapi.utilities.NetworkUtils
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity(), fragment_principal.pokeClickListener {
    override fun pokePortrait(pokemon: Pokemon) {
        startActivity(Intent(this, PokemonViewer::class.java).putExtra("CLAVIER", pokemon.url))
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
            supportFragmentManager.beginTransaction().replace(R.id.ll_principal, fragment_principal()).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.ll_principal, fragment_principal()).commit()
        }
    }
}
