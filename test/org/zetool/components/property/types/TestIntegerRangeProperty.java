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

import gui.propertysheet.types.IntegerRangeProperty;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import static org.zetool.components.property.SerializationMatchers.notDeserializable;
import static org.zetool.components.property.SerializationMatchers.notSerializable;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestIntegerRangeProperty {

    @Test
    public void testParameter() {
        IntegerRangeProperty irp = new IntegerRangeProperty();
        irp.setMajorTick(10);
        irp.setMinorTick(5);
        irp.setMinValue(1);
        irp.setMaxValue(100);
        
        assertThat(irp.getMajorTick(), is(equalTo(10)));
        assertThat(irp.getMinorTick(), is(equalTo(5)));
        assertThat(irp.getMinValue(), is(equalTo(1)));
        assertThat(irp.getMaxValue(), is(equalTo(100)));
    }
    
    @Test
    public void testEqual() {
        TestGenericProperty.testEqual(new IntegerRangeProperty(), new IntegerRangeProperty());
    }

    @Test
    public void testNotEqual() {
        TestGenericProperty.testUnEqual(new IntegerRangeProperty(), new IntegerRangeProperty());
    }

    @Test
    public void testHashcode() {
        TestGenericProperty.testHashcode(new IntegerRangeProperty(), new IntegerRangeProperty());
    }

    @Test
    public void testSerializationFails() throws IOException {
         IntegerRangeProperty ex = new IntegerRangeProperty();
         assertThat(ex, is(notSerializable()));
    }

    @Test
    public void testDeserializationFails() {
        assertThat(IntegerRangeProperty.class, is(notDeserializable()));
    }
}
