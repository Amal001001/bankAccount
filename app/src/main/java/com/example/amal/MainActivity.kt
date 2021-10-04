package com.example.amal

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var money: ArrayList<String>
    lateinit var rv: RecyclerView
    lateinit var rvAdapter: recyclerviewadapter
    lateinit var tvcurrentbalance: TextView
    lateinit var etdeposit:EditText
    lateinit var buttondeposit: Button
    lateinit var etwithdraw: EditText
    lateinit var buttonwithdraw: Button
    var currentbalance = 0
    lateinit var mainlayout: ConstraintLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // lateinit var sharedPreferences: SharedPreferences
        money = ArrayList()

        rv = findViewById(R.id.rv)
        rvAdapter = recyclerviewadapter(money)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)

        tvcurrentbalance = findViewById(R.id.tvcurrentbalance)
        tvcurrentbalance.text = "Current Balance: " + currentbalance.toString()

        etdeposit = findViewById(R.id.etdeposit)

        buttondeposit = findViewById(R.id.buttondeposit)
        buttondeposit.setOnClickListener{ deposit() }

        etwithdraw = findViewById(R.id.etwithdraw)

        buttonwithdraw = findViewById(R.id.buttonwithdraw)
        buttonwithdraw.setOnClickListener{ withdraw() }


        mainlayout = findViewById(R.id.mainlayout)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        currentbalance = sharedPreferences.getInt("currentbalance",currentbalance)


     }

    fun deposit(){
        val depositop = etdeposit.text.toString()
        if(depositop.isNotEmpty()) {
            val depositop = etdeposit.text.toString().toInt()
            currentbalance += depositop
            money.add("Deposit: $depositop")
            rvAdapter.notifyDataSetChanged()
            color()
            tvcurrentbalance.text = "Current Balance: " + currentbalance.toString()
            etdeposit.text.clear()
            etdeposit.clearFocus()

            with(sharedPreferences.edit()) {
                putInt("currentbalance", currentbalance)
                apply()
            }
        }
    }

    fun withdraw(){
        val withdrawop = etwithdraw.text.toString()
        if(withdrawop.isNotEmpty()){
        val withdrawop = etwithdraw.text.toString().toInt()
            if (currentbalance > 0) {
                if (withdrawop <= currentbalance) {
                    currentbalance -= withdrawop
                    tvcurrentbalance.text = "Current Balance: " + currentbalance.toString()
                    money.add("Withdrawal: $withdrawop")
                    rvAdapter.notifyDataSetChanged()
                } else if (withdrawop > currentbalance) {
                    currentbalance = currentbalance - withdrawop - 20
                    tvcurrentbalance.text = "Current Balance: " + currentbalance.toString()
                    money.add("Withdrawal: $withdrawop")
                    money.add("Negative Balance Fee: 20")
                    rvAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(this, "Insufficient funds", Toast.LENGTH_LONG).show()
                //Toast.makeText(mainlayout,"f",Toast.LENGTH_LONG).show() <<something wrong!
            }
            color()
            etwithdraw.text.clear()
            etwithdraw.clearFocus()

            with(sharedPreferences.edit()) {
                putInt("currentbalance", currentbalance)
                apply()
            }
        }
    }

    fun color(){
        if (currentbalance < 0)
            tvcurrentbalance.setTextColor(Color.RED)
        else tvcurrentbalance.setTextColor(Color.GRAY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CurrentBalance", currentbalance)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentbalance = savedInstanceState.getInt("CurrentBalance", 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clear -> {
                //  money.removeAll()
                rvAdapter.deleteItems()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}