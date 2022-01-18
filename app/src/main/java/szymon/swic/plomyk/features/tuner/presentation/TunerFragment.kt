package szymon.swic.plomyk.features.tuner.presentation

import kotlinx.android.synthetic.main.fragment_tuner.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment
import szymon.swic.plomyk.core.permissions.PermissionType
import szymon.swic.plomyk.core.permissions.PermissionsHelper


class TunerFragment : BaseFragment<TunerViewModel>(R.layout.fragment_tuner) {

    override val viewModel: TunerViewModel by viewModel()
    private var permissionToRecordGranted = false

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordGranted = requestCode == PermissionType.RECORD_AUDIO.requestCode &&
            PermissionsHelper.hasPermissionBeenGranted(grantResults)

        if (!permissionToRecordGranted) viewModel.goBack()
    }

    override fun initViews() {
        super.initViews()
        activity?.title = resources.getString(R.string.title_tuner)
        button_start.setOnClickListener {
            PermissionsHelper.handlePermissions(requireActivity(), PermissionType.RECORD_AUDIO) {
                viewModel.startAnalysing()
            }
        }
        button_stop.setOnClickListener { viewModel.stopAnalysing() }
    }

    override fun initObservers() {
        super.initObservers()
        observeFrequency()
    }

    private fun observeFrequency() {
        viewModel.frequency.observe(viewLifecycleOwner) {
            text_curr_frequency.text = "$it Hz"
        }
    }
}
