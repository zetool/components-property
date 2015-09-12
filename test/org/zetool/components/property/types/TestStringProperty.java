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

import gui.propertysheet.GenericProperty;
import gui.propertysheet.types.StringProperty;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import static org.zetool.components.property.SerializationMatchers.notDeserializable;
import static org.zetool.components.property.SerializationMatchers.notSerializable;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestStringProperty {
    
    @Test
    public void testEqual() {
        TestGenericProperty.testEqual(new StringProperty(), new StringProperty());
    }
    
    @Test
    public void testNotEqual() {
        TestGenericProperty.testUnEqual(new StringProperty(), new StringProperty());
    }
    
    @Test
    public void testNotEqualSuper() {
        TestGenericProperty.testNotEqual(new GenericProperty(), new StringProperty());
    }
    
    @Test
    public void testSerializationFails() throws IOException {
         StringProperty ex = new StringProperty();
         assertThat(ex, is(notSerializable()));
    }
    
    @Test
    public void testDeserializationFails() {
        assertThat(StringProperty.class, is(notDeserializable()));
    }}
