package com.nitishsharma.swipemart.di

import com.nitishsharma.swipemart.data.remote.SwipeMartRemoteRepoImpl
import com.nitishsharma.swipemart.data.remote.SwipeMartRemoteRepo
import org.koin.dsl.module

val repositoryModule = module(override = true) {
    factory<SwipeMartRemoteRepo> { SwipeMartRemoteRepoImpl() }
}