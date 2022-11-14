package com.wastrel.recv.test.recv

import com.wastrel.recv.BaseRecvAdapter
import com.wastrel.recv.item.RecvItem
import com.wastrel.recv.test.databinding.ItemImageBinding

/**
 * @date 2022/11/7 19:47
 * @description
 */
class ImageItem : RecvItem<ItemImageBinding> {
    override fun bindData(adapter: BaseRecvAdapter, viewBinding: ItemImageBinding, position: Int) {
    }

    override val spanSize: Int
        get() = 4
}
