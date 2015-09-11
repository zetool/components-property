package org.zetool.components.property.types;

import gui.propertysheet.types.StringListProperty;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestStringListProperty {
    private StringListProperty stringListProperty;
    private final int COUNT = 3;
    private Function<Integer, String> elementGenerator = i -> "element" + i;
    
    @Before
    public void setUp() {
        stringListProperty = new StringListProperty();
    }
    
    @Test
    public void testList() {
        stringListProperty.setValue(populate(new LinkedList<>()));
        
        List<String> values = stringListProperty.getValue();
        assertThat(values.size(), is(equalTo(COUNT)));
        for( int i = 1; i <= COUNT; ++i ) {
            assertThat(values.get(i-1), is(equalTo(elementGenerator.apply(i))));
        }
    }
    
    @Test
    public void testCollection() {
        stringListProperty.setValue(populate(new HashSet<>()));
        
        List<String> values = stringListProperty.getValue();
        assertThat(values.size(), is(equalTo(COUNT)));
        for(int i = 1; i <= COUNT; ++i) {
            assertThat(values.contains(elementGenerator.apply(i)), is(true));
        }
    }
    
     private <T extends Collection<String>> T populate(T c) {
        for( int i = 1; i <= COUNT; ++i ) {
            c.add(elementGenerator.apply(i));
        }
        return c;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFailForWrongType() {
        stringListProperty.setValue("fails");
    }
}
