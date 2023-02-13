package ca.frousseau.tp1_quiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*

class Quiz : AppCompatActivity() {

    var score = 0
    var questions = creerQuestions()
    var progress_inc = 100 / questions.size
    var nb_questions = questions.size
    var pseuso = ""


    lateinit var progress_quiz : ProgressBar
    lateinit var txt_question : TextView
    lateinit var choix_un : RadioButton
    lateinit var choix_deux : RadioButton
    lateinit var choix_trois : RadioButton
    lateinit var choix_quatre : RadioButton
    lateinit var btn_next : Button
    lateinit var grp_reponses : RadioGroup
    lateinit var img_question : ImageView
    lateinit var txt_nbQuestions : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setSupportActionBar(findViewById(R.id.toolbar))
        pseuso = intent.getStringExtra("pseudo").toString()
        progress_quiz = findViewById(R.id.progress_quiz)
        txt_question = findViewById(R.id.txt_question)
        choix_un = findViewById(R.id.rdb_choix_un)
        choix_deux = findViewById(R.id.rdb_choix_deux)
        choix_trois = findViewById(R.id.rdb_choix_trois)
        choix_quatre = findViewById(R.id.rdb_choix_quatre)
        btn_next = findViewById(R.id.btn_next)
        grp_reponses = findViewById(R.id.reponses)
        btn_next.setText(R.string.btn_validate)
        progress_quiz.setProgress(progress_inc, true)
        img_question = findViewById(R.id.img_question)
        txt_nbQuestions = findViewById(R.id.txtNbQuestions)

        if(savedInstanceState == null){
            afficherQuestion(questions[nb_questions - 1])
        }else{
            score = savedInstanceState.getInt("score")
            nb_questions = savedInstanceState.getInt("nb_questions")
            progress_quiz.setProgress(savedInstanceState.getInt("progress"), true)
            Toast.makeText(this, "$nb_questions", Toast.LENGTH_SHORT).show()
            if(nb_questions > 0){
                afficherQuestion(questions[nb_questions - 1])
            } else {
                afficherQuestion(questions[0])
                //disable radio buttons
                for (i in 0 until grp_reponses.childCount) {
                    (grp_reponses.getChildAt(i) as RadioButton).isEnabled = false
                }
                Toast.makeText(this, "Fin du quiz", Toast.LENGTH_SHORT).show()
            }
        }


        btn_next.setOnClickListener {

            if(btn_next.text == getText(R.string.btn_validate)){
                verifierQuestion(questions[nb_questions - 1])
                if( nb_questions == 1){
                    btn_next.setText(R.string.btn_finish)
                } else {
                    btn_next.setText(R.string.btn_next)
                }

            } else {
                nb_questions--
                if (nb_questions > 0){
                    progress_quiz.incrementProgressBy(progress_inc)

                    grp_reponses.clearCheck()
                    for (i in 0 until grp_reponses.childCount) {
                        (grp_reponses.getChildAt(i) as RadioButton).setBackgroundResource(R.drawable.radio_button_selector)
                    }

                    afficherQuestion(questions[nb_questions - 1])
                    btn_next.setText(R.string.btn_validate)
                } else {

                    val intent = Intent(this, Resultat::class.java)
                    intent.putExtra("score", score)
                    intent.putExtra("nb_questions", questions.size)
                    startActivity(intent)

                }
            }
        }



    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", score)
        outState.putInt("nb_questions", nb_questions)
        outState.putInt("progress_inc", progress_inc)

    }

    // Needed? FIXME
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        score = savedInstanceState.getInt("score")
        nb_questions = savedInstanceState.getInt("nb_questions")
        progress_inc = savedInstanceState.getInt("progress_inc")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        return when (item.itemId){
            R.id.settings -> {

                val intent = Intent(this, Configuration::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

//    override fun onStop() {
//        super.onStop()
//        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
//        with (sharedPref.edit()) {
//            putInt("score", score)
//            putInt("nb_questions", nb_questions)
//            putInt("progress_inc", progress_inc)
//            putString("pseudo", pseuso)
//            commit()
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
//        pseuso = sharedPref.getString("pseudo", "")!!
//    }





    private fun verifierQuestion(question: Question){
        val bonneReponse = grp_reponses.getChildAt(question.choix.indexOf(question.reponse)) as RadioButton

        if(grp_reponses.checkedRadioButtonId == -1){

            bonneReponse.setBackgroundResource(R.drawable.radio_button_bonne_reponse)
            for (i in 0 until grp_reponses.childCount) {
                if (grp_reponses.getChildAt(i) != bonneReponse){
                    (grp_reponses.getChildAt(i) as RadioButton).setBackgroundResource(R.drawable.radio_button_mauvaise_rep)
                }
            }

            return
        }

        val reponseChoisie = findViewById<RadioButton>(grp_reponses.checkedRadioButtonId)

        if (reponseChoisie.text == question.reponse){
            reponseChoisie.setBackgroundResource(R.drawable.radio_button_bonne_reponse)
            score++

        } else {
            reponseChoisie.setBackgroundResource(R.drawable.radio_button_mauvaise_rep)
            grp_reponses.getChildAt(question.choix.indexOf(question.reponse)).setBackgroundResource(R.drawable.radio_button_bonne_reponse)

        }
    }

    @SuppressLint("SetTextI18n")
    fun afficherQuestion(question: Question){
        txt_question.text = question.question
        question.choix.shuffle()
        choix_un.text = question.choix[0]
        choix_deux.text = question.choix[1]
        choix_trois.text = question.choix[2]
        choix_quatre.text = question.choix[3]
        img_question.setImageResource(question.img)
        txt_nbQuestions .text = "${questions.size - nb_questions + 1}/${questions.size}"
    }

    private fun creerQuestions(): Array<Question> {
        val q1 = Question(
            "Quelle est la capitale du Canada?",
            "Ottawa",
            arrayOf("Ottawa", "Toronto", "Montreal", "Québec"),
            img = R.drawable.flag_can
        )
        val q2 = Question(
            "Quelle est la capitale de la France?",
            "Paris",
            arrayOf("Paris", "Lyon", "Marseille", "Bordeaux"),
            img = R.drawable.flag_france
        )
        val q3 = Question(
            "Quelle est la capitale de l'Allemagne?",
            "Berlin",
            arrayOf("Berlin", "Hambourg", "Munich", "Cologne"),
            img = R.drawable.flag_ger
        )
        val q4 = Question(
            "Quelle est la capitale de l'Espagne?",
            "Madrid",
            arrayOf("Madrid", "Barcelone", "Valence", "Séville"),
            img = R.drawable.flag_spain
        )
        val q5 = Question(
            "Quelle est la capitale de l'Italie?",
            "Rome",
            arrayOf("Rome", "Milan", "Venise", "Turin"),
            img = R.drawable.flag_italy
        )

        return arrayOf(q1, q2, q3, q4, q5)

    }

}
