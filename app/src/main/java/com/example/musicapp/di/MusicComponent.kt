//package com.example.musicapp.di
//
//import com.example.musicapp.MainActivity
//import com.example.musicapp.views.ClassicFragment
//import com.example.musicapp.views.PopFragment
//import com.example.musicapp.views.RockFragment
//import dagger.Component
//
///**
// * Here is where gonna be inject the items located in the modules class whit the annotation @Provides
// */
//
//@Component(
//    modules = [NetworkModule::class,
//        ApplicationModule::class,
//        PresentersModule::class]
//)
//interface MusicComponent {
//    fun inject(mainActivity: MainActivity)
//
//    fun inject(rockFragment: RockFragment)
//
//    fun inject(classicFragment: ClassicFragment)
//
//    fun inject(popFragment: PopFragment)
//
//}