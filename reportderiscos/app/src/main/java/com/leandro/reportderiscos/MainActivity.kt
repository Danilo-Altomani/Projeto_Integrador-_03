package com.leandro.reportderiscos

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextDescription: EditText
    private lateinit var imageViewPhoto: ImageView
    private lateinit var buttonTakePhoto: Button
    private lateinit var buttonSave: Button
    private var currentPhotoPath: String? = null

    private val cameraResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let { imageViewPhoto.setImageBitmap(it) }
                // Aqui você pode salvar a imagem em um arquivo e guardar o caminho (currentPhotoPath)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextDescription = findViewById(R.id.editTextDescription)
        imageViewPhoto = findViewById(R.id.imageViewPhoto)
        buttonTakePhoto = findViewById(R.id.buttonTakePhoto)
        buttonSave = findViewById(R.id.buttonSave)

        buttonTakePhoto.setOnClickListener {
            takePhoto()
        }

        buttonSave.setOnClickListener {
            saveRiskReport()
        }
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            cameraResultLauncher.launch(takePictureIntent)
        } else {
            Toast.makeText(this, "Não foi possível abrir a câmera.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveRiskReport() {
        val description = editTextDescription.text.toString()

        if (description.isNotEmpty()) {
            val message = "Risco registrado!\nDescrição: $description"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            // Aqui, no futuro, você salvará esses dados no Firebase
        } else {
            Toast.makeText(this, "Por favor, adicione uma descrição.", Toast.LENGTH_SHORT).show()
        }
    }
}