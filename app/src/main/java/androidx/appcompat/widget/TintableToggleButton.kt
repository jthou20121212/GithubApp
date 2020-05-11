package androidx.appcompat.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.view.TintableBackgroundView

@SuppressLint("RestrictedApi")
class TintableToggleButton
@JvmOverloads
constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) :
    AppCompatToggleButton(TintContextWrapper.wrap(context), attrs, defStyleAttr), TintableBackgroundView {

    private var mBackgroundTintHelper: AppCompatBackgroundHelper? = null

    init {
        mBackgroundTintHelper = AppCompatBackgroundHelper(this)
        mBackgroundTintHelper?.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun setSupportBackgroundTintList(tint: ColorStateList?) {
        mBackgroundTintHelper?.supportBackgroundTintList = tint
    }

    override fun getSupportBackgroundTintMode() = mBackgroundTintHelper?.supportBackgroundTintMode

    override fun setSupportBackgroundTintMode(tintMode: PorterDuff.Mode?) {
        mBackgroundTintHelper?.supportBackgroundTintMode = tintMode
    }

    override fun getSupportBackgroundTintList() = mBackgroundTintHelper?.supportBackgroundTintList

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        super.setBackgroundResource(resId)
        mBackgroundTintHelper?.onSetBackgroundResource(resId)
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        super.setBackgroundDrawable(background)
        mBackgroundTintHelper?.onSetBackgroundDrawable(background)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        mBackgroundTintHelper?.applySupportBackgroundTint()
    }

}