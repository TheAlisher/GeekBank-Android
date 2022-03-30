package com.alish.geekbank.presentation.ui.adapters.fingerparint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.alish.geekbank.presentation.base.BaseDiffUtilCard
import com.alish.geekbank.presentation.base.BaseViewHolder

interface ItemFingerprint<V : ViewBinding, I : BaseDiffUtilCard> {

    fun isRelativeItem(item: BaseDiffUtilCard): Boolean

    @LayoutRes
    fun getLayoutId(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<V, I>

    fun getDiffUtil(): DiffUtil.ItemCallback<I>

}