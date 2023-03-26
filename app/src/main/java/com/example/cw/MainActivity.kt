//video link: https://drive.google.com/file/d/1X0aoVWPj20dJZmKzhlKfDYQ1yGVR8E9p/view?usp=share_link
package com.example.cw

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val NGbutton=findViewById<Button>(R.id.NGButton)
        val AboutButton=findViewById<Button>(R.id.AboutButton)

        var winCountHuman=0
        var winCountCom=0

         winCountCom=intent.getIntExtra("comcount",winCountCom)
         winCountHuman=intent.getIntExtra("humancount",winCountHuman)


        AboutButton.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.aboutpopupwindow, null)
            window.contentView = view
            val okButton = view.findViewById<Button>(R.id.buttonOk)
            okButton.setOnClickListener {
                window.dismiss()
            }
            window.showAsDropDown(findViewById<TextView>(R.id.textView3))
        }

        NGbutton.setOnClickListener {
            val intent= Intent(this,NewGame2::class.java)
            intent.putExtra("comcount", winCountCom)
            intent.putExtra("humancount", winCountHuman)
            startActivity(intent)

        }
    }
}