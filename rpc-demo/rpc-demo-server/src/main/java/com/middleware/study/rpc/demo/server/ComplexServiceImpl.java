package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.demo.api.ClassB;
import com.middleware.study.rpc.demo.api.ComplexService;

/**
 * @author wuhaitao
 * @date 2016/5/28 0:02
 */
public class ComplexServiceImpl implements ComplexService {
    @Override
    public ClassB test(ClassB classB) {
        return classB;
    }
}
