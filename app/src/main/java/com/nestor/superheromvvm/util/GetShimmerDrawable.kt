package com.nestor.superheromvvm.util

import android.graphics.drawable.Drawable
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

public val appShimmer: Shimmer by lazy {
    Shimmer.ColorHighlightBuilder()
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()
}