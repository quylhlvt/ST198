package com.female.maker.oc.creator.module

import android.content.Context
import com.female.maker.oc.creator.data.callapi.ApiHelper
import com.female.maker.oc.creator.data.repository.ApiRepository
import com.female.maker.oc.creator.data.repository.RoomRepository
import com.female.maker.oc.creator.utils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun providerSharedPreference(@ApplicationContext appContext: Context): SharedPreferenceUtils {
        return SharedPreferenceUtils.Companion.getInstance(appContext)
    }
    @Singleton
    @Provides
    fun providerApi(@ApplicationContext context: Context): ApiHelper {
        return ApiHelper(context)
    }
    @Singleton
    @Provides
    fun providerApiRepository(apiHelper: ApiHelper): ApiRepository {
        return ApiRepository(apiHelper)
    }
    @Singleton
    @Provides
    fun providerRepository(@ApplicationContext context: Context): RoomRepository {
        return RoomRepository(context)
    }
}