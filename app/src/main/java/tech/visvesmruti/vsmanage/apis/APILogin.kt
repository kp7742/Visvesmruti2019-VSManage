package tech.visvesmruti.vsmanage.apis

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast

import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.activities.DashboardActivity
import com.appizona.yehiahd.fastsave.FastSave

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class APILogin(private val activity: Activity) : AsyncTask<String, Void, String>() {
    private val client: OkHttpClient
    private var dialog: ProgressDialog? = null
    private lateinit var id: String

    init {
        this.client = OkHttpClient()
    }

    override fun onPreExecute() {
        dialog = ProgressDialog(activity)
        dialog!!.setMessage("Logging in...")
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    override fun doInBackground(vararg strings: String): String? {
        id = strings[0]
        val formBody = FormBody.Builder()
                .add("email", strings[0])
                .add("pass", strings[1])
                .build()

        val request = Request.Builder()
                .url("https://visvesmruti.tech/api/login/")
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
                    Toast.makeText(activity.applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
                    FastSave.getInstance().saveString(Consts.TOKEN, result.getString("ApiToken"))
                    FastSave.getInstance().saveString(Consts.ID, id)
                    val i = Intent(activity, DashboardActivity::class.java)
                    i.putExtra(Consts.NAME, result.getString(Consts.NAME))
                    i.putExtra(Consts.TYPE, result.getString(Consts.TYPE))
                    i.putExtra(Consts.DEPT, result.getString(Consts.DEPT))
                    i.putExtra(Consts.EVENT, result.getString(Consts.EVENT))
                    activity.startActivity(i)
                    activity.finish()
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
