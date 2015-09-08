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
import gui.propertysheet.types.EnumConverter;
import gui.propertysheet.types.EnumProperty;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestEnumPropertyConverter extends AbstractConverterTest<EnumProperty> {
    public enum TestEnum {
        Value1,
        Value2;
    }
    
    @Override
    protected Converter getConverter() {
        return new EnumConverter();
    }

    @Override
    public EnumProperty getProperty() {
        return new EnumProperty();
    }

    @Override
    protected Object getPropertyValue() {
        return TestEnum.Value1;
    }

    @Override
    protected Class<EnumProperty> getType() {
        return EnumProperty.class;
    }

    @Override
    protected String getExpectedNodeName() {
        return "enumNode";
    }

    @Override
    protected String getPropertyString() {
        return "org.zetool.components.property.converter.TestEnumPropertyConverter$TestEnum#Value1";
    }

}
