package com.jtm.blog

import com.jtm.blog.data.service.DraftServiceTest
import com.jtm.blog.data.service.DraftUpdateServiceTest
import com.jtm.blog.data.service.PostServiceTest
import com.jtm.blog.data.service.PostUpdateServiceTest
import com.jtm.blog.entrypoint.controller.DraftControllerTest
import com.jtm.blog.entrypoint.controller.PostControllerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(value = [
    DraftServiceTest::class,
    PostServiceTest::class,

    DraftUpdateServiceTest::class,
    PostUpdateServiceTest::class,

    DraftControllerTest::class,
    PostControllerTest::class,
])
class BlogApplicationTestSuite