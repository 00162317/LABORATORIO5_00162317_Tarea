package me.nelsoncastro.pokeapi

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewer_element_pokemon.*
import me.nelsoncastro.pokeapi.models.Pokemon
import me.nelsoncastro.pokeapi.utilities.NetworkUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class PokemonViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_element_pokemon)

        val uri:String = this.intent.extras.getString("CLAVIER")
        var fragment = fragment_second.newInstance(uri)
        supportFragmentManager.beginTransaction().replace(R.id.ll_second, fragment).commit()
    }
}