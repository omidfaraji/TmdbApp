package com.faraji.challenge.tmdb.utils

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    private var visibleThreshold = 1


    private var previousTotalItemCount = 0

    var loading = true


    var layoutManager: RecyclerView.LayoutManager? = null
        set(value) {
            field = value
            if (value is GridLayoutManager) {
                visibleThreshold *= value.spanCount
            } else if (value is StaggeredGridLayoutManager) {
                visibleThreshold *= value.spanCount
            }
            this.previousTotalItemCount = 0
        }

    constructor() {}

    constructor(layoutManager: RecyclerView.LayoutManager) {
        this.layoutManager = layoutManager
    }


    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (dy < 0)
            return
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager!!.itemCount

        lastVisibleItemPosition = when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager ->
                (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager ->
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            else -> throw  NotImplementedError("${layoutManager!!.javaClass.simpleName} is not supported")
        }
        Log.i("onScrolled","lastVisibleItemPosition:$lastVisibleItemPosition")

        if (loading && totalItemCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
        }
        if (!loading && lastVisibleItemPosition + visibleThreshold >= totalItemCount
            && view.adapter!!.itemCount > visibleThreshold
        ) {
            onLoadMore()
        }
    }


    abstract fun onLoadMore()

}
