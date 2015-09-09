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

/**
 *
 * @author Jan-Philipp Kappmeier
 */
@SuppressWarnings("serial")
public class IntegerRangeProperty extends IntegerProperty {
    private int minValue;
    private int maxValue;
    private int minorTick;
    private int majorTick;
    
    public int getMajorTick() {
        return majorTick;
    }

    public void setMajorTick( int majorTick ) {
        this.majorTick = majorTick;
    }

    public int getMinorTick() {
        return minorTick;
    }

    public void setMinorTick( int minorTick ) {
        this.minorTick = minorTick;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue( int maxValue ) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue( int minValue ) {
        this.minValue = minValue;
    }
}
