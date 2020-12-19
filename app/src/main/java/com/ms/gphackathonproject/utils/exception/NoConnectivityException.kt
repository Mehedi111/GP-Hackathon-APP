package com.gm.lollipop.utils.exception

import java.io.IOException

/*
  @Author: Mehedi Hasan
 * @Date: 12/19/20
 */
class NoConnectivityException : IOException() {
    override val message: String
        get() = "No network available, please check your WiFi or Data connection!"
}