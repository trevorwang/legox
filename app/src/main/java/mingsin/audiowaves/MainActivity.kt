package mingsin.audiowaves

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    var newIcon = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            switchNewIcon(!newIcon)
        }
        Timber.plant(Timber.DebugTree())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 切换 app icon
     * https://juejin.cn/post/6930786419396837384
     */
    private fun switchNewIcon(newIcon: Boolean) {

        val oldActivity = ComponentName(applicationContext, MainActivity::class.java)
        val newActivity =
            ComponentName(applicationContext, "mingsin.audiowaves.SplashAliasActivity")

        val toDisable = if (newIcon) oldActivity else newActivity
        val toEnable = if (newIcon) newActivity else oldActivity
        applicationContext.packageManager.setComponentEnabledSetting(
            toDisable,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        applicationContext.packageManager.setComponentEnabledSetting(
            toEnable,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        this.newIcon = newIcon
    }
}