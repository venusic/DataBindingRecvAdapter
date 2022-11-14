package com.wastrel.recv.item

import android.graphics.Color
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.wastrel.recv.BaseRecvAdapter
import com.wastrel.recv.databinding.RecvBlankItemBinding

/**
 * @Description 空白RecvItem
 * @Date 2022/3/7
 * @param blankSize 留空白的大小
 * @param orientation 水平留空白 还是垂直留空白
 * @param bgColor 背景颜色，默认透明
 */
class BlankRecvItem(
    val blankSize: Int,
    @RecyclerView.Orientation val orientation: Int = RecyclerView.VERTICAL,
    @ColorInt val bgColor: Int = Color.TRANSPARENT
) : RecvItem<RecvBlankItemBinding> {
    override fun bindData(
        adapter: BaseRecvAdapter,
        viewBinding: RecvBlankItemBinding,
        position: Int
    ) {
        viewBinding.root.setBackgroundColor(bgColor)
        val params = viewBinding.root.layoutParams
        params.apply {
            if (orientation == RecyclerView.VERTICAL) {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = blankSize
            } else {
                width = blankSize
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
        viewBinding.root.layoutParams = params
    }
}
