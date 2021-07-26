package com.icarus.calculator.base

import a.icarus.component.MonitorApplication
import a.icarus.utils.Logger


class App : MonitorApplication() {
    override fun init() {
        super.init()
        Logger.setType(Logger.ERROR)
    }
}