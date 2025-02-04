package com.example.applicationscane

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


/**
 * Activité représentant l'écran d'inscription de l'utilisateur.
 * Permet à l'utilisateur de s'inscrire avec un nom, un téléphone, un email et un mot de passe.
 */
class Sign_Up : AppCompatActivity() {

    /** Bouton pour soumettre l'inscription. */
    lateinit var signin: Button

    /** Champ de texte pour entrer le nom de l'utilisateur. */
    lateinit var name: EditText

    /** Champ de texte pour entrer le numéro de téléphone de l'utilisateur. */
    lateinit var phone: EditText

    /** Champ de texte pour entrer l'email de l'utilisateur. */
    lateinit var email: EditText

    /** Champ de texte pour entrer le mot de passe de l'utilisateur. */
    lateinit var password: EditText

    /** Dialog pour afficher une barre de progression lors de l'inscription. */
    lateinit var dialog: AlertDialog

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur et gère l'inscription de l'utilisateur.
     *
     * @param savedInstanceState État précédent de l'activité (peut être null).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        /**
         * initialiser les vues
         */
        signin = findViewById(R.id.signin)
        name = findViewById(R.id.name)
        phone = findViewById(R.id.phone)
        email = findViewById(R.id.mail)
        password = findViewById(R.id.password)
        val result = findViewById<TextView>(R.id.result)
        /**
         * signin button qui permet de valider les donner a entrer
         */
        signin.setOnClickListener {
            val nemevalid = name.text.toString()
            val phonevalid = phone.text.toString()
            val emailvalid = email.text.toString()
            val passwordvalid = password.text.toString()

            /**
             * Expressions régulières pour valider les champs d'un user
             */
            val regexName = "^[A-Za-z]+$"
            val regexPhone = "^(05|06|07)[0-9]{8}$"
            val regexEmail = "^[A-Za-z0-9-_+.]+@[A-Za-z]+\\.[a-z]{2,}$"
            val regexPassword = "^[A-Za-z0-9+-_.]+$"
            /**
             * Vérifie si tous les champs sont valides
             */

            if (nemevalid.matches(regexName.toRegex()) && phonevalid.matches(regexPhone.toRegex()) && emailvalid.matches(regexEmail.toRegex()) && passwordvalid.matches(regexPassword.toRegex())) {
                val user = User(nemevalid, phonevalid, emailvalid, passwordvalid)
                Listes.listeUser.add(user)

                result.text = ""
                /**
                 * Crée et affiche le dialogue de progression personnaliser
                 */

                val progress = layoutInflater.inflate(R.layout.progressbar, null)
                dialog = AlertDialog.Builder(this)
                    .setView(progress)
                    .create()

                dialog.show()
                /**
                 * Attendre 1 seconde avant de rediriger l'utilisateur vers l'écran de connexion
                 */

                Handler().postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }, 1000)

            } else {
                result.text = "Entrer Toutes Les chomps correct et obligatoire"
            }
        }
    }

    /**
     * Méthode appelée lorsque l'activité reprend le focus.
     * Efface les champs de saisie et ferme le dialogue de progression.
     */
    override fun onResume() {
        super.onResume()
        name.text.clear()
        phone.text.clear()
        email.text.clear()
        password.text.clear()
        phone.clearFocus()
        email.clearFocus()
        password.clearFocus()
        /**
         * Si le dialogue de progression est toujours affiché, le fermer
         */

        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}