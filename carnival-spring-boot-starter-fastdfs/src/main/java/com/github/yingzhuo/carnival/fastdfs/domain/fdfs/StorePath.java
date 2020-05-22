/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.fdfs;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.OtherConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.Column;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.DynamicFieldType;
import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSUnsupportedStorePathException;
import lombok.*;

/**
 * 存储文件的路径信息
 *
 * @author tobato
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StorePath {

    /**
     * 解析路径
     */
    private static final String SPLIT_GROUP_NAME_AND_FILENAME_SEPARATOR = "/";

    /**
     * group
     */
    private static final String SPLIT_GROUP_NAME = "group";

    @Column(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String group;

    @Column(index = 1, dynamicField = DynamicFieldType.ALL_REST_BYTE)
    private String path;

    /**
     * 从Url当中解析存储路径对象
     *
     * @param filePath 有效的路径样式为(group/path) 或者
     *                 (http://ip/group/path),路径地址必须包含group
     */
    public static StorePath parseFromUrl(String filePath) {
        String group = getGroupName(filePath);

        // 获取group起始位置
        int pathStartPos = filePath.indexOf(group) + group.length() + 1;
        String path = filePath.substring(pathStartPos);
        return new StorePath(group, path);
    }

    /**
     * 获取Group名称
     */
    private static String getGroupName(String filePath) {
        //先分隔开路径
        String[] paths = filePath.split(SPLIT_GROUP_NAME_AND_FILENAME_SEPARATOR);
        if (paths.length == 1) {
            throw new FastDFSUnsupportedStorePathException("解析文件路径错误,有效的路径样式为(group/path) 而当前解析路径为".concat(filePath));
        }
        for (String item : paths) {
            if (item.contains(SPLIT_GROUP_NAME)) {
                return item;
            }
        }

        throw new FastDFSUnsupportedStorePathException("解析文件路径错误,被解析路径url没有group,当前解析路径为".concat(filePath));
    }

    /**
     * 获取文件全路径
     */
    public String getFullPath() {
        return this.group.concat(SPLIT_GROUP_NAME_AND_FILENAME_SEPARATOR).concat(this.path);
    }

}
