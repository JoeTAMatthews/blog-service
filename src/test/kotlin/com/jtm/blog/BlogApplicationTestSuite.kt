package com.jtm.blog

import com.jtm.blog.data.service.DraftServiceTest
import com.jtm.blog.data.service.PostServiceTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(value = [
    DraftServiceTest::class,
    PostServiceTest::class,
])
class BlogApplicationTestSuite