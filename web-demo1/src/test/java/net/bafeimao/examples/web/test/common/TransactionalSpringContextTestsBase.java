package net.bafeimao.examples.web.test.common;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"classpath:/context-web.xml"})
@Rollback
@Transactional(transactionManager = "txManager")
public abstract class TransactionalSpringContextTestsBase extends AbstractTransactionalJUnit4SpringContextTests {

}
