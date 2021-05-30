package com.biblublab.skelettonmviflow.homeFeature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.biblublab.skelettonmviflow.R
import com.biblublab.skelettonmviflow.common.custom.beginShimmerAnim
import com.biblublab.skelettonmviflow.common.custom.endShimmerAnim
import com.biblublab.skelettonmviflow.common.custom.toast
import com.biblublab.skelettonmviflow.common.custom.visible
import com.biblublab.skelettonmviflow.common.viewBinding.viewBinding
import com.biblublab.skelettonmviflow.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

   private val binding by viewBinding(FragmentHomeBinding::bind)
    private val homeViewModel : HomeViewModel by viewModel()

    private val homeAdapter by lazy {
        HomeAdapter { view, news ->
            homeViewModel.processAction(HomeAction.OnClickItem(view, news))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.homeState.collect{
                renderHomeState(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.uiEffects.collect{
                renderUiEffect(it)            }
        }
        binding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.processAction(HomeAction.FetchNews)
        }
    }

    private fun renderHomeState(homeState: HomeState){
        when(homeState.homeStatus){
            HomeStatus.Fetching -> {
                binding.newsListView.visible(false)
                binding.loadingShimmer.beginShimmerAnim()
            }
            HomeStatus.Fetched -> {
                binding.swipeToRefresh.isRefreshing = false
                binding.loadingShimmer.endShimmerAnim()
                binding.newsListView.visible(true)
            }
            HomeStatus.NotFetched -> homeViewModel.processAction(HomeAction.FetchNews)
            is HomeStatus.Error -> context?.toast(homeState.homeStatus.mess)
        }
        homeAdapter.submitList(homeState.newsList)
    }

    private fun renderUiEffect(homeEffect: HomeEffect){
        when(homeEffect){
            is HomeEffect.OpenDetail -> findNavController().navigate(R.id.action_HomeFragment_to_DetailFragment)
        }
    }
}