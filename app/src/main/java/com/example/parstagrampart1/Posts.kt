package com.example.parstagrampart1

import com.parse.ParseObject
import com.parse.ParseUser
import org.json.JSONObject
import java.io.File

class Posts {
    var description: String = ""
    var user: String = ""
    var createdAt: String = ""



    companion object {
        fun fromJson(jsonObject: JSONObject): Posts {
            val posts = Posts()
            posts.description = jsonObject.getString("description")
            posts.createdAt = jsonObject.getString("createdAt")
            posts.user = jsonObject.getString("user")
            return posts
        }
    }
}