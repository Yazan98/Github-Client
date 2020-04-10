package com.yazan98.autohub

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object AutohubScope: CoroutineScope {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    fun destroyScope() {
        scope.cancel()
    }

    fun destroyScope(error: CancellationException) {
        scope.cancel(error)
    }

}
