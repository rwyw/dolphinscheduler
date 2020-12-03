/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.server.worker.runner;

import static org.powermock.api.mockito.PowerMockito.mock;

import org.apache.dolphinscheduler.common.utils.CommonUtils;
import org.apache.dolphinscheduler.server.entity.TaskExecutionContext;
import org.apache.dolphinscheduler.server.worker.cache.impl.TaskExecutionContextCacheManagerImpl;
import org.apache.dolphinscheduler.server.worker.processor.TaskCallbackService;
import org.apache.dolphinscheduler.service.bean.SpringApplicationContext;
import org.apache.dolphinscheduler.service.process.ProcessService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TaskExecuteThread.class})
public class TaskExecuteThreadTest {

    TaskExecutionContext taskExecutionContext;

    TaskCallbackService taskCallbackService;

    ApplicationContext applicationContext;

    TaskExecutionContextCacheManagerImpl taskExecutionContextCacheManager;

    private ProcessService processService;

    @Before
    public void init() throws Exception {
        taskExecutionContext = PowerMockito.mock(TaskExecutionContext.class);
        taskCallbackService = PowerMockito.mock(TaskCallbackService.class);
        applicationContext = PowerMockito.mock(ApplicationContext.class);
        SpringApplicationContext springApplicationContext = new SpringApplicationContext();
        springApplicationContext.setApplicationContext(applicationContext);
        taskExecutionContextCacheManager = new TaskExecutionContextCacheManagerImpl();
        Mockito.when(applicationContext.getBean(TaskExecutionContextCacheManagerImpl.class)).thenReturn(taskExecutionContextCacheManager);
    }

    @Test
    public void testTaskClearExecPath() throws Exception {
        processService = mock(ProcessService.class);
        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        SpringApplicationContext springApplicationContext = new SpringApplicationContext();
        springApplicationContext.setApplicationContext(applicationContext);
        Mockito.when(applicationContext.getBean(ProcessService.class)).thenReturn(processService);
        TaskExecutionContext taskExecutionContext = Mockito.mock(TaskExecutionContext.class);
        TaskCallbackService taskCallbackService = Mockito.mock(TaskCallbackService.class);
        TaskExecuteThread taskExecuteThread = PowerMockito.spy(new TaskExecuteThread(taskExecutionContext, taskCallbackService));
        Mockito.when(taskExecutionContext.getExecutePath()).thenReturn("/");


    }


    @Test
    public void testClearTaskExecPath() {

        TaskExecuteThread taskExecuteThread = new TaskExecuteThread(taskExecutionContext, taskCallbackService);
        Mockito.when(CommonUtils.isDevelopMode()).thenReturn(false);
        ;
        taskExecuteThread.clearTaskExecPath();
        Mockito.when(taskExecutionContext.getExecutePath()).thenReturn(null);
        taskExecuteThread.clearTaskExecPath();
        Mockito.when(taskExecutionContext.getExecutePath()).thenReturn("/");
        taskExecuteThread.clearTaskExecPath();
        Mockito.when(taskExecutionContext.getExecutePath()).thenReturn("/data/test-testClearTaskExecPath");
        taskExecuteThread.clearTaskExecPath();

    }

    @Test
    public void testNotClearTaskExecPath() {
        TaskExecuteThread taskExecuteThread = new TaskExecuteThread(taskExecutionContext, taskCallbackService);
        Mockito.when(CommonUtils.isDevelopMode()).thenReturn(true);
        ;
        taskExecuteThread.clearTaskExecPath();
    }
}
