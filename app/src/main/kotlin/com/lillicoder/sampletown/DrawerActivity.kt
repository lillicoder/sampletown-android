package com.lillicoder.sampletown

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.lillicoder.sampletown.recycler.GridActivity

/**
 * Base [AppCompatActivity] for activities that need to display a [DrawerLayout].
 */
abstract class DrawerActivity : AppCompatActivity() {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        mDrawerToggle = configureDrawer(toolbar, drawerLayout)

        val navigationMenu = findViewById<NavigationView>(R.id.navigation)
        navigationMenu.setNavigationItemSelectedListener(this::navigationItemSelected)

        val contentContainer = findViewById<FrameLayout>(R.id.contentContainer)
        LayoutInflater.from(this).inflate(getContentView(), contentContainer, true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        mDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

    /**
     * Gets the layout resource ID for this activity's content.
     */
    @LayoutRes
    abstract fun getContentView(): Int

    /**
     * Configures the given drawer layout for this activity.
     * @param toolbar [Toolbar] for this activity.
     * @param drawer [DrawerLayout] to configure.
     */
    private fun configureDrawer(
        toolbar: Toolbar,
        drawer: DrawerLayout
    ): ActionBarDrawerToggle {
        val toggle =
            ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.content_desc_drawer_open,
                R.string.content_desc_drawer_close
            )

        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        return toggle
    }

    /**
     * Handles the selection of a navigation item in this activity's navigation drawer.
     */
    private fun navigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_main -> MainActivity.start(this)
            R.id.navigation_grid -> GridActivity.start(this)
        }

        return false
    }
}
