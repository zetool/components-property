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
package org.zetool.components.property;

import gui.propertysheet.types.BooleanProperty;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.DoubleProperty;
import gui.propertysheet.types.EnumProperty;
import gui.propertysheet.types.IntegerProperty;
import gui.propertysheet.types.IntegerRangeProperty;
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestConverterLibrary {
    @Test
    public void testDefaultLibrary() {
        PropertyConverterLibrary library = PropertyConverterLibrary.createDefaultConverters();
        assertThat(library.canConvert(BooleanProperty.class), is(true));
        assertThat(library.canConvert(IntegerProperty.class), is(true));
        assertThat(library.canConvert(DoubleProperty.class), is(true));
        assertThat(library.canConvert(StringProperty.class), is(true));
        assertThat(library.canConvert(ColorProperty.class), is(true));
        assertThat(library.canConvert(EnumProperty.class), is(true));
        assertThat(library.canConvert(IntegerRangeProperty.class), is(true));
        assertThat(library.canConvert(StringListProperty.class), is(true));
    }
    
    @Test
    public void testEmptyLibrary() {
        PropertyConverterLibrary library = new PropertyConverterLibrary();
        assertThat(library.canConvert(BooleanProperty.class), is(false));
        assertThat(library.canConvert(IntegerProperty.class), is(false));
        assertThat(library.canConvert(DoubleProperty.class), is(false));
        assertThat(library.canConvert(StringProperty.class), is(false));
        assertThat(library.canConvert(ColorProperty.class), is(false));
        assertThat(library.canConvert(EnumProperty.class), is(false));
        assertThat(library.canConvert(IntegerRangeProperty.class), is(false));
        assertThat(library.canConvert(StringListProperty.class), is(false));
    }
}
