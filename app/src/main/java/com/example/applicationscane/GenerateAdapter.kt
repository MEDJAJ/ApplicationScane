package com.example.applicationscane

import android.app.AlertDialog
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


/**
 * Adapter pour afficher une liste d'éléments [Generate] dans un RecyclerView.
 *
 * Cet adapter gère l'affichage des éléments dans une liste, y compris l'image et les informations associées.
 * Il permet également la suppression d'un élément de la liste via un dialogue de confirmation.
 *
 * @property probableList La liste mutable des éléments [Generate] à afficher.
 */
class GenerateAdapter(
    private val probableList: MutableList<Generate>
) : RecyclerView.Adapter<GenerateAdapter.ProbableViewHolder>() {

    /**
     * ViewHolder pour les éléments de la liste.
     *
     * Ce ViewHolder contient les vues pour l'image, les informations textuelles et l'icône de suppression.
     *
     * @property itemView La vue racine du ViewHolder.
     */
    class ProbableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** ImageView pour afficher l'image associée à l'élément. */
        val image: ImageView = itemView.findViewById(R.id.donnerImage)

        /** TextView pour afficher les informations textuelles de l'élément. */
        val information: TextView = itemView.findViewById(R.id.donner)

        /** ImageView pour l'icône de suppression de l'élément. */
        val supprimer: ImageView = itemView.findViewById(R.id.itemActionIcon)
    }

    /**
     * Crée un nouveau ViewHolder en inflatant le layout d'un élément de la liste.
     *
     * @param parent Le ViewGroup parent dans lequel la nouvelle vue sera ajoutée.
     * @param viewType Le type de vue (non utilisé dans cette implémentation).
     * @return Une nouvelle instance de [ProbableViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProbableViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ProbableViewHolder(view)
    }

    /**
     * Lie les données de l'élément à la position [position] avec les vues du ViewHolder.
     *
     * @param holder Le ViewHolder à lier.
     * @param position La position de l'élément dans la liste.
     */
    override fun onBindViewHolder(holder: ProbableViewHolder, position: Int) {
        val produit = probableList[position]
        holder.information.text = "Id : 0${produit.id}"
        holder.image.setImageBitmap(produit.imageG)

        // Gestion du clic sur l'icône de suppression
        holder.supprimer.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("Supprimer")
                .setMessage("Êtes-vous sûr de vouloir supprimer cet élément?")
                .setPositiveButton("OK") { dialog, _ ->
                    probableList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, probableList.size)
                    Toast.makeText(
                        holder.itemView.context,
                        "Cet élément a été supprimé.",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
                .setNegativeButton("Annuler") { dialog, _ ->
                    dialog.cancel()
                }
                .setIcon(R.drawable.supprimer)
            dialog.show()
        }
    }

    /**
     * Retourne le nombre d'éléments dans la liste.
     *
     * @return Le nombre d'éléments dans [probableList].
     */
    override fun getItemCount(): Int = probableList.size
}
