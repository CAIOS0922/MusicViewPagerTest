package caios.android.custom_viewpager_sample

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import caios.android.custom_viewpager_sample.databinding.ViewPagerItemBinding

data class PagerData(val bitmap: Bitmap, val color: Int)

class ViewPagerHolder(binding: ViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val rootLayout = binding.root
    val imageLayout = binding.ImageLayout
    val imageView = binding.imageView
}

class ViewPagerAdapter(private val dataList: List<PagerData>) : RecyclerView.Adapter<ViewPagerHolder>() {

    private val realDataList = dataList.toMutableList().apply {
        add(0, dataList.last())
        add(dataList.first())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(ViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        with(holder) {
            val data = realDataList[position]
            rootLayout.setBackgroundColor(data.color)
            imageLayout.setBackgroundColor(data.color)
            imageView.setImageBitmap(data.bitmap)
        }
    }

    override fun getItemCount(): Int = dataList.size + 2
}