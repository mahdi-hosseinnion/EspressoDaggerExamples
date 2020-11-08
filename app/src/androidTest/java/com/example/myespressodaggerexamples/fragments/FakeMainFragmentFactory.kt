package com.example.myespressodaggerexamples.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.myespressodaggerexamples.repository.MainRepository
import com.example.myespressodaggerexamples.ui.DetailFragment
import com.example.myespressodaggerexamples.ui.FinalFragment
import com.example.myespressodaggerexamples.ui.ListFragment
import com.example.myespressodaggerexamples.ui.UICommunicationListener
import com.example.myespressodaggerexamples.util.GlideManager
import com.example.myespressodaggerexamples.util.GlideRequestManager
import com.example.myespressodaggerexamples.viewmodels.FakeMainViewModelFactory
import com.example.myespressodaggerexamples.viewmodels.MainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
class FakeMainFragmentFactory
@Inject
constructor(
    val viewModelFactory: FakeMainViewModelFactory,
    val requestManager: GlideManager
) : FragmentFactory() {

    // used for setting a mock<UICommunicationListener>
    lateinit var uiCommunicationListener: UICommunicationListener

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            ListFragment::class.java.name -> {
                val fragment = ListFragment(viewModelFactory, requestManager)
                if (::uiCommunicationListener.isInitialized) {
                    fragment.setUICommunicationListener(uiCommunicationListener)
                }
                fragment
            }

            DetailFragment::class.java.name -> {
                val fragment = DetailFragment(viewModelFactory, requestManager)
                if (::uiCommunicationListener.isInitialized) {
                    fragment.setUICommunicationListener(uiCommunicationListener)
                }
                fragment
            }

            FinalFragment::class.java.name -> {
                val fragment = FinalFragment(viewModelFactory, requestManager)
                if (::uiCommunicationListener.isInitialized) {
                    fragment.setUICommunicationListener(uiCommunicationListener)
                }
                fragment
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }

    }

}