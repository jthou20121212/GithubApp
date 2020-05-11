package com.jthou.github.settings

import com.jthou.github.AppContext
import com.jthou.github.R
import com.jthou.github.model.account.AccountManager
import com.jthou.github.utils.pref

object Settings {

    var lastPage: Int
        get() = if (lastPageIdString.isEmpty()) 0 else AppContext.resources.getIdentifier(
            lastPageIdString, "id", AppContext.packageName
        )
        set(value) {
            lastPageIdString = AppContext.resources.getResourceEntryName(value)
        }

    val defaultPage
        get() = if (AccountManager.isLoggedIn()) defaultPageForUser else defaultPageForVisitor

    private var defaultPageForUser by pref(R.id.navRepos)

    private var defaultPageForVisitor by pref(R.id.navRepos)

    private var lastPageIdString by pref("")

    var themeMode by pref("DAY")

}