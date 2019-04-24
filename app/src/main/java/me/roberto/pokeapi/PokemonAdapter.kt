package me.nelsoncastro.pokeapi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_element_pokemon.view.*
import me.nelsoncastro.pokeapi.models.Pokemon

class PokemonAdapter(val items: List<Pokemon>, val clickListener: (Pokemon) -> Unit):RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(vista: ViewGroup, tipoVista: Int): ViewHolder {
        val view = LayoutInflater.from(vista.context).inflate(R.layout.list_element_pokemon, vista, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, posicion: Int) = holder.bind(items[posicion], clickListener)


    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        fun bind(item: Pokemon, eventoClick: (Pokemon) -> Unit) = with(itemView) {
            pokeName.text = item.name
            this.setOnClickListener { eventoClick(item) }
        }
    }
}