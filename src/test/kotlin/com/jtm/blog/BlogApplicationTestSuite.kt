package com.jtm.blog

import com.jtm.blog.data.service.*
import com.jtm.blog.entrypoint.controller.DraftControllerUnitTest
import com.jtm.blog.entrypoint.controller.PostControllerUnitTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(value = [
    DraftServiceUnitTest::class,
    DraftServiceIntegrationTest::class,

    PostServiceUnitTest::class,
    PostServiceIntegrationTest::class,

    DraftUpdateServiceUnitTest::class,
    DraftUpdateServiceIntegrationTest::class,

    PostUpdateServiceUnitTest::class,
    PostUpdateServiceIntegrationTest::class,

    DraftControllerUnitTest::class,
    PostControllerUnitTest::class,
])
class BlogApplicationTestSuite