package com.example.parstagrampart1

import org.json.JSONArray
import org.json.JSONObject

class TimelineApplication {
    var createdAt: String = ""
    var user: User? = null
    var description: String = ""
    var imageUrl: String = ""
    companion object {
        fun fromJson(jsonObject: JSONObject): TimelineApplication {
            val posts = TimelineApplication()
            posts.createdAt = jsonObject.getString("createdAt")
            posts.user = User.fromJson(jsonObject.getJSONObject("user"))
            posts.description = jsonObject.getString("description")
            posts.imageUrl = jsonObject.getString("Image")
            return posts
        }

        fun fromJsonArray(jsonArray: JSONArray): List<TimelineApplication> {
            val posts = ArrayList<TimelineApplication>()
            for (i in 0 until jsonArray.length()) {
                posts.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return posts
        }
    }
}