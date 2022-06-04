package com.jmat.dashboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.LayoutDashboardFavouriteBinding
import com.jmat.powertools.Favourite
import com.jmat.powertools.base.adapter.GenericDiffer
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.conversions.ID_CONVERSIONS_L100KM_TO_MPG
import com.jmat.powertools.modules.conversions.ID_CONVERSIONS_ML_TO_OUNCES
import com.jmat.powertools.modules.conversions.ID_CONVERSIONS_KM_TO_MILES

class InstalledAdapter : ListAdapter<Favourite, FavouritesViewHolder>(
    GenericDiffer()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class InstalledViewHolder private constructor(
    private val binding: LayoutDashboardFavouriteBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Favourite) {
        binding.title.text = item.deeplink
        with(binding) {
            root.setOnClickListener {
                root.context.navigateDeeplink(item.deeplink)
            }

            val titleRes = when(item.id) {
                ID_CONVERSIONS_KM_TO_MILES -> R.string.dashboard_conversion_title_km_to_m
                ID_CONVERSIONS_L100KM_TO_MPG -> R.string.dashboard_conversion_title_l100km_to_mpg
                ID_CONVERSIONS_ML_TO_OUNCES -> R.string.dashboard_conversion_title_ml_to_oz
                else -> R.string.dashboard_empty_content
            }
            title.setText(titleRes)
        }
    }

    companion object {
        operator fun invoke(
            parent: ViewGroup
        ): InstalledViewHolder {
            return InstalledViewHolder(
                LayoutDashboardFavouriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}