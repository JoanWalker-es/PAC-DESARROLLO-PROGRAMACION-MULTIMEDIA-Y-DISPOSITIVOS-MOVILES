package com.proyecto_final.pac_desarrollo

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.proyecto_final.pac_desarrollo.databinding.Activity2Binding
import android.widget.Toast
import android.database.Cursor


class Activity2 : AppCompatActivity() {
    private lateinit var binding: Activity2Binding

    private var tabla_exits = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Toast.makeText(applicationContext, "Estas en la Activity 2", Toast.LENGTH_SHORT).show()

        val preferencias = getSharedPreferences("base_datos", Context.MODE_PRIVATE)
        val editor = preferencias.edit()

        //Extraemos el valor del fichero SharedPreferences
        tabla_exits = preferencias.getBoolean("Tabla", false)

        /*Creación de tablas
        Primero comprobamos si anteriormente se ha creado una tabla mirando el fichero SharedPreferences.
        Si no se ha creado, crea una instancia de la clase Operaciones_bbdd que hereda de SQLiteOpenHelper
        donde en la funcion onCreate hemos definido la creación de la tabla. Si la crea, guarda en el fichero
        SharedPreferences un true con la key Tabla. Asimismo como algunas veces me daba error ponemos de manera
        manual la variable tabla_exits a true para que se puedan insertar datos o consultarlos.
        */
        binding.btnCrear.setOnClickListener {

            if (tabla_exits) {
                Toast.makeText(applicationContext, "La tabla ya estaba creada", Toast.LENGTH_LONG)
                    .show()
            } else {
                val bd = Operaciones_bbdd(this, "PAC_DESARROLLO", 1)
                Toast.makeText(applicationContext, "Tabla creada correctamente", Toast.LENGTH_LONG)
                    .show()

                editor.putBoolean("Tabla", true)
                editor.commit()
                tabla_exits = true
            }

        }


        /*Insertar datos bbdd
        Primero revisamos que la variable que nos dice si la tabla está creada es true. Luego comprobamos
        que los campos de texto no esten vacíos con la función compruebaTexto. Si está todo correcto
        creamos una instancia de la clase Operaciones_bbdd con los datos de nuestra base de datos y la ponemos
        en modo edición. Le añadimos los valores que tenemos en los edittext y los insertamos.
         */
        binding.btnInsertar.setOnClickListener {

            if (tabla_exits) {
                if (compruebaTexto()) {
                    val admin = Operaciones_bbdd(this, "PAC_DESARROLLO", 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombre", binding.titCol1.text.toString())
                    registro.put("apellidos", binding.titCol2.text.toString())
                    bd.insert("Alumnos", null, registro)
                    bd.close()
                    Toast.makeText(
                        applicationContext,
                        "Datos insertados correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                    limpiaCampos()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No se han podido insertar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "NO SE HA CREADO LA TABLA", Toast.LENGTH_LONG).show()
            }


        }

        /*Consulta datos
        Al hacer click en el botón de consultar datos, abrimos una nueva activity a la que le hemos
        pasado un intent con el valor de la variable tabla_exits. Así verificamos que la tabla existe
        antes de intentar consultar datos.
         */
        binding.btnConsultar.setOnClickListener {
            val intent = Intent(this, Activity2_consultas::class.java)
            intent.putExtra("TABLA_CREADA", tabla_exits)
            startActivity(intent)
        }


        //Volver a la activity 1 eliminando de la pila esta activity
        binding.btnAct1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    //Funcion que comprueba que los campos no estén vacíos
    private fun compruebaTexto(): Boolean {
        if (binding.titCol1.text.isNullOrBlank()) {
            Toast.makeText(
                applicationContext,
                "No hay datos que insertar en el campo nombre",
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (binding.titCol2.text.isNullOrBlank()) {
            Toast.makeText(
                applicationContext,
                "No hay datos que insertar en el campo apellidos",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    //Funcion que limpia los dos edittext
    private fun limpiaCampos() {
        binding.titCol1.setText("")
        binding.titCol2.setText("")
    }


}