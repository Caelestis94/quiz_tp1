package ca.frousseau.tp1_quiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Configuration : AppCompatActivity(), View.OnClickListener {

    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var btn_accepter : Button
    lateinit var txt_pseudo_settings : EditText
    var pseudo = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pseudo = pref.getString("pseudo", "")!!

        toolbar = findViewById(R.id.toolbar_config)
        btn_accepter = findViewById(R.id.btn_accepter)
        txt_pseudo_settings = findViewById(R.id.txt_pseudo_settings)
        txt_pseudo_settings.setText(pseudo)
        btn_accepter.setOnClickListener(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
    * Sauvegarde le pseudo dans les préférences
     * @param pseudo Le pseudo à sauvegarder
     * @param context Le contexte de l'application
    */
    fun savePseudo(pseudo: String, context: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString("pseudo", pseudo)
        editor.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        if(v != null){
            if(v.id == R.id.btn_accepter){
                pseudo = txt_pseudo_settings.text.toString()
                savePseudo(pseudo, this)
                finish()
                Toast.makeText(this, getString(R.string.pseudo_modifie), Toast.LENGTH_SHORT).show()
            }
        }
    }
}