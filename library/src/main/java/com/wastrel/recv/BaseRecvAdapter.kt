package com.wastrel.recv

import android.annotation.SuppressLint
import android.util.LruCache
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewbinding.ViewBinding
import com.wastrel.recv.item.RecvItem
import java.lang.reflect.Method

open class BaseRecvAdapter(
    private val data: MutableList<RecvItem<out ViewBinding>>,
    private val supportsChangeAnimations: Boolean = false
) :
    RecyclerView.Adapter<BaseRecvAdapter.ViewHolder>() {

    /**
     * Map class to type
     * 利用两个Map实现布局Id和ViewBindingClass之间的一一对应
     *
     */
    private val mapClassToType = HashMap<Class<out ViewBinding>, Int>()
    private val mapTypeToClass = HashMap<Int, Class<out ViewBinding>>()

    /**
     * Inflate method cache
     * 利用LRU 减少反射调用率
     */
    private val inflateMethodCache = object : LruCache<Class<out ViewBinding>, Method>(15) {
        override fun create(key: Class<out ViewBinding>): Method {
            return key.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bindingClz = mapTypeToClass[viewType]
        val inflateMethod = inflateMethodCache.get(bindingClz)
        return ViewHolder(
            inflateMethod?.invoke(
                null,
                LayoutInflater.from(parent.context),
                parent,
                false
            ) as ViewBinding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].innerBindData(this, holder.viewBinding, position)
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int = kotlin.run {
        val viewBindingClass = data[position].viewBindingClass
        var type = mapClassToType[viewBindingClass]
        if (type == null) {
            // 利用当前map的大小作为itemViewType
            type = mapClassToType.size
            mapClassToType[viewBindingClass] = type
            mapTypeToClass[type] = viewBindingClass
        }
        type
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
            supportsChangeAnimations
    }

    fun addItem(item: RecvItem<out ViewBinding>) = run {
        data.add(item)
        notifyItemChanged(data.size - 1)
    }

    fun addItem(index: Int, item: RecvItem<out ViewBinding>) = run {
        data.add(index, item)
        notifyItemInserted(index)
    }

    fun addItems(items: List<RecvItem<out ViewBinding>>) {
        data.addAll(items)
        notifyItemRangeInserted(data.size - items.size, items.size)
    }

    fun getData(): List<RecvItem<out ViewBinding>> {
        return data
    }

    fun getItem(position: Int) = data[position]

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(items: List<RecvItem<out ViewBinding>>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun notifyItemChanged(item: RecvItem<out ViewBinding>) {
        val index = data.indexOf(item)
        if (index > -1) notifyItemChanged(index)
    }

    fun removeItem(item: RecvItem<out ViewBinding>) {
        val index = data.indexOf(item)
        if (index > -1) removeItem(index)
    }

    fun clearData() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun isEmpty() = data.isEmpty()

    inner class ViewHolder(val viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root)
}
