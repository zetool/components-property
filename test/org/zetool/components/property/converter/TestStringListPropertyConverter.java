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
import gui.propertysheet.types.StringListProperty;
import gui.propertysheet.types.StringListPropertyConverter;
import java.util.ArrayList;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestStringListPropertyConverter extends AbstractConverterTest<StringListProperty> {
    
    @Override
    protected Converter getConverter() {
        return new StringListPropertyConverter();
    }

    @Override
    public StringListProperty getProperty() {
        return new StringListProperty();
    }

    @Override
    protected Object getPropertyValue() {
        ArrayList<String> list = new ArrayList<>(2);
        list.add("abc");
        list.add("xyz");
        return list;
    }

    @Override
    protected Class<StringListProperty> getType() {
        return StringListProperty.class;
    }

    @Override
    protected String getExpectedNodeName() {
        return "stringListNode";
    }

    @Override
    protected String getPropertyString() {
        return "\n    <string>abc</string>\n"
                + "    <string>xyz</string>\n  ";
    }

}
