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

import com.thoughtworks.xstream.converters.Converter;
import gui.propertysheet.types.GeneralPropertyConverter;
import gui.propertysheet.types.IntegerProperty;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestIntegerPropertyConverter extends AbstractConverterTest<IntegerProperty> {
    @Override
    protected Converter getConverter() {
        return new GeneralPropertyConverter<>(() -> new IntegerProperty(), "intNode", IntegerProperty.class, Integer.class);
    }

    @Override
    public IntegerProperty getProperty() {
        return new IntegerProperty();
    }

    @Override
    protected Object getPropertyValue() {
        return 12778;
    }

    @Override
    protected Class<IntegerProperty> getType() {
        return IntegerProperty.class;
    }

    @Override
    protected String getExpectedNodeName() {
        return "intNode";
    }

    @Override
    protected String getPropertyString() {
        return "12778";
    }

}
