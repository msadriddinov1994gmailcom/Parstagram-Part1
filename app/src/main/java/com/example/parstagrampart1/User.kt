package com.example.parstagrampart1

import org.json.JSONObject

class User {
    var name: String = ""
    companion object {
        fun fromJson(jsonObject: JSONObject): User {
            val user = User()
            user.name = jsonObject.getString("name")
            return user
        }
    }
}