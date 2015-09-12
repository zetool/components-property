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
    private final int count = 5;
    private final Function<Integer, String> elementGenerator = i -> "element" + i;
    
    @Before
    public void setUp() {
        stringListProperty = new StringListProperty();
    }
    
    @Test
    public void testList() {
        stringListProperty.setValue(populate(new LinkedList<>()));
        
        List<String> values = stringListProperty.getValue();
        assertThat(values.size(), is(equalTo(count)));
        for( int i = 1; i <= count; ++i ) {
            assertThat(values.get(i-1), is(equalTo(elementGenerator.apply(i))));
        }
    }
    
    @Test
    public void testCollection() {
        stringListProperty.setValue(populate(new HashSet<>()));
        
        List<String> values = stringListProperty.getValue();
        assertThat(values.size(), is(equalTo(count)));
        for(int i = 1; i <= count; ++i) {
            assertThat(values.contains(elementGenerator.apply(i)), is(true));
        }
    }
    
     private <T extends Collection<String>> T populate(T c) {
        for( int i = 1; i <= count; ++i ) {
            c.add(elementGenerator.apply(i));
        }
        return c;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFailForWrongType() {
        stringListProperty.setValue("fails");
    }
    
    @Test
    public void testEmptyInitialized() {
        assertThat(stringListProperty.size(), is(equalTo(0)));
    }
    
    @Test
    public void testContains() {
        stringListProperty.add("test value");
        assertThat(stringListProperty.size(), is(equalTo(1)));
        assertThat(stringListProperty.contains("test value"), is(true));
        assertThat(stringListProperty.contains("not contained"), is(false));
    }
    
    @Test
    public void testRemove() {
        stringListProperty.add("test value");
        stringListProperty.add("another value");
        stringListProperty.remove("test value");
        assertThat(stringListProperty.size(), is(equalTo(1)));
        assertThat(stringListProperty.contains("test value"), is(false));
        assertThat(stringListProperty.contains("another value"), is(true));
    }
    
    @Test
    public void testAddMultiple() {
        Collection<String> c = new HashSet<>(count);
        populate(c);
        stringListProperty.addAll(c);
        assertThat(stringListProperty.size(), is(equalTo(count)));
        for(int i = 1; i <= count; ++i){
            assertThat(stringListProperty.contains(elementGenerator.apply(i)), is(true));
        }
        
    }
  
    @Test
    public void removeMultiple() {
        Collection<String> c = new HashSet<>(count);
        populate(c);
        stringListProperty.addAll(c);
        c.remove(elementGenerator.apply(1));
        stringListProperty.removeAll(c);
        assertThat(stringListProperty.size(), is(equalTo(1)));
        assertThat(stringListProperty.contains(elementGenerator.apply(1)), is(true));
        assertThat(stringListProperty.contains(elementGenerator.apply(2)), is(false));
        assertThat(stringListProperty.contains(elementGenerator.apply(3)), is(false));
    }
            
}
