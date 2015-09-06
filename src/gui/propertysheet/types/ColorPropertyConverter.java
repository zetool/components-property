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
package gui.propertysheet.types;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import org.zetool.common.util.Formatter;
import java.awt.Color;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ColorPropertyConverter extends GeneralPropertyConverter<ColorProperty, Color> {
    public final static String NODE_NAME = "colorNode";
    
    public ColorPropertyConverter() {
        super(() -> new ColorProperty(), NODE_NAME, ColorProperty.class);
    }

    @Override
    public void writeValue(MarshallingContext context) {
        Color c = prop.getValue();
        context.convertAnother(Formatter.colorToHex(c));
    }

    /**
     * Reads a hex string (e.g. #FFFFFF for white) and converts it to the corresponding color object.
     *
     * @param context
     */
    @Override
    public void readValue(UnmarshallingContext context) {
        String bool = (String) context.convertAnother(prop, String.class);
        prop.setValue(Color.decode(bool));
    }
}
