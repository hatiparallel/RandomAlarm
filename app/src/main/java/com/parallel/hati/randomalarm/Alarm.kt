package com.parallel.hati.randomalarm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Alarm : RealmObject() {
    @PrimaryKey
    var id : Int = 0
    var hour : Int = 8
    var minute : Int = 30
    var musiclist : RealmList<Music> = RealmList()
}