package com.glushko.sportcommunity.presentation.base.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

interface MenuHostFragment {

    @get:MenuRes
    val menuRes: Int

    /**
     * Действие по клику на иконку меню в тулбаре
     * Key: @IdRes Int id элемента меню
     * Value: ()->Boolean лямбда действия по нажатие на элемент меню.
     *
     * @see MenuProvider.onMenuItemSelected Почему Value  возвращает Boolean
     *
     **/
    val menuActions: Map<Int, (()->Boolean)>

    fun setupMenu(host: MenuHost, owner: LifecycleOwner) {
        host.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuRes, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val lambdaMenu = {
                    menuActions[menuItem.itemId]?.invoke() ?: false
                }
                return lambdaMenu.invoke()
            }

        }, owner, Lifecycle.State.RESUMED)
    }

}