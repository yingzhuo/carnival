/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.props;

import com.github.yingzhuo.carnival.nsq.node.NsqdNode;
import com.github.yingzhuo.carnival.nsq.node.NsqlookupdNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.nsq")
public class NsqProps {

    private boolean enabled = true;
    private List<NsqdNode> nsqdNodes = new ArrayList<>();
    private List<NsqlookupdNode> nsqlookupdNodes = new ArrayList<>();

}
