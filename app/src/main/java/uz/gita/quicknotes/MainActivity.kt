package uz.gita.quicknotes

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import uz.gita.quicknotes.R

class MainActivity : AppCompatActivity() {

    //Step 1: making nav controller
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#006262")))

        //Step 2:
        navController = findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)
    }

    //Step 3:
    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
}