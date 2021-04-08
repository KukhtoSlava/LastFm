package com.slavakukhto.lastfm.shared.data.repositoryimpl

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.completable.completable
import com.badoo.reaktive.completable.doOnAfterComplete
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.single
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.slavakukhto.lastfm.shared.data.mappers.TimeStampPeriodMapper
import com.slavakukhto.lastfm.shared.data.mappers.UserProfileMapper
import com.slavakukhto.lastfm.shared.data.source.LocalStorage
import com.slavakukhto.lastfm.shared.domain.models.TimeStampPeriod
import com.slavakukhto.lastfm.shared.domain.models.UserProfile
import com.slavakukhto.lastfm.shared.domain.repository.UserRepository

class UserRepositoryImpl(
    private val localStorage: LocalStorage,
    private val timeStampPeriodMapper: TimeStampPeriodMapper,
    private val userProfileMapper: UserProfileMapper
) : UserRepository {

    private val changeTimestampPeriodSubject =
        BehaviorSubject(timeStampPeriodMapper.parsePeriod(localStorage.timeStampPeriod))

    override fun getUserName(): Single<String> {
        return single { emitter ->
            emitter.onSuccess(localStorage.userName)
        }
    }

    override fun setUserName(userName: String): Completable {
        return completable { emitter ->
            localStorage.userName = userName
            emitter.onComplete()
        }
    }

    override fun getUserProfile(): Single<UserProfile> {
        return single { emitter ->
            emitter.onSuccess(userProfileMapper.transformToObject(localStorage.userProfileJson))
        }
    }

    override fun updateUserProfile(userProfile: UserProfile): Completable {
        return completable { emitter ->
            localStorage.userProfileJson = userProfileMapper.transformToJson(userProfile)
            emitter.onComplete()
        }
    }

    override fun getUserTimeStamp(): Single<TimeStampPeriod> {
        return single { emitter ->
            emitter.onSuccess(timeStampPeriodMapper.parsePeriod(localStorage.timeStampPeriod))
        }
    }

    override fun setUserTimeStamp(timeStampPeriod: TimeStampPeriod): Completable {
        return completable { emitter ->
            localStorage.timeStampPeriod = timeStampPeriod.name
            emitter.onComplete()
        }
            .doOnAfterComplete {
                changeTimestampPeriodSubject.onNext(timeStampPeriod)
            }
    }

    override fun clearUserData(): Completable {
        return completable { emitter ->
            localStorage.clear()
            emitter.onComplete()
        }
    }

    override fun timeStampPeriodChangeState(): Observable<TimeStampPeriod> {
        return changeTimestampPeriodSubject
    }
}
