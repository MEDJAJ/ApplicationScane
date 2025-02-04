package com.example.applicationscane

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions




/**
 * @class FragementProfil
 * @brief Fragment représentant le profil de l'utilisateur.
 *
 * Ce fragment affiche les informations de l'utilisateur et permet de les modifier.
 */
class FragementProfil : Fragment() {

    /** @brief TextView affichant le nom de l'utilisateur. */
    private lateinit var userName: TextView

    /** @brief TextView affichant l'email de l'utilisateur. */
    private lateinit var userEmail: TextView

    /** @brief TextView affichant le numéro de téléphone de l'utilisateur. */
    private lateinit var userPhone: TextView

    /** @brief ImageView affichant l'image de profil de l'utilisateur. */
    private lateinit var profileImage: ImageView

    /** @brief Bouton permettant de modifier les informations de l'utilisateur. */
    private lateinit var modifyButton: Button

    /** @brief Boîte de dialogue pour modifier les informations de l'utilisateur. */
    private lateinit var alertDialog: AlertDialog

    /**
     * @brief Crée et retourne la vue associée au fragment.
     *
     * @param inflater Le LayoutInflater utilisé pour gonfler la vue.
     * @param container Le ViewGroup parent auquel la vue est attachée.
     * @param savedInstanceState L'état précédemment enregistré du fragment.
     * @return La vue gonflée pour le fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate le layout pour ce fragment
        val view = inflater.inflate(R.layout.fragment_fragement_profil, container, false)

        // Initialisation des vues
        userName = view.findViewById(R.id.userName)
        userEmail = view.findViewById(R.id.userEmail)
        userPhone = view.findViewById(R.id.userPhone)
        profileImage = view.findViewById(R.id.profileImage)
        modifyButton = view.findViewById(R.id.modifyButton)

        // Chargement des données utilisateur
        loadUserData()

        // Chargement de l'image de profil
        Glide.with(this)
            .load(R.drawable.s)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(profileImage)

        // Ajout de l'écouteur de clic pour la modification des informations
        modifyButton.setOnClickListener {
            showEditDialog()
        }

        return view
    }

    /**
     * @brief Charge les informations de l'utilisateur et les affiche dans les TextView.
     */
    private fun loadUserData() {
        if (Listes.listeUser.isNotEmpty()) {
            val user = Listes.listeUser[0]
            userName.text = user.name
            userEmail.text = user.email
            userPhone.text = user.number
        }
    }

    /**
     * @brief Affiche une boîte de dialogue permettant de modifier les informations de l'utilisateur.
     */
    private fun showEditDialog() {
        val viewModifier = layoutInflater.inflate(R.layout.alertmodifietr, null)
        val alertName = viewModifier.findViewById<EditText>(R.id.nameEditText)
        val alertEmail = viewModifier.findViewById<EditText>(R.id.emailEditText)
        val alertPhone = viewModifier.findViewById<EditText>(R.id.PHONEEditText)
        val alertButton = viewModifier.findViewById<Button>(R.id.submitButton)

        if (Listes.listeUser.isEmpty()) return

        val user = Listes.listeUser[0]
        alertName.setText(user.name)
        alertEmail.setText(user.email)
        alertPhone.setText(user.number)

        alertButton.setOnClickListener {
            val newName = alertName.text.toString()
            val newEmail = alertEmail.text.toString()
            val newPhone = alertPhone.text.toString()

            // Mise à jour des données utilisateur
            user.name = newName
            user.email = newEmail
            user.number = newPhone

            // Mise à jour de l'interface utilisateur
            userName.text = user.name
            userEmail.text = user.email
            userPhone.text = user.number

            val activity = MainActivity
            val title = activity.titleheader
            val phone = activity.numberheader
            val mail = activity.mailheader
            title.text = user.name
            phone.text = user.number
            mail.text = user.email

            alertDialog.dismiss()
        }

        alertDialog = AlertDialog.Builder(requireContext())
            .setView(viewModifier)
            .create()
        alertDialog.show()
    }
}