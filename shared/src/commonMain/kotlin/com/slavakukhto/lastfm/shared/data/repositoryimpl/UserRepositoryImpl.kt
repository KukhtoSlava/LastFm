package com.slavakukhto.lastfm.shared.data.repositoryimpl

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.completable.completable
import com.badoo.reaktive.single.Single
import com.badoo.reaktive.single.single
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

    override fun getUserName(): Single<String> {
        return single { localStorage.userName }
    }

    override fun setUserName(userName: String): Completable {
        return completable { localStorage.userName = userName }
    }

    override fun getUserProfile(): Single<UserProfile> {
        return single {
            userProfileMapper.transformToObject(localStorage.userProfileJson)
        }
    }

    override fun updateUserProfile(userProfile: UserProfile): Completable {
        return completable {
            localStorage.userProfileJson = userProfileMapper.transformToJson(userProfile)
        }
    }

    override fun getUserTimeStamp(): Single<TimeStampPeriod> {
        return single {
            timeStampPeriodMapper.parsePeriod(localStorage.timeStampPeriod)
        }
    }

    override fun setUserTimeStamp(timeStampPeriod: TimeStampPeriod): Completable {
        return completable {
            localStorage.timeStampPeriod =
                timeStampPeriodMapper.getPeriodTopValuesQuery(timeStampPeriod)
        }
    }

    override fun clearUserData(): Completable {
        return completable { localStorage.clear() }
    }
}
