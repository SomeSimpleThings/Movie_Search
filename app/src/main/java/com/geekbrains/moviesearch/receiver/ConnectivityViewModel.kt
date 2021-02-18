package com.geekbrains.moviesearch.receiver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ConnectivityViewModel(
    application: Application,

    ) : AndroidViewModel(application) {
    fun getNetworkStatus(): ConnectivityLiveData {
        return connectivityLiveData
    }

    private val connectivityLiveData: ConnectivityLiveData = ConnectivityLiveData(getApplication())
}

class ConnectivityLiveData(
    private val context: Context
) : LiveData<Boolean>() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onActive() {
        val intentFilter = IntentFilter(CONNECTIVITY_ACTION)
        broadcastReceiver = createBroadcastReceiver()
        context.registerReceiver(broadcastReceiver, intentFilter)
    }


    override fun onInactive() {
        context.unregisterReceiver(broadcastReceiver)

    }

    private fun createBroadcastReceiver() = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val isNoConnectivity = intent?.extras?.getBoolean(EXTRA_NO_CONNECTIVITY) ?: true
            postValue(!isNoConnectivity)
        }
    }
}