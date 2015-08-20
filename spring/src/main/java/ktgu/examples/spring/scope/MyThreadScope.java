package ktgu.examples.spring.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ktgu on 15/8/19.
 */
public class MyThreadScope implements Scope {
    ThreadLocal<Map<String, Object>> threadScope =
            new NamedThreadLocal<Map<String, Object>>("SimpleThreadScope") {
                @Override
                protected Map<String, Object> initialValue() {
                    return new HashMap<String, Object>();
                }
            };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> beansMap = this.threadScope.get();
        Object bean = beansMap.get(name);
        if (bean == null) {
            bean = objectFactory.getObject();
            beansMap.put(name, bean);
        }
        return bean;
    }

    @Override
    public Object remove(String name) {
        return this.threadScope.get().remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // do nothing
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();
    }
}
