/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package carnival

import java.util.UUID

import org.apache.commons.lang3._

object Random {

  def uuid32(): String = uuid36().replaceAll("-", "")

  def uuid36(): String = UUID.randomUUID().toString

  def random(count: Int, chars: => String): String = RandomStringUtils.random(count, chars)

  def randomAlphabetic(count: Int): String = RandomStringUtils.randomAlphabetic(count)

  def randomAlphanumeric(count: Int): String = RandomStringUtils.randomAlphanumeric(count)

  def randomAscii(count: Int): String = RandomStringUtils.randomAscii(count)

  def randomGraph(count: Int): String = RandomStringUtils.randomGraph(count)

  def randomNumeric(count: Int): String = RandomStringUtils.randomNumeric(count)

  def randomPrint(count: Int): String = RandomStringUtils.randomPrint(count)

  def randomInt(startInclusive: Int, endExclusive: Int): Int = RandomUtils.nextInt(startInclusive, endExclusive)

  def randomLong(startInclusive: Long, endExclusive: Long): Long = RandomUtils.nextLong(startInclusive, endExclusive)

  def randomFloat(startInclusive: Float, endExclusive: Float): Float = RandomUtils.nextFloat(startInclusive, endExclusive)

  def randomDouble(startInclusive: Double, endExclusive: Double): Double = RandomUtils.nextDouble(startInclusive, endExclusive)

  def randomBoolean(): Boolean = RandomUtils.nextBoolean()

  def randomBytes(count: Int): Array[Byte]= RandomUtils.nextBytes(count)

}
