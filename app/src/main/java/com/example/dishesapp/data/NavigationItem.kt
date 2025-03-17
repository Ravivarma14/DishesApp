package com.example.dishesapp.data

import com.example.dishesapp.R

data class NavigationItem(val icon:Int, val title:String)

val navigationItems= listOf(
    NavigationItem(R.drawable.outdoor_grill_24,"Cook"),
    NavigationItem(R.drawable.favorite_border_24, "Favorites"),
    NavigationItem(R.drawable.chef_hat_24, "Manual"),
    NavigationItem(R.drawable.tablet_android_24, "Device"),
    NavigationItem(R.drawable.account_circle_24, "Preferences"),
    NavigationItem(R.drawable.settings_24, "Settings")
)
