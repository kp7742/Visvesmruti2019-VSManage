package tech.visvesmruti.vsmanage.utils

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.HashMap

class FontsOverride private constructor(ctx: Context) {
    private val customFontTypeface: Typeface

    init {
        customFontTypeface = Typeface.createFromAsset(ctx.assets, fontname)
    }

    fun setCustomFont() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val newMap = HashMap<String, Typeface>()
            newMap["default"] = customFontTypeface
            newMap["serif"] = customFontTypeface
            newMap["monospace"] = customFontTypeface
            newMap["sans-serif"] = customFontTypeface
            try {
                val staticField = Typeface::class.java.getDeclaredField("sSystemFontMap")
                staticField.isAccessible = true
                staticField.set(null, newMap)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
        replaceFont("DEFAULT", customFontTypeface)
        replaceFont("MONOSPACE", customFontTypeface)
        replaceFont("SERIF", customFontTypeface)
        replaceFont("SANS_SERIF", customFontTypeface)
    }

    fun overrideFonts(v: View) {
        try {
            if (v is ViewGroup) {
                for (i in 0 until v.childCount) {
                    val child = v.getChildAt(i)
                    overrideFonts(child)
                }
            } else if (v is TextView) {
                v.typeface = customFontTypeface
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun replaceFont(staticTypefaceFieldName: String, newTypeface: Typeface) {
        try {
            val staticField = Typeface::class.java.getDeclaredField(staticTypefaceFieldName)
            staticField.isAccessible = true
            staticField.set(null, newTypeface)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val fontname = "fonts/Exo2-ExtraLight.ttf"

        private var fontsOverride: FontsOverride? = null

        fun instance(ctx: Context): FontsOverride {
            if (fontsOverride == null) {
                fontsOverride = FontsOverride(ctx)
            }
            return fontsOverride!!
        }
    }
}
