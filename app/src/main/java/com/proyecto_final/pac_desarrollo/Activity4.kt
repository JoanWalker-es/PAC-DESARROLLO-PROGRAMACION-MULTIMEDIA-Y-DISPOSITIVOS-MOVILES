package com.proyecto_final.pac_desarrollo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.proyecto_final.pac_desarrollo.databinding.Activity4Binding

class Activity4 : AppCompatActivity() {

    private lateinit var binding:Activity4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= Activity4Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //Volver a la activity 1 eliminando de la pila esta activity
        binding.btnAct1.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}