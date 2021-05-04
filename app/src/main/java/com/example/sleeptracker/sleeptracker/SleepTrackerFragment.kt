package com.example.sleeptracker.sleeptracker

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sleeptracker.R
import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.databinding.FragmentSleepTrackerBinding

class SleepTrackerFragment : Fragment() {

    private lateinit var binding : FragmentSleepTrackerBinding
    private lateinit var viewModel: SleepTrackerViewModel
    private lateinit var viewModelFactory: SleepTrackerViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sleep_tracker, container, false)
        val application : Application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.sleepTrackerViewModel = viewModel
        binding.setLifecycleOwner(this)

        val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter


        viewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.i("SleepTracker",it.size.toString())
                adapter.submitList(it?.toMutableList())
            }
        })

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner,Observer{ night ->
            night?.let {

                findNavController().navigate(
                    SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment2(
                        night.nightId
                    )
                )
                viewModel.onNavigateToSleepQualityComplete()

            }
        })


        return binding.root
    }


}