package com.proyecto_final.pac_desarrollo


import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.proyecto_final.pac_desarrollo.databinding.Activity3Binding


class Activity3 : AppCompatActivity() {

    private lateinit var binding:Activity3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= Activity3Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //Botón para lanzar la cámara
        binding.btnCamara.setOnClickListener{
            val camaraintent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camaraLauncher.launch(camaraintent)
        }

        //Volver a la activity 1 eliminando de la pila esta activity
        binding.btnAct1.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Alternativa nueva a startActivityForResult. Al abrir la camara y hacer la foto, cogemos la imagen
    //y la ponemos en el imageview que tenemos en el activity.
    private val camaraLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityresult->
        if(activityresult.resultCode== RESULT_OK){
            val data: Intent? = activityresult.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageViewFoto.setImageBitmap(imageBitmap)
        }else{
            Toast.makeText(this,"NO SE HA GUARDADO NINGUNA IMAGEN",Toast.LENGTH_SHORT).show()
        }
    }

    //Le pedimos los permisos para usar la camara al usuario la primera vez que abrimos esta activity
    override fun onStart() {
        super.onStart()
        val permissions = arrayOf<String>(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissions(permissions, 0)
    }

}