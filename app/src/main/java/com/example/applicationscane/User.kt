package com.example.applicationscane



/**
 * Classe représentant un utilisateur avec des informations personnelles et un identifiant unique.
 *
 * @property name Nom de l'utilisateur.
 * @property number Numéro de téléphone de l'utilisateur.
 * @property email Adresse e-mail de l'utilisateur.
 * @property password Mot de passe de l'utilisateur (ne devrait pas être stocké en texte brut).
 */
class User(var name: String, var number: String, var email: String, val password: String) {

    companion object {
        /** Compteur d'ID unique pour tous les utilisateurs. */
        private var counter: Int = 0

        /**
         * Génère et retourne un nouvel ID unique.
         *
         * @return Un entier représentant l'ID de l'utilisateur.
         */
        private fun generateId(): Int {
            return counter++
        }
    }

    /** Identifiant unique attribué à chaque utilisateur. */
    val id: Int = generateId()
}