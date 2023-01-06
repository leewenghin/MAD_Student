package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityMainBinding
import com.example.project.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show Home Page whenever open the app
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.home_nav

        // When open the app will show HomeFragment
        replaceFragment(StdDashboardFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_nav -> replaceFragment(StdDashboardFragment())
                R.id.title_nav -> replaceFragment(TitleFragment())
                R.id.thesis_nav -> replaceFragment(ThesisFragment())
                R.id.poster_nav-> replaceFragment(PosterFragment())
                R.id.proposal_nav-> replaceFragment(ProposalFragment())

                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}