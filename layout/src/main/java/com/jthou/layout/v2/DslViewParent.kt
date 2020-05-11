package com.jthou.layout.v2

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
annotation class DslViewMaker

const val MATCH_LAYOUT = -1
const val WRAP_LAYOUT = -2

@DslViewMaker
interface DslViewParent