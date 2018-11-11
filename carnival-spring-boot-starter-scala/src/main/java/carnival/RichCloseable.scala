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

import scala.language.reflectiveCalls
import scala.util.Try

/**
  * @author 应卓
  */
private[carnival] class RichCloseable[C <: {def close() : Unit}](c: C) {

  require(c != null)

  def closeQuietly(): Unit = Try(c.close())

}
