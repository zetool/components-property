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
package org.zetool.components.property.converter;

import gui.propertysheet.PropertyTreeNode;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.ColorPropertyConverter;
import java.awt.Color;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestColorPropertyConverter {

    @Test
    public void testMarshalling() {
        Color c = Color.RED;
        ColorProperty cp = new ColorProperty();
        cp.setValue(c);
        cp.setName("color-property");
        
        ColorPropertyConverter conv = new ColorPropertyConverter();
        
        String result = ConverterTestHelper.convertProperty(conv, cp);

        String expected = "<colorNode name=\"\" useAsLocString=\"false\" information=\"\" parameter=\"color-property\">#ff0000</colorNode>";
        assertThat(result, is(equalTo(String.join("\n", expected))));
    }

}
