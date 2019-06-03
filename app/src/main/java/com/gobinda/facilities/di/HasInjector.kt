package com.gobinda.facilities.di

interface HasInjector<T> {
    fun injector(): T
}