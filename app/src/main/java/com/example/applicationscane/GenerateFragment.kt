package com.example.applicationscane
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream

/**
 * Un fragment pour générer et partager des codes QR.
 */
class QRCodeFragment : Fragment() {

    /**
     * Le bitmap du code QR généré.
     */
    private var qrBitmap: Bitmap? = null

    /**
     * Appelée pour que le fragment instancie sa vue d'interface utilisateur.
     *
     * @param inflater L'objet LayoutInflater qui peut être utilisé pour gonfler
     * toutes les vues dans le fragment,
     * @param container Si non-nul, il s'agit de la vue parente à laquelle l'interface utilisateur de ce fragment
     * doit être attachée.
     * @param savedInstanceState Si non-nul, ce fragment est en cours de reconstruction
     * à partir d'un état enregistré précédent, tel que donné ici.
     *
     * @return Retourne la View pour l'interface utilisateur du fragment, ou null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_generate, container, false)
        val productNameInput = view.findViewById<EditText>(R.id.productNameInput)
        val productPriceInput = view.findViewById<EditText>(R.id.productPriceInput)
        val productDescriptionInput = view.findViewById<EditText>(R.id.productDescriptionInput)
        val generateQRButton = view.findViewById<Button>(R.id.generateQRButton)
        val qrImageView = view.findViewById<ImageView>(R.id.qrImageView)
        val partage = view.findViewById<ImageView>(R.id.partage)

        generateQRButton.setOnClickListener {
            val name = productNameInput.text.toString()
            val price = productPriceInput.text.toString()
            val description = productDescriptionInput.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty() && description.isNotEmpty()) {
                val qrContent = "Nom: $name\nPrix: $price\nDescription: $description"
                qrBitmap = generateQRCode(qrContent) // Stocker le bitmap
                qrImageView.setImageBitmap(qrBitmap)
                qrBitmap?.let {
                    Listes.listaGenerate.add(Generate(it))
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Veuillez saisir toutes les informations du produit !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        partage.setOnClickListener {
            qrBitmap?.let { bitmap ->
                val imageUri = saveImageToCache(bitmap)
                if (imageUri != null) {
                    shareImage(imageUri)
                } else {
                    Toast.makeText(requireContext(), "Erreur lors de l'enregistrement de l'image pour le partage.", Toast.LENGTH_SHORT).show()
                }
            } ?: Toast.makeText(requireContext(), "Veuillez d'abord générer un QR !", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    /**
     * Génère un bitmap de code QR à partir du contenu donné.
     *
     * @param content Le contenu à encoder dans le code QR.
     * @return Le bitmap du code QR généré, ou null si une erreur se produit.
     */
    private fun generateQRCode(content: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 500, 500)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Enregistre le bitmap donné dans le répertoire cache de l'application.
     *
     * @param bitmap Le bitmap à enregistrer.
     * @return L'Uri de l'image enregistrée, ou null si une erreur se produit.
     */
    private fun saveImageToCache(bitmap: Bitmap): Uri? {
        return try {
            val file = File(requireContext().cacheDir, "shared_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Partage l'image à l'Uri donnée.
     *
     * @param imageUri L'Uri de l'image à partager.
     */
    private fun shareImage(imageUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Partager l'image avec"))
    }
}
