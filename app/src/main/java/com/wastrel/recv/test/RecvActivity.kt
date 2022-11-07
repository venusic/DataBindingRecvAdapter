package com.wastrel.recv.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anqile.helmet.base.ui.view.recv.RecvItem
import com.wastrel.recv.BaseRecvAdapter
import com.wastrel.recv.SimpleSpanSizeLookup
import com.wastrel.recv.test.recv.ImageItem
import com.wastrel.recv.test.recv.TextItem

/**
 * @date 2022/11/7 19:26
 * @description
 */
class RecvActivity : AppCompatActivity() {

    lateinit var recv: RecyclerView
    private val adapter by lazy {
        BaseRecvAdapter(arrayListOf())
    }
    private val type by lazy {
        intent.getStringExtra("type")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recv = RecyclerView(this)
        setContentView(recv)
        recv.adapter = adapter
        if (type == "single") {
            recv.layoutManager = LinearLayoutManager(this)
            adapter.setNewData(buildSingleItem())
        } else if (type == "multi") {
            recv.layoutManager = LinearLayoutManager(this)
            adapter.setNewData(buildMultiItem())
        } else if (type == "grid") {
            val value = GridLayoutManager(this, 4)
            value.spanSizeLookup = SimpleSpanSizeLookup(adapter)
            recv.layoutManager = value
            adapter.setNewData(buildGrid())
        }
    }

    private fun buildSingleItem(): ArrayList<RecvItem<out ViewBinding>> {
        val list = arrayListOf<RecvItem<out ViewBinding>>()
        repeat(8) {
            list.add(TextItem(it))
        }
        return list
    }

    private fun buildMultiItem(): ArrayList<RecvItem<out ViewBinding>> {
        val list = arrayListOf<RecvItem<out ViewBinding>>()
        repeat(8) {
            if (it % 2 == 0)
                list.add(TextItem(it))
            else
                list.add(ImageItem())
        }
        return list
    }

    private fun buildGrid(): ArrayList<RecvItem<out ViewBinding>> {
        val list = arrayListOf<RecvItem<out ViewBinding>>()
        repeat(20) {
            if (it % 5 == 0)
                list.add(TextItem(it))
            else
                list.add(ImageItem())
        }
        return list
    }
}
