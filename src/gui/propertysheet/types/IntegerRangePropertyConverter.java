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
package gui.propertysheet.types;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class IntegerRangePropertyConverter extends GeneralPropertyConverter<IntegerRangeProperty, Integer> {
    public static final String NODE_NAME = "intRangeNode";

    public IntegerRangePropertyConverter() {
        super(() -> new IntegerRangeProperty(), NODE_NAME, IntegerRangeProperty.class, Integer.class);
    }

    @Override
    public void readAttributes(HierarchicalStreamReader reader) {
        super.readAttributes(reader);
        prop.setMinValue(Integer.parseInt(reader.getAttribute("minValue")));
        prop.setMaxValue(Integer.parseInt(reader.getAttribute("maxValue")));
        prop.setMinorTick(Integer.parseInt(reader.getAttribute("minorTick")));
        prop.setMajorTick(Integer.parseInt(reader.getAttribute("majorTick")));
    }

    @Override
    public void writeAttributes(HierarchicalStreamWriter writer) {
        super.writeAttributes(writer);
        writer.addAttribute("minValue", Integer.toString(prop.getMinValue()));
        writer.addAttribute("maxValue", Integer.toString(prop.getMaxValue()));
        writer.addAttribute("minorTick", Integer.toString(prop.getMinorTick()));
        writer.addAttribute("majorTick", Integer.toString(prop.getMajorTick()));
    }
}
