package com.avatar.ocmaker.profile.ui.background.adapter

import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.avatar.ocmaker.profile.base.AbsBaseAdapter
import com.avatar.ocmaker.profile.base.AbsBaseDiffCallBack
import com.avatar.ocmaker.profile.data.model.SelectedModel
import com.avatar.ocmaker.profile.utils.onSingleClick
import com.avatar.ocmaker.profile.R
import com.avatar.ocmaker.profile.databinding.ItemFontBinding

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
        binding.imv.onSingleClick {
            onClick?.invoke(position)
        }
        binding.tv.typeface = ResourcesCompat.getFont(binding.root.context, data.color)
        if (data.isSelected) {
            binding.imv.setImageResource(R.drawable.imv_font_true)
        } else {
            binding.imv.setImageResource(R.drawable.imv_font_false)
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