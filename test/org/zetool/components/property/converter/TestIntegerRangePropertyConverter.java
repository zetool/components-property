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
import gui.propertysheet.types.IntegerRangeProperty;
import gui.propertysheet.types.IntegerRangePropertyConverter;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestIntegerRangePropertyConverter extends AbstractConverterTest<IntegerRangeProperty> {
    private final int MIN_VALUE = 5;
    private final int MAX_VALUE = 45;
    private final int MINOR_TICK = 1;
    private final int MAJOR_TICK = 5;
            
    @Override
    protected Converter getConverter() {
        return new IntegerRangePropertyConverter();
    }

    @Override
    public IntegerRangeProperty getProperty() {
        IntegerRangeProperty irp = new IntegerRangeProperty();
        irp.setMinValue(MIN_VALUE);
        irp.setMaxValue(MAX_VALUE);
        irp.setMinorTick(MINOR_TICK);
        irp.setMajorTick(MAJOR_TICK);
        return irp;
    }

    @Override
    protected Object getPropertyValue() {
        return 20;
    }

    @Override
    protected Class<IntegerRangeProperty> getType() {
        return IntegerRangeProperty.class;
    }

    @Override
    protected String getExpectedNodeName() {
        return "intRangeNode";
    }

    @Override
    protected String getPropertyString() {
        return "20";
    }

    @Override
    protected Stream<String> getAdditionalParameters() {
        String[] parameters = {
            String.format("minValue=\"%d\"", MIN_VALUE),
            String.format("maxValue=\"%d\"", MAX_VALUE),
            String.format("minorTick=\"%d\"", MINOR_TICK),
            String.format("majorTick=\"%d\"", MAJOR_TICK)
        };
        return Arrays.stream(parameters);
    }
}
