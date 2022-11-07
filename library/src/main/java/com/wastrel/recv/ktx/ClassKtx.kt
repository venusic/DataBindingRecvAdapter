package com.wastrel.recv.ktx

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Find parameterized type
 *  注意如果多层继承使用相同类型的泛型，返回最近的一个。
 *  例如
 *  classA<T:View>
 *  classB<T:View>:classA<TextView>
 *  classC:classB<Button>
 *  以上继承关系，target参数为View.class，当dst参数为ClassB.class时，返回Button，当dst参数为ClassA.class时返回TextViw
 * @param T
 * @param target 目标类型，例如 T:ViewBinding  此时target传ViewBinding的class
 * @param dstClz 用于表示搜索结束，一般为泛型声明类本身
 * @return 返回搜索到的类型
 */
fun <T> Class<T>.findParameterizedType(target: Class<*>, dstClz: Class<*>): Type? {
    var clz: Class<*> = this
    var rs: Type? = null
    while (true) {
        if (dstClz.isInterface) {
            val genericInterfaces = clz.genericInterfaces.find {
                (it as ParameterizedType).rawType == dstClz
            }
            if (genericInterfaces != null)
                (genericInterfaces as ParameterizedType).actualTypeArguments.forEach {
                    if (it is Class<*> && target.isAssignableFrom(it)) {
                        rs = it
                    }
                }
        } else {
            val generic = clz.genericSuperclass
            if (generic is ParameterizedType) {
                val typeArguments = generic.actualTypeArguments
                typeArguments.forEach {
                    if (it is Class<*> && target.isAssignableFrom(it)) {
                        rs = it
                    }
                }
            }
        }
        clz = clz.superclass
        if (clz == Any::class.java || clz == Object::class.java || clz == dstClz) break
    }
    if (rs == null)
        throw ClassCastException("${this.name} 不是继承自 ${dstClz.name},或者${dstClz.name} 没有泛型类型")
    else return rs
}
