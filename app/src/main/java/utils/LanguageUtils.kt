package utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageUtils {
    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun getCurrentLanguage(): String {
        return Locale.getDefault().language
    }
}