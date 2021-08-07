package com.codecad.common

class PointD(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0){


    override fun toString(): String {
        return "com.codecad.core.PointD(x=$x, y=$y, z=$z)"
    }

    operator fun minus(other: PointD): PointD {
        return PointD(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    operator fun plus(other: PointD): PointD {
        return PointD(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    fun cross(other: PointD): Double{
        return this.x * other.y - this.y * other.x
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
}
