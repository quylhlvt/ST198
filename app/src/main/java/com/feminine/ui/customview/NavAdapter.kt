package com.female.maker.oc.creator.ui.customview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.female.maker.oc.creator.base.AbsBaseAdapter
import com.female.maker.oc.creator.base.AbsBaseDiffCallBack
import com.female.maker.oc.creator.data.model.BodyPartModel
import com.female.maker.oc.creator.utils.onClickCustom
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.female.maker.oc.creator.R
import com.female.maker.oc.creator.databinding.ItemNavigationBinding

class NavAdapter(context: Context) : AbsBaseAdapter<BodyPartModel, ItemNavigationBinding>(R.layout.item_navigation, DiffNav()) {
    val ct= context
    var posNav = 0
    var onClick: ((Int) -> Unit)? = null

    class DiffNav : AbsBaseDiffCallBack<BodyPartModel>() {
        override fun itemsTheSame(oldItem: BodyPartModel, newItem: BodyPartModel): Boolean {
            return oldItem.icon == newItem.icon
        }

        override fun contentsTheSame(oldItem: BodyPartModel, newItem: BodyPartModel): Boolean {
            return oldItem.icon != newItem.icon
        }

    }

    fun setPos(pos: Int) {
        posNav = pos
    }

    override fun bind(
        binding: ItemNavigationBinding,
        position: Int,
        data: BodyPartModel,
        holder: RecyclerView.ViewHolder
    ) {
        Glide.with(binding.root).load(data.icon).encodeQuality(90).override(256).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(binding.imv)
        if (posNav == position) {
            binding.frameStroker.visibility = View.VISIBLE
//            binding.bg.strokeColor = ContextCompat.getColor(
//                binding.root.context,
//                R.color.app_color2
//            )
        } else {
            binding.frameStroker.visibility = View.INVISIBLE
//            binding.bg.strokeColor = ContextCompat.getColor(
//                binding.root.context,
//                R.color.white
//            )
        }

        binding.root.onClickCustom {
            onClick?.invoke(position)
        }
    }

}