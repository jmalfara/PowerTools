package com.jmat.dashboard.ui.model

import com.jmat.dashboard.data.model.Listing
import com.jmat.dashboard.data.model.Module
import java.io.Serializable

data class ListingData(
    val module: Module,
    val listing: Listing,
    val installed: Boolean
) : Serializable