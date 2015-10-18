package ktgu.lab.coconut.web.test.common;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations = { "classpath:/config/spring/context-web.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public abstract class TransactionalSpringContextTestsBase extends AbstractTransactionalJUnit4SpringContextTests {

}
