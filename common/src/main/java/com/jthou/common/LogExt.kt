package com.jthou.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
val loggerMap = HashMap<Class<*>, Logger>()

inline val <reified T> T.logger: Logger
    get() {
        return loggerMap[T::class.java]?: LoggerFactory.getLogger(T::class.java).apply { loggerMap[T::class.java] = this }
    }