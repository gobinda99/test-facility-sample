package com.gobinda.facilities.di

import javax.inject.Scope

/**
 * Scope for instances which live as long as the fragment
 */
@Scope
@Retention
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION)
annotation class FragmentScope