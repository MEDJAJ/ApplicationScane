package com.example.applicationscane

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



/**
 * Activité de connexion.
 *
 * Cette activité gère le processus de connexion de l'utilisateur,
 * y compris la validation des informations d'identification et la redirection
 * vers l'activité principale en cas de succès.
 */
class LoginActivity : AppCompatActivity() {

    /** Barre de progression affichée pendant la connexion. */
    private lateinit var progress: ProgressBar

    /** Champ de texte pour l'adresse e-mail. */
    private lateinit var mail: EditText

    /** Champ de texte pour le mot de passe. */
    private lateinit var password: EditText

    /** TextView affichant un message d'erreur si les identifiants sont incorrects. */
    private lateinit var verifier: TextView

    /**
     * Appelée lorsque l'activité est créée pour la première fois.
     *
     * @param savedInstanceState Si le fragment est recréé à partir d'un état
     * enregistré précédent, ce Bundle contient les données d'état d'origine.
     * Notez que la valeur sera nulle la première fois qu'un fragment est créé.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val log = findViewById<Button>(R.id.passwordd)
        val flech = findViewById<ImageView>(R.id.flechgauche)
        progress = findViewById(R.id.progressView)
        mail = findViewById(R.id.mailEdit)
        password = findViewById(R.id.passwordEditText)
        verifier = findViewById(R.id.verifier)
        val up = findViewById<TextView>(R.id.upp)

        /**
         * flech Button qui permet de arreter l'application
         */
        flech.setOnClickListener {
            finishAffinity()
        }
        /**
         * login qui permet de valider email et password pour accéder l'application
         */
        log.setOnClickListener {
            val mailV = mail.text.toString()
            val passwordV = password.text.toString()

            if (mailV.isNotEmpty() && passwordV.isNotEmpty()) {
                if (Listes.listeUser.isNotEmpty()){
                    val donnerCorrect = Listes.listeUser.find { it.email == mailV && it.password == passwordV }

                    if (donnerCorrect != null) {
                        progress.visibility = View.VISIBLE
                        verifier.visibility = View.GONE

                        Handler().postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }, 1000)
                    } else {
                        verifier.visibility = View.VISIBLE
                    }
                }
            } else {
                verifier.visibility = View.VISIBLE
            }
        }
        /**
         * up Button qui permet de transmitre de activity nommée Sign_Up
         */
        up.setOnClickListener {
            val intent = Intent(this, Sign_Up::class.java)
            startActivity(intent)
        }


        /**
         * qui verifier le version android Style de la barre d'état (si API >= Lollipop)
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = android.graphics.Color.parseColor("#000000") // Couleur noire
        }
    }

    /**
     * Appelée lorsque l'activité reprend.
     *
     * Cette méthode est appelée après onCreate() lorsque l'activité est
     * initialement démarrée ou lorsqu'elle est reprise après avoir été
     * mise en pause ou arrêtée.
     */
    override fun onResume() {
        super.onResume()
        progress.visibility = View.GONE
        verifier.visibility = View.GONE
        mail.text.clear()
        password.text.clear()
        password.clearFocus()
    }
}