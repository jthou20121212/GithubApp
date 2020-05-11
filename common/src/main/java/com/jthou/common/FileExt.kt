package com.jthou.common

import java.io.File

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
fun File.ensureDir(): Boolean {
    isDirectory.no {
        isFile.yes {
            delete()
        }
        return mkdirs()
    }
    return false
}