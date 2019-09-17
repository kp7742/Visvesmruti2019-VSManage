package tech.visvesmruti.vsmanage.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import de.hdodenhof.circleimageview.CircleImageView
import tech.visvesmruti.vsmanage.R
import java.lang.Exception

class CodersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coders)

        val mEmail = findViewById<ImageView>(R.id.email)
        val mGithub = findViewById<ImageView>(R.id.github)
        val mProfileImage = findViewById<CircleImageView>(R.id.profile_image)
        val mEmail2 = findViewById<ImageView>(R.id.email2)
        val mGithub2 = findViewById<ImageView>(R.id.github2)
        val mProfileImage2 = findViewById<CircleImageView>(R.id.profile_image2)

        mProfileImage.isBorderOverlay = true
        mProfileImage2.isBorderOverlay = true

        mEmail.setOnClickListener {
            val i = Intent("android.intent.action.SEND")
            i.setType("message/rfc822")
            i.putExtra("android.intent.extra.EMAIL", arrayOf("patel.kuldip91@gmail.com"))
            i.putExtra("android.intent.extra.SUBJECT", "Hello Kuldip")
            i.putExtra("android.intent.extra.TEXT", "")
            try {
                startActivity(Intent.createChooser(i, "Report..."))
            } catch (e: Exception){
                Toast.makeText(this@CodersActivity, "Can't find email client.", Toast.LENGTH_SHORT).show()
            }
        }

        mEmail2.setOnClickListener {
            val i = Intent("android.intent.action.SEND")
            i.setType("message/rfc822")
            i.putExtra("android.intent.extra.EMAIL", arrayOf("aadil.fetr@gmail.com"))
            i.putExtra("android.intent.extra.SUBJECT", "Hello Aadil")
            i.putExtra("android.intent.extra.TEXT", "")
            try {
                startActivity(Intent.createChooser(i, "Report..."))
            } catch (e: Exception){
                Toast.makeText(this@CodersActivity, "Can't find email client.", Toast.LENGTH_SHORT).show()
            }
        }

        mGithub.setOnClickListener {
            startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://github.com/kp7742")))
        }

        mGithub2.setOnClickListener {
            startActivity(Intent("android.intent.action.VIEW", Uri.parse("https://github.com/aadil1715")))
        }
    }
}
