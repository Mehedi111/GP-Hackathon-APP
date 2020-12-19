package com.ms.gphackathonproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ms.gphackathonproject.Constants
import com.ms.gphackathonproject.data.model.DiscoverContent
import com.ms.gphackathonproject.databinding.LayoutHomeParentBinding

/**
 * Created by Mehedi Hasan on 12/19/2020.
 */
class HomeParentAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeParentAdapter.HomeParentViewHolder>() {

    private val dataList: MutableList<DiscoverContent> = ArrayList()

    fun insertNewPatch(data: DiscoverContent) {
        dataList.add(data)
        notifyDataSetChanged()
    }

    class HomeParentViewHolder(val binding: LayoutHomeParentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeParentViewHolder {
        val binding =
            LayoutHomeParentBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeParentViewHolder, position: Int) {
        val content = dataList[position]

        holder.binding.patchName.text = dataList[position].contentType

        if (content.contentType == Constants.TYPE_TRENDING) {
            val layoutManager = GridLayoutManager(context, 2)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            holder.binding.childRv.layoutManager = layoutManager

        } else {
            holder.binding.childRv.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            holder.binding.childRv.setHasFixedSize(true)
        }

        holder.binding.childRv.adapter =
            HomeChildAdapter(
                context,
                content.contentType ?: Constants.TYPE_MOVIE,
                content.results
            )
    }

    override fun getItemCount(): Int = dataList.size
}