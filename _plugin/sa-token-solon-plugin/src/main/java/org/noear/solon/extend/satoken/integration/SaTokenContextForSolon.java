package org.noear.solon.extend.satoken.integration;

import cn.dev33.satoken.context.SaTokenContext;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.context.model.SaStorage;
import org.noear.solon.core.util.PathAnalyzer;
import org.noear.solon.extend.satoken.model.SaRequestForSolon;
import org.noear.solon.extend.satoken.model.SaResponseForSolon;
import org.noear.solon.extend.satoken.model.SaStorageForSolon;

/**
 * @author noear
 * @since 1.4
 */
public class SaTokenContextForSolon implements SaTokenContext {
    /**
     * 获取当前请求的Request对象
     */
    @Override
    public SaRequest getRequest() {
        return new SaRequestForSolon();
    }

    /**
     * 获取当前请求的Response对象
     */
    @Override
    public SaResponse getResponse() {
        return new SaResponseForSolon();
    }

    /**
     * 获取当前请求的 [存储器] 对象
     */
    @Override
    public SaStorage getStorage() {
        return new SaStorageForSolon();
    }

    /**
     * 校验指定路由匹配符是否可以匹配成功指定路径
     */
    @Override
    public boolean matchPath(String pattern, String path) {
        return  PathAnalyzer.get(path).matches(path);
    }
}
