package com.nikede.ibeacon.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nikede.ibeacon.R
import com.nikede.ibeacon.models.DeviceModel
import com.nikede.ibeacon.views.DevicesView
import kotlinx.coroutines.*

@InjectViewState
class DevicesPresenter: MvpPresenter<DevicesView>() {

    private var all = true

    fun loadDevices() {
        viewState.startLoading(all)
        GlobalScope.async {
            delay(2000)
            launch(Dispatchers.Main) {
                viewState.endLoading(all)
            }
        }
    }

    fun addDevice(device: DeviceModel) {
        viewState.addDevice(device)
    }

    fun devicesLoaded(devicesList: ArrayList<DeviceModel>) {
        viewState.endLoading(all)
        if (devicesList.size == 0) {
            viewState.setupEmptyList()
        } else {
            viewState.setupDevicesList(devicesList)
        }
    }

    fun showError() {
        viewState.endLoading(all)
        viewState.showError(0)
    }

    fun switchButtonClick() {
        if (all)
            viewState.setSwitchButtonTitle(R.string.beacons)
        else
            viewState.setSwitchButtonTitle(R.string.all)
        all = !all
    }
}