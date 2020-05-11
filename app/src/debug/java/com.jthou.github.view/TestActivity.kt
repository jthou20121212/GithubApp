package com.jthou.github.view

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.jthou.layout.v1.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            setBackgroundColor(Color.RED)

            verticalLayout {

                layoutWidth = WRAP_LAYOUT
                layoutHeight = MATCH_LAYOUT
                layoutGravity = Gravity.END

                button {
                    text = "button 1"
                    layoutWeight = 1f

                    setBackgroundColor(Color.YELLOW)
                }

                button {
                    text = "button 2"
                    layoutWeight = 3f

                    setBackgroundColor(Color.GREEN)
                }

            }
        }
    }

}