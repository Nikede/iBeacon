package com.nikede.ibeacon.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.nikede.ibeacon.models.DeviceModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DevicesView:MvpView {
    fun setupEmptyList()
    fun setupDevicesList(devicesList: ArrayList<DeviceModel>)
    fun startLoading(all: Boolean)
    fun endLoading(all: Boolean)
    fun setSwitchButtonTitle(textSource: Int)
    fun addDevice(device: DeviceModel)
    @StateStrategyType(value = SingleStateStrategy::class)
    fun showError(textSource: Int)
}