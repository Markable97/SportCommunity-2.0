package com.glushko.sportcommunity.presentation.base

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.glushko.sportcommunity.R

abstract class BaseFragmentWithToolbarMenu<B : ViewBinding>(@LayoutRes layout: Int) : BaseXmlFragment<B>(layout) {

    @get:MenuRes
    abstract val menuRes: Int

    /**
     * Действие по клику на иконку меню в тулбаре
     * Key: @IdRes Int id элемента меню
     * Value: ()->Boolean лямбда действия по нажатие на элемент меню.
     *
     * @see MenuProvider.onMenuItemSelected Почему Value  возвращает Boolean
     *
     **/
    abstract val menuActions: Map<Int, ((MenuItem)->Boolean)>

    private var _menu: Menu? = null

    fun setupMenu(host: MenuHost, owner: LifecycleOwner) {
        host.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                _menu = menu
                menuInflater.inflate(menuRes, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val lambdaMenu = {
                    menuActions[menuItem.itemId]?.invoke(menuItem) ?: false
                }
                return lambdaMenu.invoke()
            }

        }, owner, Lifecycle.State.RESUMED)
    }

    fun checkFavorite(isCheck: Boolean) {
        _menu?.findItem(R.id.menuFavorite)?.isChecked = isCheck
    }


    override fun onDestroy() {
        _menu = null
        super.onDestroy()
    }

}