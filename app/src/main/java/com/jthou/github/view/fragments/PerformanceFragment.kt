//package com.bennyhuo.github.view.fragments
//
//import android.os.Bundle
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.jthou.github.R
//import kotlin.concurrent.thread
//
///**
// * Created by benny on 7/9/17.
// */
//class PerformanceFragment : Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        thread {
//            // avoid loading classes in tests.
//            PerformanceFragmentUI().createView(AnkoContext.create(container!!.context, this))
//            inflater.inflate(R.layout.activity_login, container, false)
//            System.nanoTime()
//
//            fun cost(tag: String, block: ()->Unit){
//                System.gc()
//                System.gc()
//                val start = System.nanoTime()
//                repeat(1) {
//                    block()
//                }
//                val cost = System.nanoTime() - start
//                logger.error("$tag: $cost")
//            }
//
//            cost("dsl") {
//                PerformanceFragmentUI().createView(AnkoContext.create(container!!.context, this))
//            }
//
//            cost("xml") {
//                inflater.inflate(R.layout.fragment_about, container, false)
//            }
//        }
//        return PerformanceFragmentUI().createView(AnkoContext.create(container!!.context, this))
//    }
//}
//
//class PerformanceFragmentUI : AnkoComponent<PerformanceFragment> {
//    override fun createView(ui: AnkoContext<PerformanceFragment>) = ui.apply {
//        nestedScrollView {
//            verticalLayout {
//
//                imageView {
//                    imageResource = R.mipmap.ic_launcher
//                }.lparams(width = wrapContent, height = wrapContent){
//                    gravity = Gravity.CENTER_HORIZONTAL
//                }
//                themedTextView("GitHub", R.style.detail_title){
//                    textColor = R.color.colorPrimary
//                }.lparams(width = wrapContent, height = wrapContent){
//                    gravity = Gravity.CENTER_HORIZONTAL
//                }
//                themedTextView("By Bennyhuo", R.style.detail_description){
//                    textColor = R.color.colorPrimary
//                }.lparams(width = wrapContent, height = wrapContent){
//                    gravity = Gravity.CENTER_HORIZONTAL
//                }
//                themedTextView(R.string.open_source_licenses, R.style.detail_description){
//                    textColor = R.color.colorPrimary
//
////                    onClick {
////                        alert {
////                            customView {
////                                scrollView {
////                                    textView {
////                                        padding = dip(10)
////                                        markdownText = context.assets.open("licenses.md").bufferedReader().readText()
////                                    }
////                                }
////                            }
////                        }.show()
////                    }
//                }.lparams(width = wrapContent, height = wrapContent){
//                    gravity = Gravity.CENTER_HORIZONTAL
//                }
//            }.lparams(width = wrapContent, height = wrapContent){
//                gravity = Gravity.CENTER
//            }
//        }
//
//    }.view
//}