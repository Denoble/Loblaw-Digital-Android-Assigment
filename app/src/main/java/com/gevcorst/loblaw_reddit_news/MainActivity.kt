package com.gevcorst.loblaw_reddit_news

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gevcorst.loblaw_reddit_news.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class ConnectivityMode {
        NONE,
        WIFI,
        MOBILE,
        OTHER,
        MAYBE
    }
    var connectivityMode = ConnectivityMode.NONE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        connectivityMode = checkConnectivity(this.applicationContext)
        if(!(connectivityMode == ConnectivityMode.MOBILE
                    || connectivityMode == ConnectivityMode.WIFI)){
            showNoInternetAlertDialog()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
    private fun checkConnectivity(context: Context): MainActivity.ConnectivityMode {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        cm.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                    return when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> MainActivity.ConnectivityMode.WIFI
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectivityMode.MOBILE
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectivityMode.OTHER
                        hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> ConnectivityMode.MAYBE
                        else -> ConnectivityMode.NONE
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                activeNetworkInfo?.run {
                    return when (type) {
                        ConnectivityManager.TYPE_WIFI -> ConnectivityMode.WIFI
                        ConnectivityManager.TYPE_MOBILE -> ConnectivityMode.MOBILE
                        ConnectivityManager.TYPE_ETHERNET -> ConnectivityMode.OTHER
                        ConnectivityManager.TYPE_BLUETOOTH -> ConnectivityMode.MAYBE
                        else -> ConnectivityMode.NONE
                    }
                }
            }
        }
        return ConnectivityMode.NONE
    }
    fun showNoInternetAlertDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("You are not connected to the Internet !")
            .setCancelable(false)
            .setPositiveButton("Close App", DialogInterface.OnClickListener {
                    dialog, id -> finish()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Internet Connection")
        alert.show()
        alert.window?.setBackgroundDrawableResource(R.color.colorPrimary)
    }
}
