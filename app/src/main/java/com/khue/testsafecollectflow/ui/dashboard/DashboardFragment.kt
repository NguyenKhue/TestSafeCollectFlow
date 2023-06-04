package com.khue.testsafecollectflow.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.khue.testsafecollectflow.databinding.FragmentDashboardBinding
import com.khue.testsafecollectflow.ui.home.HomeViewModel
import com.khue.testsafecollectflow.ui.home.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.testCallBackFlow) {
            Log.d("HomeFragment", "TestCallFlow collected $it")
        }

        observe(viewModel.networkStatus) {
            Log.d("HomeFragment", "TestCallFlow collected $it")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}