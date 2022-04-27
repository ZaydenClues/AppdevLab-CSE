package com.example.bankingapp

import android.os.Bundle
import java.util.*
import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun navigateFrag(fragment: Fragment, addToStack: Boolean, bundle: Bundle?)
}