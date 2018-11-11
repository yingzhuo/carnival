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

import scala.util.{Failure, Success, Try}

/**
  * @author 应卓
  */
private[carnival] class RichTry[T](t: Try[T]) {

  require(t != null)

  def ifSuccess[U](f: T => U): Unit = t match {
    case Failure(_) =>
    case Success(x) => f(x)
  }

  def ifFailure[U](f: Throwable => U): Unit = t match {
    case Success(_) =>
    case Failure(e) => f(e)
  }

}
