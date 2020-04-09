package com.test.domain;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class ResultsExecutionExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        File log = new File("target/executionLogs/" + testMethod.getName() + "Log.txt");
        StringBuilder sb = new StringBuilder();
        sb.append("Test name ").append(testMethod.getName()).append(" \n");
        if (context.getExecutionException().isPresent()) {
            sb.append("Test failed \n");
            sb.append("==============\n");
            sb.append("Test error: \n" + context.getExecutionException().toString() + "\n");
        } else {
            sb.append("Test succeeded \n");
        }
        FileUtils.writeStringToFile(log, sb.toString(), StandardCharsets.UTF_8, false);
    }
}
