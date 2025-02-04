package com.example.applicationscane

import android.graphics.Bitmap

/**
 * Représente une classe qui génère un identifiant pour chaque instance.
 *
 * @property imageG L'image bitmap associée à l'instance. Peut être nulle.
 * @property id L'identifiant unique généré pour l'instance.
 */
class Generate(val imageG: Bitmap?) {
    companion object {
        /**
         * Un compteur utilisé pour générer des identifiants uniques.
         */
        private var counter: Int = 0

        /**
         * Génère un identifiant unique en incrémentant le compteur.
         *
         * @return L'identifiant unique généré.
         */
        fun generateId(): Int {
            return ++counter
        }
    }

    /**
     * L'identifiant unique de cette instance, généré à l'aide de [generateId].
     */
    val id: Int = generateId()
}