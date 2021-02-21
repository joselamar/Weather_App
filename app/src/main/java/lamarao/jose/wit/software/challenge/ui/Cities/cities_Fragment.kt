package lamarao.jose.wit.software.challenge.ui.Cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cities.*
import lamarao.jose.wit.software.challenge.R
import lamarao.jose.wit.software.challenge.adapter.rv_cities_adapter


class cities_Fragment : Fragment() {

    private lateinit var citiesViewModel: cities_ViewModel
    private var cities_layoutManager: RecyclerView.LayoutManager? = null
    private var cities_adapter : RecyclerView.Adapter<rv_cities_adapter.ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val selectedUnit = sharedPref.getString("Unit", "metric")!!

        citiesViewModel = ViewModelProviders.of(this, CustomViewModelFactory(selectedUnit)).get(cities_ViewModel::class.java)

        cities_layoutManager = LinearLayoutManager(getContext())
        rv_cities.layoutManager = cities_layoutManager

        val dividerItemDecoration = DividerItemDecoration(rv_cities.context, (cities_layoutManager as LinearLayoutManager).orientation)
        rv_cities.addItemDecoration(dividerItemDecoration)

        citiesViewModel.response.observe(viewLifecycleOwner, Observer {
            c_progress_bar.isVisible = false
            c_progressbar_info.isVisible = false

            cities_adapter = rv_cities_adapter(it.list,selectedUnit)
            rv_cities.adapter = cities_adapter
        } )
    }


}