package com.codecad.common

import java.lang.RuntimeException
import kotlin.math.asin
import kotlin.math.sqrt

class PointD(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0){

    override fun toString(): String {
        return "com.codecad.core.PointD(x=$x, y=$y, z=$z)"
    }

    operator fun get(index: Int) : Double{
        return when(index){
            0 -> x
            1 -> y
            2 -> z
            3 -> 1.0
            else -> throw RuntimeException("sss")
        }
    }

    operator fun set(index: Int, value: Double) {
        when(index){
            0 -> x = value
            1 -> y = value
            2 -> z = value
            3 -> return
            else -> throw RuntimeException("sss")
        }
    }

    operator fun minus(other: PointD): PointD {
        return PointD(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    operator fun plus(other: PointD): PointD {
        return PointD(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun times(other: Double): PointD {
        return PointD(x * other, y * other, z * other)
    }

    fun crossZ(other: PointD): Double{
        return this.x * other.y - this.y * other.x
    }

    fun dot(other: PointD): Double{
        return this.x * other.x + this.y * other.y + this.z * other.z
    }

    operator fun div(other: Double) : PointD {
        return PointD(this.x / other, this.y / other, this.z / other)
    }

    fun cross(other: PointD, result: PointD = PointD()): PointD {
        val x = y * other.z - z * other.y
        val y = z * other.x - this.x * other.z
        result.z = this.x * other.y - this.y * other.x
        result.x = x
        result.y = y
        return result
    }

    fun squaredLength() : Double{
        return x*x + y*y + z*z
    }

    fun length() : Double{
        return sqrt(squaredLength())
    }

    fun normalized() : PointD {
        return this / length()
    }

    fun projectTo(target: PointD) : PointD {
        return target.dot(this) / target.squaredLength() * target
    }

    fun angleTo(other: PointD) : Double{
        return asin(cross(other).length() / (length() * other.length()))
    }

    fun squaredDistanceTo(other: PointD) : Double{
        return (this - other).squaredLength()
    }

    fun distanceTo(other: PointD) : Double{
        return sqrt(squaredDistanceTo(other))
    }

    fun asList() : List<Double>{
        return listOf(x,y,z)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PointD) return false

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    companion object{
        val ZERO = PointD(0.0,0.0,0.0)
    }
}

operator fun Double.times(other: PointD): PointD {
    return PointD(other.x * this, other.y * this, other.z * this)
}
