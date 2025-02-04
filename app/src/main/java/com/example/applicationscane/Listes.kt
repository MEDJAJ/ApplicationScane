package com.example.applicationscane



/**
 * Objet contenant les listes utilisées par l'application pour stocker les données.
 *
 * Cet objet fournit un emplacement centralisé pour accéder et manipuler les listes
 * de données utilisées dans l'application, notamment les données générées, scannées
 * et les informations utilisateur.
 */
object Listes {

    /**
     * Liste mutable d'objets [Generate] contenant les données des codes QR générés.
     *
     * Cette liste permet de stocker et de gérer les informations relatives aux
     * codes QR qui ont été générés par l'application, telles que les images
     * et les données associées.
     */
    val listaGenerate = mutableListOf<Generate>()

    /**
     * Liste mutable d'objets [Scane] contenant les données des codes QR scannés.
     *
     * Cette liste permet de stocker et de gérer les informations relatives aux
     * codes QR qui ont été scannés par l'application, telles que les données
     * extraites et les informations de lecture.
     */
    val listScane = mutableListOf<Scane>()

    /**
     * Liste mutable d'objets [User] contenant les informations des utilisateurs.
     *
     * Cette liste permet de stocker et de gérer les informations relatives aux
     * utilisateurs de l'application, telles que leurs noms, adresses e-mail et
     * autres détails.
     */
    val listeUser = mutableListOf<User>()
}