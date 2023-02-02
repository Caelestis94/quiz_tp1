package ca.frousseau.tp1_quiz
// make class parcelable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Question() : Parcelable {
    var question: String = ""
    var reponse: String = ""
    var choix: Array<String> = arrayOf("", "", "", "")

    constructor(question: String, reponse: String, choix: Array<String>) : this() {
        this.question = question
        this.reponse = reponse
        this.choix = choix
    }



}