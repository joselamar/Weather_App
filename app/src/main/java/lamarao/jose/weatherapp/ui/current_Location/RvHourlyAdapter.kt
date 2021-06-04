package lamarao.jose.weatherapp.ui.current_Location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import lamarao.jose.weatherapp.R
import lamarao.jose.weatherapp.database.Hourly
import lamarao.jose.weatherapp.databinding.HourItemBinding

class RvHourlyAdapter: RecyclerView.Adapter<HourlyViewHolder>() {

    var data: List<Hourly> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val withDataBinding : HourItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            HourlyViewHolder.LAYOUT,
            parent,
            false)
        return HourlyViewHolder(withDataBinding)
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(viewHolder: HourlyViewHolder, position: Int) {
        viewHolder.viewDataBinding.also {
            it.hourly = data[position]
        }
    }
}

class HourlyViewHolder(val viewDataBinding: HourItemBinding ) : RecyclerView.ViewHolder(viewDataBinding.root) {
   companion object{
       @LayoutRes
       val LAYOUT = R.layout.hour_item
   }
}