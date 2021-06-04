package lamarao.jose.weatherapp.ui.current_Location

import android.Manifest
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.databinding.FragmentCurrentLocationBinding
import lamarao.jose.weatherapp.ui.hasPermission


class CurrentLocationFragment : Fragment() {

    private lateinit var currentLocationModelFactory: CustomViewModelFactory
    private lateinit var hourlyAdapter : RvHourlyAdapter
    private lateinit var dailyAdapter: RvDailyAdapter

    private lateinit var _binding: FragmentCurrentLocationBinding
    private val binding get() = _binding

    private val currentLocationViewModel: CurrentLocationViewModel by lazy {
        val application: Application = requireNotNull(this.activity).application
        currentLocationModelFactory = CustomViewModelFactory(application)
        ViewModelProvider(this,currentLocationModelFactory).get(CurrentLocationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentLocationViewModel.currentLocationWeather.observe(viewLifecycleOwner, { currentLocationWeather ->
            currentLocationWeather?.apply {
                hourlyAdapter.data = currentLocationWeather.hourly
                dailyAdapter.data = currentLocationWeather.daily.subList(1,8)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_location, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = currentLocationViewModel
        hourlyAdapter = RvHourlyAdapter()
        dailyAdapter = RvDailyAdapter()

        currentLocationViewModel.startLocationUpdates()

        currentLocationViewModel.userLocation.observe(viewLifecycleOwner, { userLocation ->
            userLocation?.apply{
                currentLocationViewModel.refreshCurrentLocationWeather(userLocation.lat,userLocation.lon)
            }
        })

        binding.root.findViewById<RecyclerView>(R.id.rv_hour).apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = hourlyAdapter
        }

        binding.root.findViewById<RecyclerView>(R.id.rv_weekday).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dailyAdapter
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        if (!requireContext().hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            currentLocationViewModel.stopLocationUpdates()
        }
    }

}