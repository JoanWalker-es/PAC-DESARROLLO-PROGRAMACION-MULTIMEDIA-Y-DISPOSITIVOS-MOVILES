package com.proyecto_final.pac_desarrollo

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.proyecto_final.pac_desarrollo.databinding.Activity2ConsultasBinding

class Activity2_consultas : AppCompatActivity() {
    private lateinit var binding: Activity2ConsultasBinding

    private var aceptar_dialogo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2ConsultasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Recuperamos los datos del intent que nos llega desde Activity2
        val bundle = intent.extras
        val tabla_creada = intent.getBooleanExtra("TABLA_CREADA", false)

        //En el fichero de SharedPreferences guardamos una variable booleana que nos indica si el
        //dialogo se ha mostrado o no. Si se ha mostrado anteriormente, ya no se vuelve a mostrar.
        val preferencias = getSharedPreferences("dialogo", Context.MODE_PRIVATE)
        val editor = preferencias.edit()

        //Extraemos el valor del fichero SharedPreferences
        aceptar_dialogo = preferencias.getBoolean("aceptar_dialogo", false)

        //Comprobamos si se ha mostrado el dialogo anteriormente
        if (!aceptar_dialogo) {
            editor.putBoolean("aceptar_dialogo", true)
            editor.commit()
            showDialogAlertSimple()
        }


        /*
        Al pulsar el botón de consultar los datos, primero comprueba que la tabla está creada.
        Luego revisa que el campo donde insertar el código de alumno no esté vacío. Si está todo
        correcto, crea una instancia de la clase Operaciones_bbdd para sacar los datos y mostrarlos
        en los textView
         */
        binding.btnConsultar.setOnClickListener {

            if (tabla_creada) {
                if (!binding.titCol.text.isNullOrBlank()) {
                    val admin = Operaciones_bbdd(this, "PAC_DESARROLLO", 1)
                    val bd = admin.writableDatabase
                    val fila = bd.rawQuery(
                        "select nombre,apellidos from Alumnos where codigo=${
                            Integer.parseInt(binding.titCol.text.toString())
                        }", null
                    )
                    if (fila.moveToFirst()) {
                        binding.textviewNombre.setText(fila.getString(0))
                        binding.textviewApellido.setText(fila.getString(1))
                    } else
                        Toast.makeText(
                            this,
                            "No existe un alumno con dicho código",
                            Toast.LENGTH_SHORT
                        ).show()
                    bd.close()
                } else {
                    Toast.makeText(
                        this,
                        "NO HAS INTRODUCIDO UN CODIGO DE ALUMNO",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "NO HAY UNA TABLA CREADA", Toast.LENGTH_SHORT).show()
            }

        }

        //Botón para volver a la Activity2
        binding.btnVover.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Función que crea un cuadro de dialogo y lo muestra.
    fun showDialogAlertSimple() {
        AlertDialog.Builder(this)
            .setTitle("Consulta de datos")
            .setMessage("Cada vez que se insertan datos se asigna un código. El primer registro en la base de datos tendra el código 1.")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado
                })
            .setCancelable(false)
            .show()
    }
}