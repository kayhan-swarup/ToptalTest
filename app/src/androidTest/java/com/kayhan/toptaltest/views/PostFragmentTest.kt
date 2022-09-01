package com.kayhan.toptaltest.views

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.kayhan.toptaltest.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.kayhan.toptaltest.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@MediumTest
@HiltAndroidTest
class PostFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Inject
    lateinit var fragmentFactory: PostFragmentFactory

    @Test
    fun navigateToPostDetailsTest(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<PostFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)

        }
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            PostFragmentDirections.actionPostFragmentToPostDetailsFragment()
        )
    }
}