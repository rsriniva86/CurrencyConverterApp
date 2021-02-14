package com.shyam.currencyconverter.core

import java.io.*
import java.util.*

internal object Result {
    /*
     * Complete the 'ComputeDivision' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY inputArray
     *  2. INTEGER divisor
     */
    fun ComputeDivision(inputArray: Array<Int>, divisor: Int): Array<Int> {
        val output: Array<Int> = arrayOf<Int>(inputArray.size)
        if (divisor == 0) {
            return arrayOf()
        }
        var index = 0;
        for (value in inputArray) {
            var dividedValue = value / divisor
            val remainder =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Math.floorMod(value, divisor)
                } else {
                    value % divisor;
                }
            val isdivisorEven = divisor % 2 == 0
            if (isdivisorEven) {
                if (remainder > divisor / 2) {
                    dividedValue++

                }
            } else {
                if (remainder >= (divisor + 1) / 2) {
                    dividedValue++
                }
            }
            output[index] = dividedValue
            index++
        }
        return output
    }
}

object InputArray {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
        val bufferedWriter = BufferedWriter(FileWriter(System.getenv("OUTPUT_PATH")))
        val inputArrayCount = bufferedReader.readLine().trim { it <= ' ' }.toInt()
        val inputArray: MutableList<Int> = ArrayList()
        for (i in 0 until inputArrayCount) {
            val inputArrayItem = bufferedReader.readLine().trim { it <= ' ' }.toInt()
            inputArray.add(inputArrayItem)
        }
        val divisor = bufferedReader.readLine().trim { it <= ' ' }.toInt()
        val inputArrayOfInts: Array<Int> = inputArray.toTypedArray()
        val result = Result.ComputeDivision(
            inputArrayOfInts, divisor
        )
        for (i in result.indices) {
            bufferedWriter.write(result[i].toString())
            if (i != result.size - 1) {
                bufferedWriter.write("\n")
            }
        }
        bufferedWriter.newLine()
        bufferedReader.close()
        bufferedWriter.close()
    }
}