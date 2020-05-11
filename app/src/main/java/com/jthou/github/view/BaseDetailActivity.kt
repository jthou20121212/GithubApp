package com.jthou.github.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import com.bennyhuo.swipefinishable.SwipeFinishable
import com.jthou.github.R
import com.jthou.github.settings.Themer
import splitties.dimensions.dp

abstract class BaseDetailActivity : AppCompatActivity() {

    private val swipeBackTouchDelegate by lazy {
        SwipeBackTouchDelegate(this, ::finish)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home ->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item);
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_in, R.anim.rignt_out)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return  swipeBackTouchDelegate.onTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

}

class SwipeBackTouchDelegate(val activity: AppCompatActivity, block: () -> Unit) {

    companion object {
        private const val MIN_FLING_TO_BACK = 2000
    }

    private val minFlingToBack by lazy {
        activity.dp(MIN_FLING_TO_BACK)
    }

    private val swipeBackDelegate by lazy {
        GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return if (velocityX > minFlingToBack) {
                    block()
                    true
                } else {
                    false
                }
            }
        })
    }

    fun onTouchEvent(event: MotionEvent) = swipeBackDelegate.onTouchEvent(event)

}

abstract class BaseDetailSwipeFinishableActivity : AppCompatActivity(), SwipeFinishable.SwipeFinishableActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home ->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item);
    }

    override fun finish() {
        SwipeFinishable.INSTANCE.finishCurrentActivity()
    }

    override fun finishThisActivity() {
        super.finish()
    }

}
