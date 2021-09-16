/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see ConditionalOnResource
 * @since 1.10.20
 */
class ConditionalOnResourceCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ConditionalOnResource.class.getName()));
        final String[] locations = attributes.getStringArray("value");

        if (locations.length == 0) {
            return false;
        }

        final ResourceLoader loader = context.getResourceLoader();
        final ConditionalOnResource.Existence existence = attributes.getEnum("existence");
        final ConditionalOnResource.Type type = attributes.getEnum("type");

        boolean c1 = false;
        switch (existence) {
            case ANY:
                c1 = convert(loader, locations).anyMatch(Resource::exists);
                break;
            case ALL:
                c1 = convert(loader, locations).allMatch(Resource::exists);
                break;
            case NONE:
                c1 = convert(loader, locations).noneMatch(Resource::exists);
                break;
        }

        boolean c2 = false;
        switch (type) {
            case ANY:
                c2 = true;
                break;
            case FILE:
                c2 = convert(loader, locations).allMatch(this::isFile);
                break;
            case DIRECTORY:
                c2 = convert(loader, locations).allMatch(this::isDirectory);
                break;
        }

        return c1 && c2;
    }

    private Stream<Resource> convert(ResourceLoader resourceLoader, String[] locations) {
        return Stream.of(locations).map(resourceLoader::getResource);
    }

    private boolean isFile(Resource resource) {
        try {
            return resource.getFile().isFile();
        } catch (IOException e) {
            return false;
        }
    }

    private boolean isDirectory(Resource resource) {
        try {
            return resource.getFile().isDirectory();
        } catch (IOException e) {
            return false;
        }
    }
}
