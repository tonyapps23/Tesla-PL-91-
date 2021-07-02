package com.tslax.tesla_xprogrampl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.InfoClass
import com.retrofit.RetrofitInstance
import com.retrofit.UserInfo

import com.tslax.tesla_xprogrampl.databinding.ActivityRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userField()

    }
    private fun userField() {
        binding.poai.setOnClickListener {
            val ed1stName = binding.ed1stName.text.toString().trim()
            val ed2stName = binding.edSecondName.text.toString().trim()
            val ed_email = binding.edEmail.text.toString().trim()
            val ed_tel = binding.edPhone.text.toString().trim()
            val countrypicker = binding.codePicker.selectedCountryNameCode.toString()



            if(ed1stName.isEmpty()){
                binding.ed1stName.error = "Wpisz swoje imię"
                binding.ed1stName.requestFocus()
                return@setOnClickListener

            }

            if(ed2stName.isEmpty()){
                binding.edSecondName.error = "Proszę podać swoje nazwisko"
                binding.edSecondName.requestFocus()
                return@setOnClickListener

            }

            if(ed_email.isEmpty()){
                binding.edEmail.error = "Wpisz mail"
                binding.edEmail.requestFocus()
                return@setOnClickListener

            }else if(!Patterns.EMAIL_ADDRESS.matcher(ed_email).matches()){
                binding.edEmail.error = "Proszę podać poprawne Email"
                binding.edEmail.requestFocus()
                return@setOnClickListener


            }
            if(ed_tel.isEmpty()){
                binding.edPhone.error = "Wprowadź swój telefon"
                binding.edPhone.requestFocus()
                return@setOnClickListener

            }
            binding.poai.setBackgroundResource(R.drawable.bg_color_select)
            binding.poai.isEnabled = false

            val userinfo = UserInfo(countrypicker,ed_email,ed1stName,"60b61dd93f1f950026a86f9f",ed2stName,ed_tel,"hjhygsxdere")
            val call : Call<InfoClass> = RetrofitInstance.api.signUpUser(userinfo)

            call.enqueue(object : Callback<InfoClass> {
                override fun onResponse(call: Call<InfoClass>, response: Response<InfoClass>) {
                    if(response.isSuccessful){
                        Log.d("Info", "Status: ${response.body()?.status}" )
                        Log.d("Info", "Pr: ${response.body()?.data?.pr}" )
                        Log.d("Info", "Redirect: ${response.body()?.data?.redirect}" )

                        when {
                            response.body()?.data?.pr.equals("success") -> {
                                val i = Intent(
                                    this@RegistrationActivity,
                                    ThanksActivity::class.java
                                ).apply {
                                    putExtra("Name", userinfo.first_name)
                                    putExtra("Second", userinfo.last_name)

                                }
                                startActivity(i)
                                binding.poai.setBackgroundResource(R.drawable.bg_selector)
                                binding.poai.isEnabled = true
                            }
                            response.body()?.data?.pr.equals("exist") -> {
                                Toast.makeText(this@RegistrationActivity,"Jesteś już zarejestrowany!",
                                    Toast.LENGTH_LONG).show()
                                binding.poai.setBackgroundResource(R.drawable.bg_selector)
                                binding.poai.isEnabled = true
                            }
                            response.body()?.data?.pr.equals("redirect") -> {
                                val i = Intent(this@RegistrationActivity,WebViewActivity::class.java).apply {
                                    putExtra("Url",response.body()?.data?.redirect.toString())
                                }
                                startActivity(i)
                                binding.poai.setBackgroundResource(R.drawable.bg_selector)
                                binding.poai.isEnabled = true
                            }
                        }

                    }
                    else {
                        Toast.makeText(applicationContext,"Wystąpił błąd, spróbuj ponownie", Toast.LENGTH_SHORT).show()
                        binding.poai.setBackgroundResource(R.drawable.bg_selector)
                        binding.poai.isEnabled = true
                        return
                    }

                }

                override fun onFailure(call: Call<InfoClass>, t: Throwable) {
                    Toast.makeText(applicationContext,"Sprawdź swoje łącze internetowe", Toast.LENGTH_SHORT).show()
                    binding.poai.setBackgroundResource(R.drawable.bg_selector)
                    binding.poai.isEnabled = true
                }


            })



        }

    }
}