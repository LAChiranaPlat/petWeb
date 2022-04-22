package com.example.petweb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import org.json.JSONArray
import org.json.JSONObject


class testCarrito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_carrito)

        val prefManager= PreferenceManager.getDefaultSharedPreferences(this)//iniciamos con un admin
        var y=prefManager.getString("lst","user")

        var list=ArrayList<List>()
        list= ArrayList()

        var e=JSONArray(y)

        for (item in 0 until e.length()-1){
            var obj=JSONObject(e.get(item).toString())
            list.add(List(
                obj.get("nombres").toString(),
                obj.get("apellidos").toString(),
                obj.get("dni").toString()
                ))
        }


        Log.i("result",list.toString())
    }
}