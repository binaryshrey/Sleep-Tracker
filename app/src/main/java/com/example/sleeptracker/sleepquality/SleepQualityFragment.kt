package com.example.sleeptracker.sleepquality

import android.app.Application
import android.os.Bundle
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
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {

    private lateinit var binding : FragmentSleepQualityBinding
    private lateinit var viewModel: SleepQualityViewModel
    private lateinit var viewModelFactory: SleepQualityViewModelFactory
    private lateinit var application: Application
    private lateinit var dataSource : SleepDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sleep_quality, container, false)
        application = requireNotNull(this.activity).application
        dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val args = SleepQualityFragmentArgs.fromBundle(requireArguments())

        viewModelFactory = SleepQualityViewModelFactory(args.id,dataSource)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SleepQualityViewModel::class.java)

        binding.sleepQualityViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer { navigated ->
            if(navigated){
                findNavController().navigate(R.id.action_sleepQualityFragment2_to_sleepTrackerFragment)
                viewModel.onNavigationCompleted()
            }
        })


        return binding.root
    }


}