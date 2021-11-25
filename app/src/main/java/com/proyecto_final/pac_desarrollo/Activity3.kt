package com.proyecto_final.pac_desarrollo


import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.proyecto_final.pac_desarrollo.databinding.Activity3Binding


class Activity3 : AppCompatActivity() {

    private lateinit var binding: Activity3Binding

    private var permiso_camara = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //Solicitamos los permisos con el registerForActivityResult
        solitandoPermisos.launch(Manifest.permission.CAMERA)


        //Botón para lanzar la cámara
        binding.btnCamara.setOnClickListener {
            if (permiso_camara) {
                val camaraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                camaraLauncher.launch(camaraintent)
            } else {
                Toast.makeText(
                    this,
                    "NO SE HAN CONCEDIDO PERMISOS PARA ACCEDER A LA CAMARA",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //Volver a la activity 1 eliminando de la pila esta activity
        binding.btnAct1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Alternativa nueva a startActivityForResult. Al abrir la camara y hacer la foto, cogemos la imagen
    //y la ponemos en el imageview que tenemos en el activity.
    private val camaraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityresult ->
            if (activityresult.resultCode == RESULT_OK) {
                val data: Intent? = activityresult.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.imageViewFoto.setImageBitmap(imageBitmap)
            } else {
                Toast.makeText(this, "NO SE HA GUARDADO NINGUNA IMAGEN", Toast.LENGTH_SHORT).show()
            }
        }

    //Le pedimos los permisos para usar la camara al usuario la primera vez que abrimos esta activity. Una vez
    //que el usuario acepte los permisos, estos quedan registrados.
    private val solitandoPermisos =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permiso ->
            if (permiso) {
                permiso_camara = true
            } else {
                Toast.makeText(this, "NO SE HAN CONCEDIDO PERMISOS", Toast.LENGTH_SHORT).show()
                permiso_camara = false
            }
        }


}