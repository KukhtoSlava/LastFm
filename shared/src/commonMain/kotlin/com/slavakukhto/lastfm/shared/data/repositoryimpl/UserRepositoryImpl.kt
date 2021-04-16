package com.slavakukhto.lastfm.shared.data.repositoryimpl

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.completable.completableFromFunction
import com.badoo.reaktive.completable.doOnAfterComplete
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.singleFromFunction
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
        return singleFromFunction {
            localStorage.userName
        }
    }

    override fun setUserName(userName: String): Completable {
        return completableFromFunction {
            localStorage.userName = userName
        }
    }

    override fun getUserProfile(): Single<UserProfile> {
        return singleFromFunction {
            userProfileMapper.transformToObject(localStorage.userProfileJson)
        }
    }

    override fun updateUserProfile(userProfile: UserProfile): Completable {
        return completableFromFunction {
            localStorage.userProfileJson = userProfileMapper.transformToJson(userProfile)
        }
    }

    override fun getUserTimeStamp(): Single<TimeStampPeriod> {
        return singleFromFunction {
            timeStampPeriodMapper.parsePeriod(localStorage.timeStampPeriod)
        }
    }

    override fun setUserTimeStamp(timeStampPeriod: TimeStampPeriod): Completable {
        return completableFromFunction {
            localStorage.timeStampPeriod = timeStampPeriod.name
        }
            .doOnAfterComplete {
                changeTimestampPeriodSubject.onNext(timeStampPeriod)
            }
    }

    override fun clearUserData(): Completable {
        return completableFromFunction {
            localStorage.clear()
        }
    }

    override fun timeStampPeriodChangeState(): Observable<TimeStampPeriod> {
        return changeTimestampPeriodSubject
    }
}
