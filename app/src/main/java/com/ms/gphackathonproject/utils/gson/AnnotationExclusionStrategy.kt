package com.gm.lollipop.utils.gson

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.ms.gphackathonproject.utils.gson.Exclude

/*
 @Author: Mehedi Hasan
* @Date: 11/25/20
*/
class AnnotationExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(Exclude::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }
}