/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.refuse;

import com.github.yingzhuo.carnival.restful.security.exception.AccessRefusedException;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 * @since 1.1.6
 */
public class NopRefuseManager implements RefuseManager {

    @Override
    public void apply(NativeWebRequest webRequest) throws AccessRefusedException {
        // nop
    }

}
