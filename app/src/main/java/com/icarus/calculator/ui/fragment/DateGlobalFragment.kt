package com.icarus.calculator.ui.fragment

import a.icarus.component.BaseFragment
import a.icarus.impl.RepeatThread
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icarus.calculator.R
import com.icarus.calculator.databinding.FragmentDateGlobalBinding
import com.icarus.calculator.model.DateGlobalViewModel
import com.icarus.calculator.ui.activity.RegionActivity

class DateGlobalFragment : BaseFragment() {
    private lateinit var binding: FragmentDateGlobalBinding
    private lateinit var model: DateGlobalViewModel
    private lateinit var thread: RepeatThread
    private lateinit var launch: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false)
        return binding.root
    }

    override fun setLayout(): Int {
        return R.layout.fragment_date_global
    }

    override fun initView(view: View?) {
    }

    override fun initData() {
        model = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DateGlobalViewModel::class.java)
        binding.model = model
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        thread = RepeatThread {
            binding.model?.update()
            Thread.sleep(500)
        }
        thread.start()
    }

    override fun onStop() {
        super.onStop()
        thread.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                it.data?.run {
                    model.modifyRegion(getStringExtra("id"), getStringExtra("name"))
                }
            }

    }

    override fun initListener() {
        binding.toRegionListActivity.setOnClickListener {
            launch.launch(Intent(context, RegionActivity::class.java))
        }
    }
}