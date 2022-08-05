package com.jmat.powertools.extensions

import com.jmat.powertools.Feature
import com.jmat.powertools.Module
import com.jmat.powertools.data.model.Feature as UiFeature
import com.jmat.powertools.data.model.Module as UiModule

fun Module.toUiModule(): UiModule {
    return UiModule(
        name = name,
        installName = installName,
        author = author,
        shortDescription = shortDescription,
        entrypoint = entrypoint,
        iconUrl = iconUrl,
        features = featuresList.map { feature ->
            feature.toUiFeature()
        }
    )
}

fun Feature.toUiFeature(): UiFeature {
    return UiFeature(
        id = id,
        title = title,
        description = description,
        module = module,
        iconUrl = iconUrl,
        entrypoint = entrypoint
    )
}
