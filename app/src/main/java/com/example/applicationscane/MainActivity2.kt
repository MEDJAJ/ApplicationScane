package com.example.applicationscane


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



/**
 * MainActivity2 est une activité qui permet d'afficher des listes de produits scannés et générés.
 */
class MainActivity2 : AppCompatActivity() {

    /** Liste mutable contenant les produits scannés. */
    lateinit var listeProduit: MutableList<Scane>

    /** Liste mutable contenant les éléments générés. */
    lateinit var listeGenerate: MutableList<Generate>

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur et configure les gestionnaires d'événements pour les boutons.
     *
     * @param savedInstanceState État précédent de l'activité (peut être null).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        /**
         * Initialisation des vues de cette activity
         */

        val scane = findViewById<Button>(R.id.scane)
        val generate = findViewById<Button>(R.id.generate)
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val imagevide = findViewById<ImageView>(R.id.imagevide)
        val textvide = findViewById<TextView>(R.id.textvide)
        val intoro = findViewById<ImageView>(R.id.intoro)

        /**
         * Met à jour l'affichage des éléments en fonction de la taille de la liste affichée.
         *
         * @param listSize Taille de la liste actuelle.
         */
        fun updateUI(listSize: Int) {
            if (listSize > 0) {
                recycler.visibility = View.VISIBLE
                imagevide.visibility = View.GONE
                textvide.visibility = View.GONE
                intoro.visibility = View.GONE
            } else {
                recycler.visibility = View.GONE
                imagevide.visibility = View.VISIBLE
                textvide.visibility = View.VISIBLE
                intoro.visibility = View.GONE
            }
        }


        /**
         *  scan  bouton qui permet de  obtenu la listes de scan
         */
        scane.setOnClickListener {
            try {
                listeProduit = Listes.listScane
            } catch (e: Exception) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }

            if (this::listeProduit.isInitialized) {
                recycler.layoutManager = LinearLayoutManager(this)
                val scaneAdapter = ProbaleAdapter(listeProduit)
                recycler.adapter = scaneAdapter
            }
            updateUI(listeProduit.size)
        }

        /**
         *  generate  bouton qui permet de  obtenu la listes de generates
         */
        generate.setOnClickListener {
            listeGenerate = Listes.listaGenerate

            if (this::listeGenerate.isInitialized) {
                recycler.layoutManager = LinearLayoutManager(this)
                val generateAdapter = GenerateAdapter(listeGenerate)
                recycler.adapter = generateAdapter
            }
            updateUI(listeGenerate.size)
        }
    }
}

