package com.anqile.helmet.base.ui.view.recv

import androidx.viewbinding.ViewBinding
import com.wastrel.recv.BaseRecvAdapter
import com.wastrel.recv.ktx.findParameterizedType

interface RecvItem<V : ViewBinding> {
    /**
     * 用于GridLayoutManger时表明占用多少空间
     * 例如横向指定为4个item时，此处1表示占一个，2表示占2个，4表示占三个，4表示占一行
     */
    val spanSize: Int
        get() = 1

    /**
     * 布局的ViewBinding类，此处直接拿泛型
     */
    val viewBindingClass: Class<V>
        get() = this.javaClass.findParameterizedType(
            ViewBinding::class.java,
            RecvItem::class.java
        ) as Class<V>

    /**
     * 用来做类型转换，否则由于运行时的抹除机制，编译无法通过
     */
    fun innerBindData(adapter: BaseRecvAdapter, viewBinding: ViewBinding, position: Int) {
        bindData(adapter, viewBinding as V, position)
    }

    /**
     * 子类实现该方法即可
     *
     * @param viewBinding
     */
    fun bindData(adapter: BaseRecvAdapter, viewBinding: V, position: Int)
}
