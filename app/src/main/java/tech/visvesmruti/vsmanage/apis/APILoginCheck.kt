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

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class APILoginCheck(private val activity: Activity) : AsyncTask<Void, Void, String>() {
    private val client: OkHttpClient
    private var dialog: ProgressDialog? = null

    init {
        this.client = OkHttpClient()
    }

    override fun onPreExecute() {
        dialog = ProgressDialog(activity)
        dialog!!.setMessage("Logging in...")
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    override fun doInBackground(vararg voids: Void): String? {
        val formBody = FormBody.Builder()
                .add("apikey", FastSave.getInstance().getString(Consts.TOKEN, ""))
                .build()

        val request = Request.Builder()
                .url("https://visvesmruti.tech/api/check/")
                .post(formBody)
                .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    return response.body!!.string()
                }
            }
        } catch (e: Exception) {
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
                    val i = Intent(activity, DashboardActivity::class.java)
                    i.putExtra(Consts.NAME, result.getString(Consts.NAME))
                    i.putExtra(Consts.TYPE, result.getString(Consts.TYPE))
                    i.putExtra(Consts.DEPT, result.getString(Consts.DEPT))
                    i.putExtra(Consts.EVENT, result.getString(Consts.EVENT))
                    activity.startActivity(i)
                    activity.finish()
                } else if (code == 404) {
                    Toast.makeText(activity, result.getString("Message"), Toast.LENGTH_LONG).show()
                    FastSave.getInstance().deleteValue(Consts.TOKEN)
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
