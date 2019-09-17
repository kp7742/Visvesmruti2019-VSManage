package tech.visvesmruti.vsmanage

import android.app.Application
import android.content.Context
import com.appizona.yehiahd.fastsave.FastSave

class VSApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FastSave.init(this)
    }
}
