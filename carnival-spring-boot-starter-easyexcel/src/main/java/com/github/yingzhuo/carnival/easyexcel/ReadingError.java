/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel;

import lombok.*;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.9.2
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class ReadingError implements Serializable {

    private String filename;
    private int sheetNumber;
    private String sheetName;
    private int rowNumber;
    private int colNumber;
    private String exceptionMessage;
    private String exceptionType;

}
