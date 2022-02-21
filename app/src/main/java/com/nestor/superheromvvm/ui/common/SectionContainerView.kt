package com.nestor.superheromvvm.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.nestor.superheromvvm.R
import com.nestor.superheromvvm.databinding.SectionContainerBinding

class SectionContainerView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attrs, defStyle) {
    private var sectionContainerBinding: SectionContainerBinding =
        SectionContainerBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            sectionContainerBinding.title.text = value
        }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        attrs?.let {
            context
                .theme
                .obtainStyledAttributes(it, R.styleable.SectionContainerView, 0, 0)
                .apply {
                    try {
                        title = getString(R.styleable.SectionContainerView_text)
                    } finally {
                        recycle()
                    }
                }
        }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        sectionContainerBinding.viewContainer.addView(child, params)
    }
}