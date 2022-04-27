package com.example.restaurantapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val foodType = resources.getStringArray(R.array.food_type)

        val typeSpinner = findViewById<Spinner>(R.id.food_type)

        val orderButton: Button = findViewById(R.id.order_button)

        orderButton.setOnClickListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        if(typeSpinner != null){
            val typeAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, foodType)
            typeSpinner.adapter = typeAdapter

            typeSpinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if(p0?.getItemAtPosition(p2).toString() == "Veg"){
                        showVeg()
                    } else {
                        showNonVeg()
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    private fun showVeg() {
        val drinks = resources.getStringArray(R.array.drinks)
        val drinkSpinner = findViewById<Spinner>(R.id.drinks)
        val drinkAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,drinks)
        drinkSpinner.adapter = drinkAdapter

        val soups = resources.getStringArray(R.array.veg_soups)
        val soupSpinner = findViewById<Spinner>(R.id.soups)
        val soupAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,soups)
        soupSpinner.adapter = soupAdapter

        val starters = resources.getStringArray(R.array.veg_starters)
        val starterSpinner = findViewById<Spinner>(R.id.starters)
        val starterAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,starters)
        starterSpinner.adapter = starterAdapter

        val mainDish = resources.getStringArray(R.array.veg_main_dish)
        val mainDishSpinner = findViewById<Spinner>(R.id.maindish)
        val mainDishAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,mainDish)
        mainDishSpinner.adapter = mainDishAdapter

        val desert = resources.getStringArray(R.array.deserts)
        val desertSpinner = findViewById<Spinner>(R.id.deserts)
        val desertAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,desert)
        desertSpinner.adapter = desertAdapter
    }

    private fun showNonVeg() {
        val drinks = resources.getStringArray(R.array.drinks)
        val drinkSpinner = findViewById<Spinner>(R.id.drinks)
        val drinkAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,drinks)
        drinkSpinner.adapter = drinkAdapter

        val soups = resources.getStringArray(R.array.non_veg_soups)
        val soupSpinner = findViewById<Spinner>(R.id.soups)
        val soupAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,soups)
        soupSpinner.adapter = soupAdapter

        val starters = resources.getStringArray(R.array.non_veg_starters)
        val starterSpinner = findViewById<Spinner>(R.id.starters)
        val starterAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,starters)
        starterSpinner.adapter = starterAdapter

        val mainDish = resources.getStringArray(R.array.nonveg_main_dish)
        val mainDishSpinner = findViewById<Spinner>(R.id.maindish)
        val mainDishAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,mainDish)
        mainDishSpinner.adapter = mainDishAdapter

        val desert = resources.getStringArray(R.array.deserts)
        val desertSpinner = findViewById<Spinner>(R.id.deserts)
        val desertAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,desert)
        desertSpinner.adapter = desertAdapter
    }
}