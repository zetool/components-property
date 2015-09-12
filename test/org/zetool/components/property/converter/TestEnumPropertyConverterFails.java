package org.zetool.components.property.converter;

import com.thoughtworks.xstream.converters.ConversionException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestEnumPropertyConverterFails extends TestEnumPropertyConverter {
    @Override
    public void assertProperty() {
        // pass, cannot marshal with nonexistent enum type
    }
    
    @Override
    public void testUnmarshal() {
        try {
            super.testUnmarshal();
        } catch (ConversionException ex ) {
            String e = ex.getMessage();
            assertThat(e.contains(getPropertyString()), is(true));
            return;
        }
        throw new AssertionError("Unmarshal should fail with ConversionException");
    }
    
    @Override
    protected String getPropertyString() {
        return "org.zetool.components.property.converter.TestEnumPropertyConverter$FakeEnum#Value";
    }
    
}
