package com.jthou.github.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ToggleButton
import com.jthou.common.otherwise
import com.jthou.common.yes
import com.jthou.github.R
import com.jthou.github.model.account.AccountManager
import com.jthou.github.view.LoginActivity
import kotlinx.android.synthetic.main.detail_item.view.*
import rx.Observable
import splitties.activities.start
import splitties.views.onClick
import kotlin.reflect.KProperty

typealias CheckEvent = (Boolean) -> Observable<Boolean>

// 大佬封装了的更强大的开源库 https://github.com/enbandari/ObjectPropertyDelegate
class ObjectPropertyDelegate<T, R>(
    private val receiver: R,
    private val getter: ((R) -> T)? = null,
    private val setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null
) {

    private var value: T? = defaultValue

    operator fun getValue(ref: Any, property: KProperty<*>): T {
        return getter?.invoke(receiver) ?: value!!
    }

    operator fun setValue(ref: Any, property: KProperty<*>, value: T) {
        setter?.invoke(receiver, value)
        this.value = value
    }

}

class DetailItemView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.detail_item, this)
    }

    var title by ObjectPropertyDelegate(titleView, TextView::getText, TextView::setText)
    var content by ObjectPropertyDelegate(contentView, TextView::getText, TextView::setText)

    var icon by ObjectPropertyDelegate(iconView, null, ImageView::setImageResource, 0)
    var operatorIcon by ObjectPropertyDelegate(
        operatorIconView,
        null,
        ToggleButton::setBackgroundResource,
        0
    )

    var isChecked by ObjectPropertyDelegate(
        operatorIconView,
        ToggleButton::isChecked,
        ToggleButton::setChecked
    )

    var checkEvent: CheckEvent? = null

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.DetailItemView)
            title = a.getString(R.styleable.DetailItemView_item_title)
            content = a.getString(R.styleable.DetailItemView_item_content)
            icon = a.getResourceId(R.styleable.DetailItemView_item_icon, 0)
            operatorIcon = a.getResourceId(R.styleable.DetailItemView_item_op_icon, 0)
            a.recycle()
        }
        onClick {
            AccountManager
                .isLoggedIn()
                .yes {
                    checkEvent?.invoke(isChecked)?.subscribe({
                        isChecked = it
                    }, {
                        it.printStackTrace()
                    })
                }.otherwise {
                    context.start<LoginActivity>()
                }
        }
    }

}