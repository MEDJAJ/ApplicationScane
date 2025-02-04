package com.example.applicationscane

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



/**
 * Adaptateur pour afficher une liste d'éléments [Scane] dans un RecyclerView.
 *
 * @property probableList Une liste mutable d'éléments [Scane] à afficher.
 */
class ProbaleAdapter(
    private val probableList: MutableList<Scane>
) : RecyclerView.Adapter<ProbaleAdapter.ProbableViewHolder>() {

    /**
     * ViewHolder pour contenir les vues de chaque élément du RecyclerView.
     *
     * @property itemView La vue racine de la mise en page de l'élément.
     * @property image L'ImageView pour afficher l'image de l'élément [Scane].
     * @property information Le TextView pour afficher les informations de l'élément [Scane].
     * @property supprimer L'ImageView pour l'action de suppression de l'élément [Scane].
     */
    class ProbableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.donnerImage)
        val information: TextView = itemView.findViewById(R.id.donner)
        val supprimer: ImageView = itemView.findViewById(R.id.itemActionIcon)
    }

    /**
     * Crée une nouvelle instance de [ProbableViewHolder] en gonflant la mise en page de l'élément.
     *
     * @param parent Le ViewGroup parent dans lequel la nouvelle vue sera ajoutée.
     * @param viewType Le type de la vue (non utilisé dans cette implémentation).
     * @return Une nouvelle instance de [ProbableViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProbableViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ProbableViewHolder(view)
    }

    /**
     * Lie les données d'un élément [Scane] aux vues dans le [ProbableViewHolder].
     *
     * @param holder Le [ProbableViewHolder] auquel les données sont liées.
     * @param position La position de l'élément dans [probableList].
     */
    override fun onBindViewHolder(holder: ProbableViewHolder, position: Int) {
        val produit = probableList[position]
        holder.information.text = produit.information
        holder.image.setImageBitmap(produit.image)

        // Définir un écouteur de clic pour le bouton de suppression
        holder.supprimer.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("Supprimer")
                .setMessage("Êtes-vous sûr de vouloir supprimer cet élément ?")
                .setPositiveButton("OK") { d, _ ->
                    // Supprime l'élément de la liste et notifie l'adaptateur
                    probableList.remove(produit)
                    notifyDataSetChanged()
                    Toast.makeText(holder.itemView.context, "Cet élément a été supprimé", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Annuler") { d, _ ->
                    d.cancel() // Ferme la boîte de dialogue
                }
                .setIcon(R.drawable.supprimer) // Définit l'icône de la boîte de dialogue
            dialog.show() // Affiche la boîte de dialogue
        }
    }

    /**
     * Retourne le nombre total d'éléments dans [probableList].
     *
     * @return La taille de [probableList].
     */
    override fun getItemCount(): Int = probableList.size
}