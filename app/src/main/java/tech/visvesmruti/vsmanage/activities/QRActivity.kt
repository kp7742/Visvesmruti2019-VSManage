package tech.visvesmruti.vsmanage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R

class QRActivity : AppCompatActivity() {
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val mQrdecoderview: QRCodeReaderView = findViewById(R.id.qrdecoderview)
        mQrdecoderview.setOnQRCodeReadListener { text, _ ->
            if(count++ == 1 && text.startsWith("VS-")) {
                val i = Intent(this@QRActivity, TakeAttendanceActivity::class.java)
                i.putExtra(Consts.SID, text)
                startActivity(i)
                finish()
            }
        }
        mQrdecoderview.setQRDecodingEnabled(true)
        mQrdecoderview.setAutofocusInterval(2000L)
        mQrdecoderview.setBackCamera()
        mQrdecoderview.startCamera()
    }
}
