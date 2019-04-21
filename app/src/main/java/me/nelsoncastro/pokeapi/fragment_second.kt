package me.nelsoncastro.pokeapi

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fragment_second.*
import kotlinx.android.synthetic.main.fragment_fragment_second.view.*
import me.nelsoncastro.pokeapi.models.Pokemon
import me.nelsoncastro.pokeapi.utilities.NetworkUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class fragment_second : Fragment() {

    lateinit var uri : String
    lateinit var viewGlobal : View

    companion object {
        fun newInstance(uri : String) : fragment_second{
            var pokeFragment = fragment_second()
            pokeFragment.uri = uri
            return pokeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Esta variable te va a servir para obtener todos los componentes xml.
        var view = inflater.inflate(R.layout.fragment_fragment_second, container, false)

        view.collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        view.collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        viewGlobal = view

        FetchPokemonTask().execute(uri)

        return view
    }

    fun init(pokemon: Pokemon){
        Picasso.with(viewGlobal.context)
            .load(pokemon.sprite)
            .resize((viewGlobal.context.resources.displayMetrics.widthPixels/viewGlobal.context.resources.displayMetrics.density).toInt(),256)
            .centerCrop()
            .error(R.drawable.ic_pokemon_go)
            .into(viewGlobal.app_bar_image_viewer)
        viewGlobal.collapsingtoolbarviewer.title = pokemon.name
        viewGlobal.weight.text = pokemon.weight
        viewGlobal.height_fragment.text = pokemon.height
        viewGlobal.fstType.text = pokemon.fsttype
        viewGlobal.sndType.text = pokemon.sndtype
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home -> {activity?.onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val url = query[0]
            val pokeAPI = Uri.parse(url).buildUpon().build()
            val finalurl = try {
                URL(pokeAPI.toString())
            } catch (e: MalformedURLException){
                URL("")
            }

            return try {
                NetworkUtils().getResponseFromHttpUrl(finalurl)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String) {
            val pokemon = if (!pokemonInfo.isEmpty()) {
                val root = JSONObject(pokemonInfo)
                val sprites = root.getString("sprites")
                val types = root.getJSONArray("types")
                val fsttype = JSONObject(types[0].toString()).getString("type")
                val sndtype = try { JSONObject(types[1].toString()).getString("type") }catch (e: JSONException){ "" }

                Pokemon(root.getInt("id"),
                    root.getString("name").capitalize(),
                    JSONObject(fsttype).getString("name").capitalize(),
                    if(sndtype.isEmpty()) " " else JSONObject(sndtype).getString("name").capitalize(),
                    root.getString("weight"), root.getString("height"), root.getString("location_area_encounters"),
                    JSONObject(sprites).getString("front_default"))

            } else {
                Pokemon(0, R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
            }
            init(pokemon)
        }
    }

}
