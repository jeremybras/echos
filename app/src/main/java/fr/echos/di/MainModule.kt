package fr.echos.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideResources(
        @ApplicationContext appContext: Context,
    ): Resources {
        return appContext.resources
    }

    @Named("ioDispatcher")
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
