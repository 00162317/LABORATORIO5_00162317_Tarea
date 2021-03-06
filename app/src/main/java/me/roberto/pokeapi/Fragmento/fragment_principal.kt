package me.roberto.pokeapi.Fragmento

import android.content.Context
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fragment_principal.view.*
import me.roberto.pokeapi.recycler.PokemonAdapter
import me.roberto.pokeapi.R
import me.roberto.pokeapi.models.Pokemon
import me.roberto.pokeapi.utilities.NetworkUtils
import org.json.JSONObject
import java.io.IOException


class fragment_principal : Fragment() {

    private lateinit var viewAdapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    //Crea una interface para poder obtener eventos click para ambas orientaciones.
    interface pokeClickListener {
        fun pokePortrait(pokemon : Pokemon)
        fun landScape(pokemon : Pokemon)
    }

    var listenerTools : pokeClickListener? = null

    lateinit var viewGlobal : View //Esta te sirve para los fetch, porque necesita esas view

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Esta variable te va a servir para obtener todos los componentes xml.
        var view = inflater.inflate(R.layout.fragment_fragment_principal, container, false)

        viewGlobal = view

        FetchPokemonTask().execute("")


        return view
    }

    fun initRecycler(pokemon: MutableList<Pokemon>){
        viewManager = LinearLayoutManager(viewGlobal.context)

        if (viewGlobal.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            viewAdapter = PokemonAdapter(
                pokemon,
                { pokemonItem: Pokemon -> listenerTools?.pokePortrait(pokemonItem) })
            Log.d("Hola", "Port")
        } else{
            viewAdapter = PokemonAdapter(
                pokemon,
                { pokemonItem: Pokemon -> listenerTools?.landScape(pokemonItem) })
            Log.d("Hola", "Land")
        }

        viewGlobal.recyclerPokemonList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }



    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("pokemon",ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String) {
            listPokemon(pokemonInfo,"results")
            }
    }
    fun listPokemon(pokemonInfo: String,name: String){
        val pokemon = if (!pokemonInfo.isEmpty()) {
            val root = JSONObject(pokemonInfo)
            val results = root.getJSONArray(name)
            MutableList(20) { i ->
                Gson().fromJson<Pokemon>(results[i].toString(),Pokemon::class.java)
            }
        } else {
            MutableList(20) { i ->
                Pokemon(i, R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),
                    R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
            }
        }
        initRecycler(pokemon)
    }
    private inner class QueryPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("type",ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }
        override fun onPostExecute(pokemonInfo: String) {
            listPokemon(pokemonInfo,"pokemon")
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is pokeClickListener) {
            listenerTools = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerTools = null
    }

}
