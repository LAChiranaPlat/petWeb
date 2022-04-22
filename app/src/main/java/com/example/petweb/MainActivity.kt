package com.example.petweb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.petweb.databinding.ActivityMainBinding
import com.google.gson.Gson

import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var myViews:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        myViews= ActivityMainBinding.inflate(layoutInflater)

        val peticiones=Volley.newRequestQueue(this)

        setContentView(myViews.root)

        val cajaBuscar=myViews.cajaBuscar
        val btnBuscar=myViews.btnBuscar
        val capital=myViews.txtCapital
        val moneda=myViews.txtMoneda
        val idiomas=myViews.txtIdioma



        btnBuscar.setOnClickListener {

            var url="https://restcountries.com/v3.1/name/${cajaBuscar.editText?.text}"

            /**/
            val request=JsonArrayRequest(
                url,
                Response.Listener {
                    Log.i("result","Objetos Obtenidos: ${it.length()}")
                    // Log.i("result","Objeto Json: ${it}")

                    var obj=JSONObject(it.get(0).toString())

                    //Log.i("result"," Objeto: $obj")
                    var objPais=JSONObject(obj.get("name").toString())
                    Log.i("result"," País: ${objPais.get("common")}")

                    var langPais=JSONObject(objPais.get("nativeName").toString())

                    var claves=langPais.keys()//retorna las claves de un objeto
                    var lenguajes=""

                    for (index in claves){
                        lenguajes +=index.toString()+", "
                    }

                    Log.i("result"," Idiomas: ${lenguajes }")
                    val x= arrayOf(obj.get("capital"))
                    capital.text= x[0].toString()
                    idiomas.text= lenguajes

                    var monedaJson=JSONObject(obj.get("currencies").toString())
                    Log.i("result"," Idiomas: ${monedaJson }")

                    var clavesMoneda=monedaJson.keys()//retorna las claves de un objeto

                    for (index in clavesMoneda){
                        var json=JSONObject(monedaJson.get(index).toString())
                         moneda.text="${json.get("name").toString()} - ${json.get("symbol").toString()}"
                    }

                    var f=JSONObject(obj.get("flags").toString())
                    //var ll=JSONObject(obj.get("latlng").toString())

                    Log.i("result"," Location: ${obj.get("latlng")}")

                    Glide
                        .with(this)
                        .load(f.get("png"))
                        .centerCrop()
                        .placeholder(R.drawable.flag)
                        .into(myViews.imgFlag);

                },
                Response.ErrorListener {
                    Log.e("result",it.toString())
                }
            )

            peticiones.add(request)

            /**/
        }


}
}
