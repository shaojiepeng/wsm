package com.wsm.common.dialect;

import com.wsm.common.dialect.Processor.PermissionEncryptProcessor;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义标签 wsm:permission
 */
@Component
public class PermissionDialect extends AbstractDialect {

    private static final String PREFIX = "wsm";
    private static final String ELEMENT_NAME = "permission";

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new PermissionEncryptProcessor());
        return processors;
    }
}
