package com.wastrel.recv.layout

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.wastrel.recv.BaseRecvAdapter

/**
 * 混合布局下的spancount计算，如果item的spanSize=1表示为占用一整行
 * 反之 表示占用1/x行
 */
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