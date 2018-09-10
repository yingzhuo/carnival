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

import org.apache.commons.lang3.EnumUtils

import scala.collection.JavaConverters._

/**
  * @author 应卓
  */
private[carnival] class RichEnumClass[E <: Enum[E]](enumType: Class[E]) {

  assert(enumType != null)

  def getEnumConstants: Iterable[E] = enumType.getEnumConstants

  def getEnumConstantsAsList: List[E] = enumType.getEnumConstants.toList

  def getEnumConstantsAsStream: Stream[E] = enumType.getEnumConstants.toStream

  def getEnumConstantsAsMap: Map[String, E] = EnumUtils.getEnumMap(enumType).asScala.toMap

  def isValid(value: => String): Boolean = EnumUtils.isValidEnum(enumType, value)

}
