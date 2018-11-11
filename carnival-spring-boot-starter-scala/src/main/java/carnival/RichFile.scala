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

import java.io.{File, InputStream, OutputStream}
import java.net.URL

import org.apache.commons.io.FileUtils

/**
  * @author 应卓
  */
private[carnival] class RichFile(file: File) {

  require(file != null)

  def openInputStream(): InputStream = FileUtils.openInputStream(file)

  def openOutputStream(append: Boolean = true): OutputStream = FileUtils.openOutputStream(file, append)

  def touch(): File = {
    FileUtils.touch(file)
    file
  }

  def contentEquals(that: File): Boolean = FileUtils.contentEquals(file, that)

  def toUrl: URL = FileUtils.toURLs(Array(file))(0)

  def delete(): Unit = FileUtils.deleteQuietly(file)

}
