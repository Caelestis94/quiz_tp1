package ca.frousseau.tp1_quiz
// make class parcelable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Représente une question
 * */
class Question() {
    var question: String = ""
    var reponse: String = ""
    var choix: Array<String> = arrayOf("", "", "", "")
    var img : Int = 0

    constructor(question: String, reponse: String, choix: Array<String>,img: Int) : this() {
        this.question = question
        this.reponse = reponse
        this.choix = choix
        this.img = img
    }



}