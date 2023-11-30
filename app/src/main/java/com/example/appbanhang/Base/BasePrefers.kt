package com.example.appbanhang.Base

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

class BasePrefers(context: Context) {

    private val prefsLocale = "prefsLocale"

    private val mPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    var locale
        get() = mPrefs.getString(prefsLocale, "en")
        set(value) = mPrefs.edit { putString(prefsLocale, value) }

    companion object {
        @Volatile
        private var INSTANCE: BasePrefers? = null

        fun initPrefs(context: Context): BasePrefers {
            return INSTANCE ?: synchronized(this) {
                val instance = BasePrefers(context)
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun getPrefsInstance(): BasePrefers {
            return INSTANCE ?: error("GoPreferences not initialized!")
        }
    }
}