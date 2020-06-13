package com.parallel.hati.randomalarm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Music : RealmObject() {
    @PrimaryKey
    var id : Int = 0
    var uri : String = ""
}