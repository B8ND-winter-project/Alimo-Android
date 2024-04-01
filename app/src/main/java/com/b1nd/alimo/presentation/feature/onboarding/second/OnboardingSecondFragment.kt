package com.b1nd.alimo.presentation.feature.onboarding.second

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.second.OnboardingSecondViewModel.Companion.ON_CLICK_START
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSecondFragment :
    BaseFragment<FragmentOnboardingSecondBinding, OnboardingSecondViewModel>(
        R.layout.fragment_onboarding_second
    ) {
    override val viewModel: OnboardingSecondViewModel by viewModels()
    private val args: OnboardingSecondFragmentArgs by navArgs()


    override fun initView() {
        viewModel.tokenCheck()
        // RefreshToken 만료됐다면 SnackBar Show

        collectStateFlow(viewModel.tokenState){
            if (it.token == "만료") {
                Dlog.d("initView: $it")
                requireContext().shortToast("세션이 만료되었습니다")
                viewModel.tokenReset()
            }
        }


        // 현재 Android 버전이 10보다 크면 알림 권한 창을 뛰움
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Q는 Android 10을 나타냄
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS), 30)
            }
        }



        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_START -> {
                        findNavController().navigate(R.id.action_onboardingSecond_to_onboardingThird)

                    }
                }
            }
        }
    }

}