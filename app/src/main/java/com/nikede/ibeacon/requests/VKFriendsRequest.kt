//package com.nikede.vk_sdk_code_template.requests
//
//import com.nikede.vk_sdk_code_template.models.FriendModel
//import com.vk.api.sdk.requests.VKRequest
//import org.json.JSONObject
//
//class VKFriendsRequest() : VKRequest<ArrayList<FriendModel>>("friends.get") {
//
//    val TAG = "VKFriendsRequest"
//
//    init {
//        addParam("fields", "photo_50, nickname, city, online")
//    }
//
//    override fun parse(r: JSONObject): ArrayList<FriendModel> {
//        val friends = r.getJSONObject("response").getJSONArray("items")
//        val result = ArrayList<FriendModel>()
//        for (i in 0 until friends.length()) {
//            result.add(FriendModel.parse(friends.getJSONObject(i)))
//        }
//        return result
//    }
//}