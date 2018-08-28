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

import scala.annotation.tailrec

/**
  * @author 应卓
  */
private[carnival] class RichThrowable[T <: Throwable](t: T) {

  assert(t != null)

  def getRoot: Throwable = {

    // 内部函数 (尾递归)
    @tailrec def doGetRoot(t: Throwable): Throwable = Option(t.getCause) match {
      case None => t
      case Some(x) => doGetRoot(x)
    }

    doGetRoot(t)
  }

}
