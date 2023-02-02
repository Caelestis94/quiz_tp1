package ca.frousseau.tp1_quiz

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
    lateinit var progress_quiz : ProgressBar
    lateinit var txt_question : TextView
    lateinit var choix_un : RadioButton
    lateinit var choix_deux : RadioButton
    lateinit var choix_trois : RadioButton
    lateinit var choix_quatre : RadioButton
    lateinit var btn_next : Button
    lateinit var grp_reponses : RadioGroup




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progress_quiz = findViewById(R.id.progress_quiz)
        txt_question = findViewById(R.id.txt_question)
        choix_un = findViewById(R.id.rdb_choix_un)
        choix_deux = findViewById(R.id.rdb_choix_deux)
        choix_trois = findViewById(R.id.rdb_choix_trois)
        choix_quatre = findViewById(R.id.rdb_choix_quatre)
        btn_next = findViewById(R.id.btn_next)
        grp_reponses = findViewById(R.id.reponses)
        btn_next.text = "Valider"
        progress_quiz.setProgress(progress_inc, true)

        if(savedInstanceState == null){
            afficherQuestion(questions[nb_questions - 1])
        }else{
            score = savedInstanceState.getInt("score")
            nb_questions = savedInstanceState.getInt("nb_questions")
            progress_quiz.setProgress(savedInstanceState.getInt("progress"), true)
            Toast.makeText(this, "$nb_questions", Toast.LENGTH_SHORT).show()
            afficherQuestion(questions[nb_questions - 1])
        }


        btn_next.setOnClickListener {

            if(btn_next.text == "Valider"){
                verifierQuestion(questions[nb_questions - 1])
                btn_next.text = "Suivant"

            } else {
                nb_questions--
                if (nb_questions > 0){
                    progress_quiz.incrementProgressBy(progress_inc)

                    grp_reponses.clearCheck()
                    for (i in 0 until grp_reponses.childCount) {
                        (grp_reponses.getChildAt(i) as RadioButton).setBackgroundResource(R.drawable.radio_button_selector)
                    }

                    afficherQuestion(questions[nb_questions - 1])
                    btn_next.text = "Valider"
                } else {

                    Toast.makeText(this, "Fin du quiz", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    fun verifierQuestion(question: Question){
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

    fun afficherQuestion(question: Question){
        txt_question.text = question.question
        question.choix.shuffle()
        choix_un.text = question.choix[0]
        choix_deux.text = question.choix[1]
        choix_trois.text = question.choix[2]
        choix_quatre.text = question.choix[3]
    }

    private fun creerQuestions(): Array<Question> {
        var q1 = Question("Quelle est la capitale du Canada?", "Ottawa", arrayOf("Ottawa", "Toronto", "Montreal", "Québec"))
        var q2 = Question("Quelle est la capitale de la France?", "Paris", arrayOf("Paris", "Lyon", "Marseille", "Bordeaux"))
        var q3 = Question("Quelle est la capitale de l'Allemagne?", "Berlin", arrayOf("Berlin", "Hambourg", "Munich", "Cologne"))
        var q4 = Question("Quelle est la capitale de l'Espagne?", "Madrid", arrayOf("Madrid", "Barcelone", "Valence", "Séville"))
        var q5 = Question("Quelle est la capitale de l'Italie?", "Rome", arrayOf("Rome", "Milan", "Venise", "Turin"))

        var questions = arrayOf(q1, q2, q3, q4, q5)
        return questions

    }

}
