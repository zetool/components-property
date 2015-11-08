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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.components.property.converter;

import com.thoughtworks.xstream.converters.Converter;
import gui.propertysheet.types.ColorProperty;
import gui.propertysheet.types.GeneralPropertyConverter;
import java.awt.Color;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestDefaultColorPropertyConverter extends AbstractConverterTest<ColorProperty> {
    @Override
    protected Converter getConverter() {
        return new GeneralPropertyConverter<>(() -> new ColorProperty(), "colorNode", ColorProperty.class, Color.class);
    }

    @Override
    public ColorProperty getProperty() {
        return new ColorProperty();
    }

    @Override
    protected Object getPropertyValue() {
        return Color.RED;
    }

    @Override
    protected Class<ColorProperty> getType() {
        return ColorProperty.class;
    }

    @Override
    protected String getExpectedNodeName() {
        return "colorNode";
    }

    @Override
    protected String getPropertyString() {
        return "\n    <red>255</red>\n"
                + "    <green>0</green>\n"
                + "    <blue>0</blue>\n"
                + "    <alpha>255</alpha>\n  ";
    }
}
