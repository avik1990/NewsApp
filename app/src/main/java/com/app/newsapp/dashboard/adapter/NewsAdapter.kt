package com.app.newsapp.dashboard.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.customviews.RobotoSlabBoldTextView
import com.app.customviews.RobotoSlabRegularTextView
import com.app.newsapp.R
import com.app.newsapp.dashboard.model.Article
import com.app.newsapp.dashboard.model.NewsResponse
import com.app.newsapp.utils.getFormattedDate
import com.squareup.picasso.Picasso

class NewsAdapter(
    private val context: Context,
    private val newsList: List<Article>,
    private val onNewsSelected: onRowItemSelected,
    private val from: String

) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    lateinit var rowView: View

    interface onRowItemSelected {
        fun getPosition(pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_newsitem, parent, false)

        rowView = itemView
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (!newsList[position].urlToImage.isNullOrBlank()) {
            Picasso.get()
                .load(newsList[position].urlToImage)
                .resize(1024, 628)
                .onlyScaleDown()
                .into(holder.imgvwBankIcon)
        }

        holder.tvSource.text = newsList[position].source!!.name

        holder.tvHeader.text = newsList[position].title
        holder.tv_date.text = getFormattedDate(newsList[position].publishedAt!!)

        holder.card_view.setOnClickListener {
            onNewsSelected.getPosition(position)
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHeader: RobotoSlabRegularTextView
        var imgvwBankIcon: ImageView
        var tvSource: RobotoSlabBoldTextView
        var tv_date: TextView
        var card_view: CardView

        init {
            tvHeader = itemView.findViewById(R.id.tv_header)
            imgvwBankIcon = itemView.findViewById(R.id.iv_img)
            tvSource = itemView.findViewById(R.id.tv_source)
            tv_date = itemView.findViewById(R.id.tv_date)
            card_view = itemView.findViewById(R.id.card_view)
        }

    }

}