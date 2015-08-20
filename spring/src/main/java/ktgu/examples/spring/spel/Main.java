package ktgu.examples.spring.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Created by ktgu on 15/8/19.
 */
public class Main
{
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

// invokes 'getBytes()'
        Expression exp = parser.parseExpression("''Hello World''.bytes");
        byte[] bytes = (byte[]) exp.getValue();
    }
}
