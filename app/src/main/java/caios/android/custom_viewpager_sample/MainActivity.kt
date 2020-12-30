package caios.android.custom_viewpager_sample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import caios.android.custom_viewpager_sample.ViewPagerTransformer.Companion.GESTURE_LEFT
import caios.android.custom_viewpager_sample.ViewPagerTransformer.Companion.GESTURE_RIGHT
import caios.android.custom_viewpager_sample.databinding.ActivityMainBinding

internal val TAG = "<TAG>"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewPagerAdapter by lazy { ViewPagerAdapter(dataList) }
    private val viewPagerTransformer by lazy { ViewPagerTransformer() }
    private val dataList by lazy {
        listOf(PagerData(getBitmap(R.drawable.dog1), getColorInt(R.color.colorDefault0)),
                PagerData(getBitmap(R.drawable.dog2), getColorInt(R.color.colorDefault1)),
                PagerData(getBitmap(R.drawable.dog3), getColorInt(R.color.colorDefault2)),
                PagerData(getBitmap(R.drawable.dog4), getColorInt(R.color.colorDefault3)),
                PagerData(getBitmap(R.drawable.dog5), getColorInt(R.color.colorDefault4)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ViewPager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = viewPagerAdapter
            setPageTransformer(ViewPagerTransformer())
            setCurrentItem(1, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                private var currentPosition = -1
                private var realPosition = -1
                private var sumPosition = 0f

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    when {
                        position + positionOffset > sumPosition -> viewPagerTransformer.direction = GESTURE_LEFT
                        position + positionOffset < sumPosition -> viewPagerTransformer.direction = GESTURE_RIGHT
                    }
                    sumPosition = position.toFloat() + positionOffset
                }

                override fun onPageSelected(position: Int) {
                    currentPosition = position
                }

                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        when (currentPosition) {
                            0                 -> realPosition = dataList.size
                            dataList.size + 1 -> realPosition = 1
                        }

                        if (realPosition != -1) {
                            binding.ViewPager2.setCurrentItem(realPosition, false)
                            realPosition = -1
                        }
                    }
                }
            })
        }
    }

    private fun getBitmap(@DrawableRes id: Int): Bitmap {
        return BitmapFactory.decodeResource(resources, id)
    }

    private fun getColorInt(@ColorRes id: Int): Int {
        return ContextCompat.getColor(this, id)
    }
}