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
        name = "Name",
        icon = "URL",
        action = "action"
    )
}