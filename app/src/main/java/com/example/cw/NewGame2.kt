package com.example.cw

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.*


@Suppress("DEPRECATION")
class NewGame2 : AppCompatActivity() {
    private var intArray = IntArray(5)
    private var intArrayC = IntArray(5)
    private lateinit var editTextValue: EditText
    private lateinit var yourScoreTV: TextView
    private lateinit var computerScoreTV: TextView
    private var winCountCom: Int = 0
    private var winCountHuman: Int = 0
    private var total: Int = 0
    private var totalC: Int = 0
    private var target1: Int = 0
    private var comptuerTotal: Int = 0
    private var humantotal: Int = 0
    private var clickCount: Int = 0
    private val flagArray = booleanArrayOf(false, false, false, false, false)
    private val flagArrayCom = booleanArrayOf(false, false, false, false, false)
    private lateinit var viewModel: GameViewModel

    @SuppressLint("MissingInflatedId", "CutPasteId", "WrongViewCast", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game2)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        arrayOf(
            R.drawable.dice_face_1, R.drawable.dice_face_2,
            R.drawable.dice_face_3, R.drawable.dice_face_4,
            R.drawable.dice_face_5, R.drawable.dice_face_6
        )
        val delayTime = 20

        editTextValue = findViewById(R.id.targetPT)
        target1 = editTextValue.text.toString().toInt()

        yourScoreTV = findViewById(R.id.HScoreTV)
        computerScoreTV = findViewById(R.id.CScoreTV)

        val throwButton = findViewById<Button>(R.id.throwButton)
        val scoreButton = findViewById<Button>(R.id.scoreButton)
        val imageButton1 = findViewById<ImageButton>(R.id.imageButton1)
        val imageButton2 = findViewById<ImageButton>(R.id.imageButton2)
        val imageButton3 = findViewById<ImageButton>(R.id.imageButton3)
        val imageButton4 = findViewById<ImageButton>(R.id.imageButton4)
        val imageButton5 = findViewById<ImageButton>(R.id.imageButton5)
        val imageButton6 = findViewById<ImageButton>(R.id.imageButton6)
        val imageButton7 = findViewById<ImageButton>(R.id.imageButton7)
        val imageButton8 = findViewById<ImageButton>(R.id.imageButton8)
        val imageButton9 = findViewById<ImageButton>(R.id.imageButton9)
        val imageButton10 = findViewById<ImageButton>(R.id.imageButton10)

        winCountCom = intent.getIntExtra("comcount", winCountCom)
        winCountHuman = intent.getIntExtra("humancount", winCountHuman)

        val countTextView = findViewById<TextView>(R.id.tvCount)
        countTextView.text = " Y:$winCountHuman | C: $winCountCom"

        val userImageButtons =
            arrayOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5)
        val computerImageButtons =
            arrayOf(imageButton6, imageButton7, imageButton8, imageButton9, imageButton10)

        fun rollDice(
            userImageButtons: Array<ImageButton>,
            computerImageButtons: Array<ImageButton>
        ) {
            val gen = Random()
            for ((i, elements) in userImageButtons.withIndex()) {
                if (!flagArray[i]) {
                    val randomNumber = 1 + gen.nextInt(6)
                    intArray[i] = randomNumber //get the values of dices into an array
                    when (randomNumber) {
                        1 -> elements.setImageResource(R.drawable.dice_face_1)
                        2 -> elements.setImageResource(R.drawable.dice_face_2)
                        3 -> elements.setImageResource(R.drawable.dice_face_3)
                        4 -> elements.setImageResource(R.drawable.dice_face_4)
                        5 -> elements.setImageResource(R.drawable.dice_face_5)
                        6 -> elements.setImageResource(R.drawable.dice_face_6)
                    }
                    Thread.sleep(delayTime.toLong())
                } else {
                    if (flagArray[i]) {
                        clickCount++
                    }
                }
                flagArray[i] = false
            }

            for ((i, elements) in computerImageButtons.withIndex()) {
                if (!flagArrayCom[i]) {
                    val randomNumber = 1 + gen.nextInt(6)
                    intArrayC[i] = randomNumber //generate random numbers for the dices
                    when (randomNumber) {
                        1 -> elements.setImageResource(R.drawable.dice_face_1)
                        2 -> elements.setImageResource(R.drawable.dice_face_2)
                        3 -> elements.setImageResource(R.drawable.dice_face_3)
                        4 -> elements.setImageResource(R.drawable.dice_face_4)
                        5 -> elements.setImageResource(R.drawable.dice_face_5)
                        6 -> elements.setImageResource(R.drawable.dice_face_6)
                    }
                    Thread.sleep(delayTime.toLong())
                } else {
                    totalC += intArrayC[i] //addinf the value of clicked button to the total
                }
                flagArrayCom[i] = false //set the flag false when the reroll is over
            }
        }
        throwButton.setOnClickListener {
            onClick(userImageButtons)
            computerRandom(computerImageButtons)
            rollDice(userImageButtons, computerImageButtons)
            getScore()
            if (clickCount >= 2) {
                humantotal += total
                comptuerTotal += totalC

                val message1 = "H S:  $humantotal"
                yourScoreTV.text = message1

                val message2 = "C S:  $comptuerTotal"
                computerScoreTV.text = message2
                clickCount = 0
            }
        }

        scoreButton.setOnClickListener {
            clickCount = 0
            getScore()
            humantotal += total
            comptuerTotal += totalC

            val message1 = "H S:  $humantotal"
            yourScoreTV.text = message1

            val message2 = "C S:  $comptuerTotal"
            computerScoreTV.text = message2
            total = 0
            totalC = 0
            winner()
        }

    }
//In the reroll, user click on a image button relevant   flag will be true and reroll the dices with false flag
    private fun onClick(userImageButtons: Array<ImageButton>) {
        for (i in userImageButtons.indices) {
            userImageButtons[i].setOnClickListener {
                flagArray[i] = true
            }
        }
    }

    fun computerRandom(computerImageButtons: Array<ImageButton>) {
        val options = listOf("yes", "no")
        val randomWord = options.random()
        if (randomWord == "yes") {
            for (i in computerImageButtons.indices) {
                computerImageButtons[i].setOnClickListener {
                    val index = intArrayC.indexOf(intArrayC.max())
                        flagArrayCom[index] = true //get index of max value computer's dices and set the flag true. Then other flag's value will change other than the max value

                }
            }
        }
    }

    private fun popUpWinWindow() {
        val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.popupwindowwin, null)
        window.contentView = view
        window.showAsDropDown(findViewById<TextView>(R.id.computerTV2))
    }

    private fun popUpLostWindow() {
        val window = PopupWindow(this)
        val view = layoutInflater.inflate(R.layout.popupwindowlost, null)
        window.contentView = view
        window.showAsDropDown(findViewById<TextView>(R.id.computerTV2))
    }
//android back
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("comcount", winCountCom)
        intent.putExtra("humancount", winCountHuman)
        startActivity(intent)
    }
//get the sum of array
    private fun getScore() {
        for (i in intArray) {
            total = intArray.sum()
        }
        for (i in intArrayC) {
            totalC = intArrayC.sum()
        }
    }
//popup window
    private fun winner() {
        if ((humantotal > target1 && comptuerTotal > target1) || (humantotal > target1 || comptuerTotal > target1)) {

            if (humantotal > comptuerTotal) {
                winCountHuman++
                popUpWinWindow()
            } else {
                winCountCom++
                popUpLostWindow()
            }
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putIntArray("intArray", intArray)
//        outState.putIntArray("intArrayC", intArrayC)
//        outState.putInt("winCountCom", winCountCom)
//        outState.putInt("winCountHuman", winCountHuman)
//        outState.putInt("total", total)
//        outState.putInt("totalC", totalC)
//        outState.putInt("comptuerTotal", comptuerTotal)
//        outState.putInt("humantotal", humantotal)
//        outState.putInt("clickCount", clickCount)
//
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        viewModel.intArray = savedInstanceState.getIntArray("intArray") ?: intArrayOf(1, 2, 3, 4, 5)
//        viewModel.intArrayC = savedInstanceState.getIntArray("intArrayC") ?: intArrayOf(1, 2, 3, 4, 5)
//        viewModel.winCountCom = savedInstanceState.getInt("winCountCom")
//        viewModel.winCountHuman = savedInstanceState.getInt("winCountHuman")
//        viewModel.total = savedInstanceState.getInt("total")
//        viewModel.totalC = savedInstanceState.getInt("totalC")
//        viewModel.comptuerTotal = savedInstanceState.getInt("comptuerTotal")
//        viewModel.humantotal = savedInstanceState.getInt("humantotal")
//        viewModel.clickCount = savedInstanceState.getInt("clickCount")
//    }
}









