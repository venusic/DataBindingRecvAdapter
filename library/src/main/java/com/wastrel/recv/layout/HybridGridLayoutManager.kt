package com.wastrel.recv.layout

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.wastrel.recv.BaseRecvAdapter

class HybridGridLayoutManager(adapter: BaseRecvAdapter, context: Context, spanCount: Int) :
    GridLayoutManager(context, spanCount) {
    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return spanCount / adapter.getItem(position).spanSize
            }

        }
    }
}