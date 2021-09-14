package com.codecad.common

import kotlin.math.pow

class Line(val origin: PointD, val direction: PointD){
    companion object{
        operator fun Double.times(other: PointD): PointD {
            return PointD(other.x * this, other.y * this, other.z * this)
        }

        private fun projectPlane(a: Plane, b: Plane) : PointD {
            return (a.distance - b.distance * ( a.normal.dot(b.normal) ))/
                    (1 - a.normal.dot(b.normal).pow(2.0)) * a.normal
        }

        fun projectPlaneFull(a: Plane, b: Plane) : PointD {
            return (a.distance * b.normal.squaredLength() - b.distance * ( a.normal.dot(b.normal) ))/
                    (a.normal.squaredLength() * b.normal.squaredLength() - a.normal.dot(b.normal).pow(2.0)) * a.normal
        }

        fun fromPlanes(a: Plane, b: Plane) : Line {
            val a = a.normalized()
            val b = b.normalized()
            val direction = a.normal.cross(b.normal)
            val origin = projectPlane(a,b) + projectPlane(b,a)
            return Line(origin, direction)
        }
    }
}

class Plane(val normal : PointD, val distance: Double){
    companion object{
        val XY = Plane(PointD(0.0,0.0,1.0), 0.0)
        val YZ = Plane(PointD(1.0,0.0,0.0), 0.0)
        val ZX = Plane(PointD(0.0,1.0,0.0), 0.0)

        fun fromPoints(a: PointD, b: PointD, c: PointD) : Plane {
            val ab = b - a
            val ac = c - a
            val normal = ab.cross(ac).normalized()
            val distance = normal.dot(a)
            return Plane(normal, distance)
        }

        fun fromPoints(points : List<PointD>) : Plane {
            return fromPoints(points[0], points[1], points[2])
        }

        fun fromConvexPoints(points : Iterable<PointD>) : Plane {
            return fromConvexPoints(points.iterator())
        }

        fun fromConvexPoints(points : Iterator<PointD>) : Plane {
            return fromPoints(getNextOr(points), getNextOr(points), getNextOr(points))
        }

        fun getNextOr(points : Iterator<PointD>) : PointD{
            return points.next()
        }
    }

    fun normalized() : Plane {
        val length = normal.length()
        return Plane(normal / length, distance / length)
    }

    fun flip(offset: Double = 0.0) : Plane{
        return Plane(normal * -1.0, distance * -1 + offset)
    }

    fun move( offset: Double ) : Plane{
        return Plane(normal, distance + offset)
    }

    fun distanceTo(p: PointD) : Double{
        return normal.dot(p) - distance
    }

    fun projectTo(p: PointD) : PointD {
        return p - normal * distanceTo(p)
    }
}
