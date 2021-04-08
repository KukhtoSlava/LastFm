package com.slavakukhto.lastfm.shared.domain.repository

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.single.Single
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.models.UserProfile

interface UserRepository {

    fun getUserName(): Single<String>

    fun setUserName(userName: String): Completable

    fun getUserProfile(): Single<UserProfile>

    fun updateUserProfile(userProfile: UserProfile): Completable

    fun getUserTimeStamp(): Single<TimeStampPeriod>

    fun setUserTimeStamp(timeStampPeriod: TimeStampPeriod): Completable

    fun clearUserData(): Completable

    fun timeStampPeriodChangeState(): Observable<TimeStampPeriod>
}
