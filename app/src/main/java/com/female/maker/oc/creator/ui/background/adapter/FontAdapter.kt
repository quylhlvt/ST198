package com.female.maker.oc.creator.ui.background.adapter

import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.female.maker.oc.creator.base.AbsBaseAdapter
import com.female.maker.oc.creator.base.AbsBaseDiffCallBack
import com.female.maker.oc.creator.data.model.SelectedModel
import com.female.maker.oc.creator.utils.onSingleClick
import com.female.maker.oc.creator.R
import com.female.maker.oc.creator.databinding.ItemFontBinding

class FontAdapter :
    AbsBaseAdapter<SelectedModel, ItemFontBinding>(R.layout.item_font, DiffCallBack()) {
    var onClick: ((Int) -> Unit)? = null
    var posSelect = 0
    override fun bind(
        binding: ItemFontBinding,
        position: Int,
        data: SelectedModel,
        holder: RecyclerView.ViewHolder
    ) {
        binding.material.onSingleClick {
            onClick?.invoke(position)
        }
        binding.tv.typeface = ResourcesCompat.getFont(binding.root.context, data.color)
        if (data.isSelected) {
            binding.tv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.app_color8))
            binding.material.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
        } else {
            binding.tv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            binding.material.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.transparent))
        }
    }

    class DiffCallBack : AbsBaseDiffCallBack<SelectedModel>() {
        override fun itemsTheSame(
            oldItem: SelectedModel,
            newItem: SelectedModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun contentsTheSame(
            oldItem: SelectedModel,
            newItem: SelectedModel
        ): Boolean {
            return oldItem.path != newItem.path || oldItem.isSelected != newItem.isSelected
        }

    }
}