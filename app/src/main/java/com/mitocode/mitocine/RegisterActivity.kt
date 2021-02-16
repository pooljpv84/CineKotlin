package com.mitocode.mitocine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mitocode.mitocine.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //regreso en el action var
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title= getString(R.string.str_register_now)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->
            {
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}