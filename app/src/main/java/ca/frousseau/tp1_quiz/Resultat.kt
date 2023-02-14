package ca.frousseau.tp1_quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Resultat : AppCompatActivity(), View.OnClickListener {

    var score = 0
    var nb_questions = 0
    var pseudo = ""

    lateinit var text_pseudo : TextView
    lateinit var text_score : TextView
    lateinit var text_score_pourcentage : TextView
    lateinit var btn_share : Button
    lateinit var btn_rejouer : Button
    lateinit var toolbar_resultat : Toolbar
    lateinit var image_resultat : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultat)

        score = intent.getIntExtra("score", 0)
        nb_questions = intent.getIntExtra("nb_questions", 0)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pseudo = pref.getString("pseudo", "")!!

        text_pseudo = findViewById(R.id.text_pseudo)
        text_score = findViewById(R.id.text_score)
        text_score_pourcentage = findViewById(R.id.text_score_pourcentage)
        btn_share = findViewById(R.id.btn_share)
        btn_rejouer = findViewById(R.id.btn_restart)
        toolbar_resultat = findViewById(R.id.toolbar_resultat)
        image_resultat = findViewById(R.id.image_resultat)
        btn_share.setOnClickListener(this)
        btn_rejouer.setOnClickListener(this)
        setSupportActionBar(toolbar_resultat)

        val resultat_quiz :Int = score*100/nb_questions

        if(resultat_quiz >= 60) {
            image_resultat.setImageResource(R.drawable.trophy)
        }
        else {
            image_resultat.setImageResource(R.drawable.lost)
        }

        text_pseudo.text = "${getText(R.string.resultat_pseudo)} $pseudo"
        text_score.text = "${getText(R.string.resultat_score)} $score/$nb_questions"
        text_score_pourcentage.text = "${getText(R.string.resultat_score_percent)} ${score*100/nb_questions}%"
    }

    override fun onClick(v: View?) {
        if(v != null) {
            if(v.id == R.id.btn_share) {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, "${getText(R.string.resultat_score_share)} $score/$nb_questions")
                intent.type = "text/plain"
                startActivity(intent)
            }
            else if(v.id == R.id.btn_restart) {
                val intent = Intent(this, Quiz::class.java)
                intent.putExtra("pseudo", pseudo)
                startActivity(intent)
            }
        }
    }
}