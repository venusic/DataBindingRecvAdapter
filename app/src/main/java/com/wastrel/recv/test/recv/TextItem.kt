package com.wastrel.recv.test.recv

import com.anqile.helmet.base.ui.view.recv.RecvItem
import com.wastrel.recv.BaseRecvAdapter
import com.wastrel.recv.test.databinding.ItemTextBinding

/**
 * @date 2022/11/7 19:39
 * @description
 */
class TextItem(val i: Int) : RecvItem<ItemTextBinding> {
    override val spanSize: Int
        get() = 4

    override fun bindData(adapter: BaseRecvAdapter, viewBinding: ItemTextBinding, position: Int) {
        viewBinding.tvName.text = "第${i}个Item"
    }
}
