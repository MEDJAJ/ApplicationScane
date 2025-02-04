package com.example.applicationscane

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView


/**
 * ScaneFragment est un fragment qui gère la lecture de codes-barres et QR codes à l'aide d'une caméra.
 * Il utilise la bibliothèque ZXing pour la capture et le décodage des codes.
 */
class ScaneFragment : Fragment() {

    /** Vue pour afficher l'aperçu de la caméra et scanner les codes-barres. */
    private lateinit var barcodeView: CompoundBarcodeView

    /** Texte affiché pour indiquer le résultat du scan. */
    private lateinit var textScanning: TextView

    /** Bouton permettant d'activer ou désactiver le flash. */
    private lateinit var flashButton: ImageButton

    /** Indique si le flash est activé ou non. */
    private var isFlashOn = false

    /**
     * Méthode appelée lors de la création de la vue du fragment.
     * Initialise les composants de l'interface et démarre le scanner.
     *
     * @param inflater Utilisé pour gonfler la mise en page du fragment.
     * @param container Vue parent contenant le fragment.
     * @param savedInstanceState État précédent du fragment (peut être null).
     * @return La vue du fragment initialisée.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_scane, container, false)
        /**
         * initialiser les vues
         */
        barcodeView = view.findViewById(R.id.scanArea)
        textScanning = view.findViewById(R.id.textScanning)
        flashButton = view.findViewById(R.id.flashButton)
        /**
         * Vérifie si la permission de la caméra est accordée
         */
        checkCameraPermission()
        /**
         *  Démarre la détection des codes-barres en continu
         */

        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    textScanning.text = "Résultat : ${it.text}"

                    val capturedBitmap: Bitmap = it.bitmap
                    val text = it.text.toString()
                    /**
                     * Vérifie si l'élément existe déjà dans la liste avant de l'ajouter
                     */

                    if (Listes.listScane.none { scane -> scane.information == text }) {
                        textScanning.text = "Résultat : $text"
                        Listes.listScane.add(Scane(capturedBitmap, text))
                    }
                }
            }
        })
        /**
         * Gestion du flash
         */
        flashButton.setOnClickListener {
            toggleFlash()
        }

        return view
    }

    /**
     * Active ou désactive le flash de l'appareil photo.
     */
    private fun toggleFlash() {
        try {
            isFlashOn = !isFlashOn
            barcodeView.barcodeView.setTorch(isFlashOn)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Reprend le scanner lorsque le fragment est visible.
     */
    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    /**
     * Met en pause le scanner lorsque le fragment est en arrière-plan.
     */
    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    /**
     * Vérifie si l'application a la permission d'utiliser la caméra.
     * Si ce n'est pas le cas, elle demande la permission à l'utilisateur.
     */
    private fun checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 100)
        }
    }

    /**
     * Gère la réponse de l'utilisateur lors de la demande de permission pour la caméra.
     *
     * @param requestCode Code de la requête de permission.
     * @param permissions Liste des permissions demandées.
     * @param grantResults Résultat de la demande de permission.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                requireContext(),
                "Permission to use camera is required",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}



