package com.example.cw

import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel

open class GameViewModel: ViewModel() {
  var intArray=IntArray(5)
  var intArrayC=IntArray(5)
  var winCountCom: Int=0
  var winCountHuman: Int=0
  var total: Int=0
  var totalC: Int=0
  var target1:Int = 0
  var comptuerTotal: Int = 0
  var humantotal: Int = 0
  var clickCount:Int = 0
  val flagArray = booleanArrayOf(false,false,false,false,false)
  val flagArrayCom = booleanArrayOf(false,false,false,false,false)

}