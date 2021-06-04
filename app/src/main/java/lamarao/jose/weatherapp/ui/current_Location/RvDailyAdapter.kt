package lamarao.jose.weatherapp.ui.current_Location

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.database.Daily
import lamarao.jose.weatherapp.database.Hourly
import lamarao.jose.weatherapp.databinding.DayItemBinding
import lamarao.jose.weatherapp.databinding.HourItemBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class RvDailyAdapter : RecyclerView.Adapter<DailyViewHolder>() {

    var data: List<Daily> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val withDataBinding : DayItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DailyViewHolder.LAYOUT,
            parent,
            false)
        return DailyViewHolder(withDataBinding)
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(viewHolder: DailyViewHolder, position: Int) {
        viewHolder.viewDataBinding.also {
            it.day = data[position]
        }
    }

}

class DailyViewHolder(val viewDataBinding: DayItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object{
        @LayoutRes
        val LAYOUT = R.layout.day_item
    }
}