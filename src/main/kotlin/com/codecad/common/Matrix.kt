package com.codecad.common

import kotlin.math.cos
import kotlin.math.sin

class Transformation{
    companion object{
        fun translation(translation: PointD) : Matrix {
            val values = DoubleArray(16)

            values[0] = 1.0
            values[5] = 1.0
            values[10] = 1.0

            values[3] = translation.x
            values[7] = translation.y
            values[11] = translation.z
            values[15] = 1.0

            return Matrix(4, 4, values)
        }

        fun rotation(axis : PointD, angle: Double) : Matrix {
            val cosA = cos(angle)
            val cosA2 = 1 - cosA
            val sinA = sin(angle)

            val values = DoubleArray(16)

            /*values[3] = 0.0
            values[7] = 0.0
            values[11] = 0.0
            values[12] = 0.0
            values[13] = 0.0
            values[14] = 0.0*/
            values[15] = 1.0

            values[1] = axis.x * axis.y * cosA2
            values[4] = axis.x * axis.y * cosA2

            values[2] = axis.x * axis.z * cosA2
            values[8] = axis.x * axis.z * cosA2

            values[6] = axis.y * axis.z * cosA2
            values[8] = axis.y * axis.z * cosA2

            values[0] = axis.x * axis.x * cosA2
            values[5] = axis.y * axis.y * cosA2
            values[10] = axis.z * axis.z * cosA2

            values[1] -= axis.z * sinA
            values[4] += axis.z * sinA

            values[2] += axis.y * sinA
            values[8] -= axis.y * sinA

            values[6] -= axis.x * sinA
            values[9] += axis.x * sinA

            values[0] += cosA
            values[5] += cosA
            values[10] += cosA

            return Matrix(4,4,  values)
        }

        fun fromPlanes(source: Plane, target: Plane) : Matrix {
            val angle = source.normal.angleTo(target.normal)
            val axis = target.normal.cross(source.normal)

            val translate = translation(source.normal * -source.distance)
            val rotate = rotation(axis,  angle)
            val translate2 = translation(target.normal * target.distance)

            return translate2 * rotate * translate
        }
    }
}

class Matrix(val rows : Int, val cols: Int,  val values : DoubleArray = DoubleArray(rows * cols) ) {

    init {
        assert(values.size % rows == 0)
    }

    operator fun get(row: Int, col: Int) : Double{
        return values[row * cols + col]
    }

    operator fun set(row: Int, col: Int, value: Double) {
        values[row * cols + col] = value
    }

    operator fun times( vector : PointD) : PointD {
        val result = PointD()

        for(row in 0 until rows){
            for(col in 0 until cols ){
                result[row] += this[row, col] * vector[col]
            }
        }

        return PointD(result[0], result[1], result[2])
    }

    operator fun times( other : Matrix) : Matrix {
        val result = Matrix(rows, other.cols)

        for(row in 0 until rows){
            for(col in 0 until other.cols ){
                for( scan in 0 until cols ){
                    result[row, col] += this[row, scan] * other[scan, col]
                }
            }
        }

        return result
    }
}
