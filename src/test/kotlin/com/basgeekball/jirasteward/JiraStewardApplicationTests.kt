package com.basgeekball.jirasteward

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertNotNull

@SpringBootTest(classes = [JiraStewardApplication::class])
class JiraStewardApplicationTests {

	@Test
	fun contextLoads(context: ApplicationContext) {
		assertNotNull(context)
	}

}
