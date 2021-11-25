package com.proyecto_final.pac_desarrollo

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.proyecto_final.pac_desarrollo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //Creamos una variable de tipo MediaPlayer
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Seleccionamos el tema principal para que no de error con el splashscreen
        setTheme(R.style.Theme_PAC_DESARROLLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //Iniciamos la función para iniciar la variable MediaPlayer y poder reproducir la canción.
        initAudio()

        //Ir a la Activity 2 y parar en caso de que se estuviese reproduciendo la música
        binding.btnAct2.setOnClickListener {
            stopAudio()
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }
        //Ir a la Activity 3 y parar en caso de que se estuviese reproduciendo la música
        binding.btnAct3.setOnClickListener {
            stopAudio()
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }
        //Ir a la Activity 4 y parar en caso de que se estuviese reproduciendo la música
        binding.btnAct4.setOnClickListener {
            stopAudio()
            val intent = Intent(this, Activity4::class.java)
            startActivity(intent)
        }

    }

    //Iniciamos la variable Mediaplayer con la canción asociada
    private fun initAudio() {
        mp = MediaPlayer.create(this, R.raw.musica)
        /*Si termina la canción, con este listener lanzamos el metodo sotpAudio que para la
        canción y creamos una notificación con un toast
         */
        mp.setOnCompletionListener { mp ->
            stopAudio()
            Toast.makeText(this, getString(R.string.audio_terminado), Toast.LENGTH_LONG).show()
        }
        /*
        Si pulsamos el botón del play lo primero que hace es comprobar que la canción se escucha
        o no con este if. Si se está reproduciendo llamamos al método pauseAudio y la paramos,
        y si no, llamamos al método playAudio para seguir reproduciendola.
         */
        binding.btnPlay.setOnClickListener {
            if (mp.isPlaying) {
                pauseAudio()
            } else {
                playAudio()
            }
        }
        //Si pulsamos el botón del stop, llamará a la función stop y dejará de funcionar
        binding.btnStop.setOnClickListener {
            stopAudio()
        }

    }

    /*Creamos la funciones que reproducen, pausan y paran la canción. Hemos introducido una línea
    para que cambie el texto del botón PLAY. Así, cuando está reproduciendo la canción aparecerá
    Pause para parar la canción, y cuando esté parada volvemos a poner el texto Play.
     */
    private fun playAudio() {
        mp.start()
        binding.btnPlay.text = getString(R.string.PAUSE)
    }

    private fun stopAudio() {
        mp.stop()
        mp.prepare()
        mp.seekTo(0)
        binding.btnPlay.text = getString(R.string.PLAY)
    }

    private fun pauseAudio() {
        mp.pause()
        binding.btnPlay.text = getString(R.string.PLAY)
    }

}