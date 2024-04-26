package com.itau.family

import android.app.Application
import android.content.Context
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class AppApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AppApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        ViewPump.init(ViewPump.builder()
            .addInterceptor(CalligraphyInterceptor(
                CalligraphyConfig.Builder()
                    .setDefaultFontPath(getString(R.string.font_p1))
                    .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                    .build()))
            .build())
    }
}