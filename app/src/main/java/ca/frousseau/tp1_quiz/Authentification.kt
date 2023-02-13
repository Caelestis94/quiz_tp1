package ca.frousseau.tp1_quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Authentification : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_start : Button
    lateinit var txt_pseudo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        btn_start = findViewById(R.id.btn_start)
        txt_pseudo = findViewById(R.id.txt_pseudo)


        btn_start.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_start) {
                if (txt_pseudo.text.toString().isNotEmpty()) {
                    val intent = Intent(this, Quiz::class.java)
                    val pref = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = pref.edit()
                    editor.putString("pseudo", txt_pseudo.text.toString())
                    editor.apply()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.pseudo_vide_erreur), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
