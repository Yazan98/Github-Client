package com.yazan98.autohub.utils

import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.OnHeapAnalyzedListener
import shark.HeapAnalysis

class LeakUploader  : OnHeapAnalyzedListener {
    private val defaultListener = DefaultOnHeapAnalyzedListener.create()
    override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {
        defaultListener.onHeapAnalyzed(heapAnalysis)
    }
}