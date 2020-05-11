package com.jthou.github.network.services

import com.jthou.github.network.entites.User
import com.jthou.github.network.retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 *
 *
 * @author jthou
 * @version 1.0.0
 * @date 01-03-2020
 */
interface UserApi {

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>

}

object UserService : UserApi by retrofit.create(UserApi::class.java)