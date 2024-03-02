package com.b1nd.alimo.presentation.feature.onboarding.second

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomSnackBar
import com.b1nd.alimo.presentation.feature.onboarding.second.OnboardingSecondViewModel.Companion.ON_CLICK_START
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSecondFragment:
    BaseFragment<FragmentOnboardingSecondBinding, OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: OnboardingSecondViewModel by viewModels()

    override fun initView() {
        val snackBar = CustomSnackBar.make(requireView(), "세션이 만료 되었어요")
//        snackBar.show()
        viewModel.alarmCheck()

        collectStateFlow(viewModel.alarmState){
            if(!it){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Q는 Android 10을 나타냅니다.
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 30)
                    }
                }
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