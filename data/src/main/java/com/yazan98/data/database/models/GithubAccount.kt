package com.yazan98.data.database.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class GithubAccount : RealmObject() {

    @PrimaryKey
    var id: Long = 0
    var login: String = "" 
    var avatar_url: String = "" 
    var url: String = "" 
    var type: String = "" 
    var name: String = "" 
    var company: String = "" 
    var blog: String? = null 
    var location: String = "" 
    var email: String? = null 
    var hireable: Boolean = false 
    var bio: String = "" 
    var public_repos: Long = 0 
    var public_gists: Long = 0 
    var followers: Long = 0 
    var following: Long = 0
    var created_at: String = "" 
    var updated_at: String = ""
    var entityType: String = ""

}
