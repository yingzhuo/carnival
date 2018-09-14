/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package carnival.spring

import com.github.yingzhuo.carnival.spring.ProfileUtils

/**
  * @author 应卓
  */
private[spring] class RichStringAsProfile(profile: String) {

  assert(profile != null)

  def isActiveAsProfile: Boolean = ProfileUtils.allActive(profile)

  def ifActiveAsProfile[U](f: String => U): Unit = {
    if (ProfileUtils.allActive(profile)) f.apply(profile)
  }

}
