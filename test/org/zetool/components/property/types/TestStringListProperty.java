/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.zetool.components.property.types;

import gui.propertysheet.types.StringListProperty;
import java.io.IOException;
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
import static org.zetool.components.property.SerializationMatchers.notDeserializable;
import static org.zetool.components.property.SerializationMatchers.notSerializable;

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
    
    @Test
    public void testIterator() {
        stringListProperty.setValue(populate(new LinkedList<>()));
        Collection<String> containedStrings = new HashSet<>();
        for( String string : stringListProperty) {
            containedStrings.add(string);
        }
        assertThat(containedStrings.size(), is(equalTo(count)));
        for(int i = 1; i <= count; ++i){
            assertThat(containedStrings.contains(elementGenerator.apply(i)), is(true));
        }   
    }

    @Test
    public void testSerializationFails() throws IOException {
         StringListProperty ex = new StringListProperty();
         assertThat(ex, is(notSerializable()));
    }
    
    @Test
    public void testDeserializationFails() {
        assertThat(StringListProperty.class, is(notDeserializable()));
    }
}
