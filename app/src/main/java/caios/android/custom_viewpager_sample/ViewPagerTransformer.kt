package caios.android.custom_viewpager_sample

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ViewPagerTransformer : ViewPager2.PageTransformer {
    var direction = 0

    override fun transformPage(page: View, position: Float) {
        (page as ConstraintLayout).apply {
            val parentWidth = (parent as RecyclerView).width
            val parentHeight = (parent as RecyclerView).height

            when {
                position < -1                                       -> {
                    getChildAt(0).apply {
                        layoutParams.apply {
                            width = parentWidth
                            height = parentHeight
                        }
                        requestLayout()
                    }

                    visibility = View.GONE
                    translationZ = 0f
                }
                position > 1                                        -> {
                    getChildAt(0).apply {
                        layoutParams.apply {
                            width = parentWidth
                            height = parentHeight
                        }
                        requestLayout()
                    }

                    visibility = View.GONE
                    translationZ = 0f
                }
                position in -1.0..1.0 && direction == GESTURE_LEFT  -> {
                    getChildAt(0).apply {
                        layoutParams.apply {
                            width = (parentWidth.toDouble() * (1 - abs(position))).toInt()
                            height = parentHeight
                        }
                        requestLayout()
                    }

                    visibility = View.VISIBLE
                    translationZ = 1f
                    x = 0f
                }
                position in -1.0..1.0 && direction == GESTURE_RIGHT -> {
                    getChildAt(0).apply {
                        layoutParams.apply {
                            width = (parentWidth.toDouble() * (1 - abs(position))).toInt()
                            height = parentHeight
                        }
                        requestLayout()
                    }

                    visibility = View.VISIBLE
                    translationZ = 0f
                    x
                }
            }
        }
    }

    companion object {
        const val GESTURE_LEFT = 22
        const val GESTURE_RIGHT = 23
    }
}