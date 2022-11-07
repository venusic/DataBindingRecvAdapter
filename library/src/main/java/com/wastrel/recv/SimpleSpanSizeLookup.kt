package com.wastrel.recv

import androidx.recyclerview.widget.GridLayoutManager

/**
 * @date 2022/11/7 20:12
 * @description
 */
class SimpleSpanSizeLookup(private val adapter: BaseRecvAdapter) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return adapter.getItem(position).spanSize
    }
}
