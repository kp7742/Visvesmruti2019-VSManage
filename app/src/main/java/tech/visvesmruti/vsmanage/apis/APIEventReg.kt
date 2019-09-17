package tech.visvesmruti.vsmanage.apis

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast

import com.appizona.yehiahd.fastsave.FastSave

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.activities.PayingAmountActivity

class APIEventReg(private val activity: Activity, private val count: Int, private val isPaid: Boolean) : AsyncTask<String, Void, String>() {
    private val client: OkHttpClient
    private var dialog: ProgressDialog? = null
    private lateinit var email: String

    init {
        this.client = OkHttpClient()
    }

    override fun onPreExecute() {
        dialog = ProgressDialog(activity)
        dialog!!.setMessage("Participation in progress...")
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    override fun doInBackground(vararg strings: String): String? {
        email = strings[1]
        val formbuilder = FormBody.Builder()
        formbuilder.add("apikey", FastSave.getInstance().getString(Consts.TOKEN, ""))
        formbuilder.add("teamlength", Integer.toString(count))
        formbuilder.add("eventcode", strings[0])
        for (i in 0 until count) {
            formbuilder.add("email$i", strings[i + 1])
        }

        val formBody = formbuilder.build()

        val request = Request.Builder()
                .url("https://visvesmruti.tech/api/event/register/")
                .post(formBody)
                .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    return response.body!!.string()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(s: String?) {
        dialog!!.cancel()
        if (s == null) {
            Toast.makeText(activity, "Error!, Internet", Toast.LENGTH_LONG).show()
        } else {
            try {
                val result = JSONObject(s)
                val code = result.getInt("Code")
                if (code == 200) {
                    if (isPaid) {
                        val i = Intent(activity, PayingAmountActivity::class.java)
                        i.putExtra(Consts.ID, 1)
                        i.putExtra(Consts.EMAIL, email)
                        i.putExtra(Consts.AMOUNT, result.getString(Consts.AMOUNT))
                        i.putExtra(Consts.MSG, "AMOUNT THEY HAVE TO PAY")
                        i.putExtra(Consts.ERCODE, result.getString(Consts.ERCODE))
                        activity.startActivity(i)
                    }
                    Toast.makeText(activity, result.getString("Message"), Toast.LENGTH_LONG).show()
                } else if (code == 404) {
                    Toast.makeText(activity, result.getString("Message"), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Error!, Wrong Status", Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(activity, "Error!, Result Problem", Toast.LENGTH_LONG).show()
            }

        }
    }
}
