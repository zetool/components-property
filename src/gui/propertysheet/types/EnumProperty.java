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

import gui.propertysheet.BasicProperty;
import javax.swing.JPanel;

/**
 * A property that allows to switch the quality of visualization. The quality
 * is stored in an enumeration {@link QualityPreset}. There, several presets are
 * defined. The property allows to store one of the presets and provides a
 * {@link JPanel} containing a combo box to select the quality.
 * @author Jan-Philipp Kappmeier
 */
public class EnumProperty extends BasicProperty<Enum> {

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        setType(value.getClass());
    }
}
