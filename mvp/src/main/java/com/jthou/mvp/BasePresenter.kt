package com.jthou.mvp

import android.content.res.Configuration
import android.os.Bundle

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 29-02-2020
 */

abstract class BasePresenter <out V: IView<BasePresenter<V>>> : IPresenter<V> {

    override lateinit var view: @UnsafeVariance V

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

}