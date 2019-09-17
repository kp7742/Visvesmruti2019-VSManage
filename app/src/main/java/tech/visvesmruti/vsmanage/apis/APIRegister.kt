package tech.visvesmruti.vsmanage.apis

import android.app.Activity
import android.app.ProgressDialog
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

class APIRegister(private val activity: Activity) : AsyncTask<String, Void, String>() {
    private val client: OkHttpClient
    private var dialog: ProgressDialog? = null

    init {
        this.client = OkHttpClient()
    }

    override fun onPreExecute() {
        dialog = ProgressDialog(activity)
        dialog!!.setMessage("Registering....")
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    override fun doInBackground(vararg strings: String): String? {
        val formBody = FormBody.Builder()
                .add("apikey", FastSave.getInstance().getString(Consts.TOKEN, ""))
                .add("email", strings[0])
                .add("fname", strings[1])
                .add("lname", strings[2])
                .add("college", strings[3])
                .add("department", strings[4])
                .add("semester", strings[5])
                .add("mobile", strings[6])
                .add("gender", strings[7])
                .build()

        val request = Request.Builder()
                .url("https://visvesmruti.tech/api/register/")
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
                val message = result.getString("Message")
                if (code == 200) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                } else if (code == 404) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
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
