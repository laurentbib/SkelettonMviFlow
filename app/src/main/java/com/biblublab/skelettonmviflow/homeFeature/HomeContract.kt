package com.biblublab.skelettonmviflow.homeFeature

import android.view.View
import com.biblublab.domain.homeFeature.entities.HomeNews

data class HomeState(val homeStatus: HomeStatus, val newsList : List<HomeItemView>)

sealed class HomeEffect(){
    data class OpenDetail(val itemView : View, val news: HomeNews) : HomeEffect()
}

sealed class HomeStatus{
    object Fetching : HomeStatus()
    object Fetched : HomeStatus()
    object NotFetched : HomeStatus()
    data class Error(val mess : Int) : HomeStatus()

}

sealed class HomeAction(){
    object FetchNews : HomeAction()
    data class OnClickItem(val itemView : View, val news: HomeNews) : HomeAction()
}
