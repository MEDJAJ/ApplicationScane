package com.example.applicationscane

import android.graphics.Bitmap



/**
 * Représente un élément scanné avec une image et des informations associées.
 *
 * @property image Image associée au scan.
 * @property information Informations textuelles sur l'élément scanné.
 */
class Scane(val image: Bitmap, val information: String)