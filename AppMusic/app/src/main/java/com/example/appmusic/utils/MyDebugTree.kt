package com.example.appmusic.utils

import timber.log.Timber

class MyDebugTree : Timber.DebugTree() {
    protected fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "(%s:%s)#%s",
            element.fileName,
            element.lineNumber,
            element.methodName
        )
    }
}