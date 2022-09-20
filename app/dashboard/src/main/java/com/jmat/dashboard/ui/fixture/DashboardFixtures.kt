package com.jmat.dashboard.ui.fixture

import com.jmat.dashboard.data.model.Module
import com.jmat.dashboard.ui.model.ShortcutData

object DashboardFixtures {
    val module = Module(
        name = "Name",
        author = "Author",
        iconUrl = "IconUrl",
        shortDescription = "ShortDescription",
        installName = "InstallName",
        entrypoint = "Entrypoint",
        features = listOf()
    )

    val shortcutData = ShortcutData(
        id = "1234",
        name = "Lorem Ipsum is simply dummy text",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        icon = "URL",
        action = "action"
    )
}