package com.example.swipe

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class PageSource (private val itemDao: Dao, private val index: Long) : PagingSource<Int, ItemList>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemList> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        return try {
            val items = itemDao.getListPage(index) //, limit, offset

            for (item in items)
            {
                Log.d("MyLogMAct", "item {${item.item}" )
            }

            val prevKey = if (offset == 0) null else offset - limit
            val nextKey = offset + limit

            LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.d("MyLogMAct", "NOT WORKING KHE KHE MEOW")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemList>): Int? {
        TODO("Not yet implemented")
    }
}

