package lamarao.jose.weatherapp.ui.cities

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
import lamarao.jose.weatherapp.databinding.FragmentCitiesBinding

class Cities_Fragment : Fragment() {

    private lateinit var citiesViewModelFactory: CustomViewModelFactory
    private var viewModelAdapter: RvCitiesAdapter? = null

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!

    private val citiesViewModel: Cities_ViewModel by lazy {
        val application = requireNotNull(this.activity).application
        citiesViewModelFactory = CustomViewModelFactory(application)
        ViewModelProvider(this,citiesViewModelFactory).get(Cities_ViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citiesViewModel.citiesWeather.observe(viewLifecycleOwner, { citiesweather ->
            citiesweather?.apply {
                viewModelAdapter?.data = citiesweather.list
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cities, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = citiesViewModel
        viewModelAdapter = RvCitiesAdapter()

        binding.root.findViewById<RecyclerView>(R.id.rvcities).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
