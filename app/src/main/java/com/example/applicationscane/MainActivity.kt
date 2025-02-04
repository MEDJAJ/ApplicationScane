package com.example.applicationscane

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

/**
 * Classe principale pour la gestion de l'interface utilisateur et des interactions dans l'application.
 */
class MainActivity : AppCompatActivity() {
companion object{
    lateinit var mailheader:TextView
    lateinit var numberheader:TextView
    lateinit var titleheader:TextView
}
    /**
     * Initialise l'interface utilisateur et configure les écouteurs d'événements.
     * @param savedInstanceState État de l'instance précédente si disponible.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Initialisation des vues
         */

        val flonting: FloatingActionButton = findViewById(R.id.fab)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val generate: ImageView = bottomNavigationView.findViewById(R.id.generate)
        val history: ImageView = findViewById(R.id.historyy)
        val navigation: NavigationView = findViewById(R.id.navigation_view)
        val index= navigation.getHeaderView(0)
        val profilheader: ImageView = index.findViewById(R.id.header_image)
         mailheader = index.findViewById(R.id.header_mail)
         numberheader = index.findViewById(R.id.header_number)
         titleheader = index.findViewById(R.id.header_title)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val buttonNotification: Button = findViewById(R.id.buttonSettings)
        val navButton: ImageView = findViewById(R.id.nav)

        /**
         * Remplissage des informations utilisateur
         */

        val element = Listes.listeUser[0]
        mailheader.text = element.email
        numberheader.text = element.number
        titleheader.text = element.name

        /**
         * Chargement de l'image du profil avec Glide
         */

        Glide.with(this)
            .load(R.drawable.s)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(profilheader)

        /**
         *profilHeader Image View d'un profil  pour le clic sur l'image de profil qui permet de afficher détail de profil
         */

        profilheader.setOnClickListener {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, FragementProfil())
                    .addToBackStack("true")
                    .commit()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        /**
         *  flonting bouton qui permet de remplacer Activity une fragment nommée ScaneFragment
         */

        flonting.setOnClickListener {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, ScaneFragment())
                    .addToBackStack("true")
                    .commit()
            }
        }

        /**
         *  generate bouton qui permet de remplacer Activity une fragment nommée QRCodeFragment
         */

        generate.setOnClickListener {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, QRCodeFragment())
                    .addToBackStack("true")
                    .commit()
            }
        }

        /**
         *history bouton qui permet de transméttre d'un MainActivity2
         */

        history.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        /**
         * buttonNotification button pour afficher les notification
         */

        buttonNotification.setOnClickListener {
            Toast.makeText(this, "Notification clicked", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        /**
         * navButton Image view pour ovérte drawerLayout
         */

        navButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}