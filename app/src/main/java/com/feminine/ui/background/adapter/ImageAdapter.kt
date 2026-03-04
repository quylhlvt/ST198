package com.female.maker.oc.creator.ui.background.adapter

import androidx.recyclerview.widget.RecyclerView
import com.female.maker.oc.creator.base.AbsBaseAdapter
import com.female.maker.oc.creator.base.AbsBaseDiffCallBack
import com.female.maker.oc.creator.data.model.SelectedModel
import com.female.maker.oc.creator.utils.hide
import com.female.maker.oc.creator.utils.onSingleClick
import com.female.maker.oc.creator.utils.show
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.female.maker.oc.creator.R
import com.female.maker.oc.creator.databinding.ItemImageBinding

class ImageAdapter :
    AbsBaseAdapter<SelectedModel, ItemImageBinding>(R.layout.item_image, DiffCallBack()) {
    var onClick: ((Int) -> Unit)? = null
    var posSelect = -1
    override fun bind(
        binding: ItemImageBinding,
        position: Int,
        data: SelectedModel,
        holder: RecyclerView.ViewHolder
    ) {
//        binding.tvAddImage.isSelected = true
        binding.imvImage.onSingleClick {
            onClick?.invoke(position)
        }

        Glide.with(binding.root).load(data.path).encodeQuality(70)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(binding.imvImage)
//        if (position == 0) {
//            binding.lnlAddItem.show()
//        } else {
//            binding.lnlAddItem.hide()
//        }
        if (data.isSelected) {
            binding.vFocus.show()

        } else {
            binding.vFocus.hide()

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