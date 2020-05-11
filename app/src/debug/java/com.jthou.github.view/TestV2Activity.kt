package com.jthou.github.view

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.jthou.layout.v2.MATCH_LAYOUT
import com.jthou.layout.v2.button
import com.jthou.layout.v2.frameLayout
import com.jthou.layout.v2.verticalLayout

class TestV2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            setBackgroundColor(Color.RED)

            verticalLayout {

                button {
                    text = "button 1"
                    setBackgroundColor(Color.YELLOW)
                }.lparams {
                    weight = 1f
                }

                button {
                    text = "button 2"
                    setBackgroundColor(Color.GREEN)
                }.lparams {
                    weight = 3f
                }
            }.lparams {
                height = MATCH_LAYOUT
                gravity = Gravity.END
            }
        }
    }

}