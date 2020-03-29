package org.zetool.common.datastructure;

import ds.PropertyContainer;
import java.awt.Color;
import java.util.Arrays;
import java.util.function.Function;
import javax.swing.UIManager;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestPropertyContainer {
    PropertyContainer propertyContainer;
    
    @Before
    public void initPropertyContainer() {
        propertyContainer = new PropertyContainer();
    }
    
    @Test
    public void testPropertyContainer() {
        assertThat(propertyContainer.size(), is(equalTo(0)));
        propertyContainer.define("some parameter", Boolean.class, false);
        assertThat(propertyContainer.size(), is(equalTo(1)));
        propertyContainer.define("some other parameter", Boolean.class, true);
        assertThat(propertyContainer.size(), is(equalTo(2)));
        assertThat(propertyContainer.getAs("some parameter", Boolean.class), is(equalTo(false)));
        assertThat(propertyContainer.getAs("some other parameter", Boolean.class), is(equalTo(true)));
        assertThat(propertyContainer, contains("some parameter", "some other parameter"));
    }
    
    @Test
    public void testContainment() {
        assertThat(propertyContainer.isDefined("test parameter"), is(false));
        propertyContainer.define("test parameter", Integer.class, 2);
        assertThat(propertyContainer.isDefined("test parameter"), is(true));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDoubleDefineFails() {
        propertyContainer.define("some parameter", Integer.class, 1);
        propertyContainer.define("some parameter", Integer.class, 2);
    }
    
    @Test
    public void typeStoredCorrect() {
        propertyContainer.define("double parameter", Double.class, 1.0);
        propertyContainer.define("float parameter", Float.class, 1.0f);
        assertThat(propertyContainer.getType("double parameter"), is(equalTo(Double.class)));
        assertThat(propertyContainer.getType("float parameter"), is(equalTo(Float.class)));
    }
    
    @Test
    public void testSetting() {
        propertyContainer.define("param", Integer.class, -1);
        propertyContainer.set("param", 1);
        assertThat(propertyContainer.getAs("param", Integer.class), is(equalTo(1)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSettingFailsForUndefined() {
        propertyContainer.set("not defined", 2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSettingFailsWrongType() {
        propertyContainer.define("intparam", Integer.class, 1);
        try {
            propertyContainer.set("intparam", 3.4);
        } catch (IllegalArgumentException ex ) {
            assertThat(ex.getMessage(), containsString("java.lang.Integer"));
            throw ex;
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAsNotContained() {
        propertyContainer.getAs("my key", Integer.class);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAsBadType() {
        propertyContainer.define("intparam", Integer.class, 0);
        try {
            propertyContainer.getAs("intparam", Double.class);
        } catch (IllegalArgumentException ex ) {
            assertThat(ex.getMessage(), containsString("java.lang.Integer"));
            assertThat(ex.getMessage(), containsString("java.lang.Double"));
            throw ex;
        }
    }
    
    @Test
    public void testGetAsSupertype() {
        propertyContainer.define("intparam", Integer.class, 2);
        Number n = propertyContainer.getAs("intparam", Number.class);
        assertThat(n, is(equalTo(2)));
    }
    
    @Test
    public void testSimpleGet() {
        propertyContainer.define("some param", Integer.class, 1);
        
    }
    
    @Test
    public void testGetAsSpecific() {
        assertSpecificProperty(propertyContainer::get, this);
        assertSpecificProperty(propertyContainer::getAsBoolean, true);
        assertSpecificProperty(propertyContainer::getAsColor, Color.DARK_GRAY);
        assertSpecificProperty(propertyContainer::getAsInt, 1);
        assertSpecificProperty(propertyContainer::getAsDouble, Math.PI);
        assertSpecificProperty(propertyContainer::getAsFont, UIManager.getFont("TextField.font"));
        assertSpecificProperty(propertyContainer::getAsString, "some string");
        assertSpecificProperty(propertyContainer::getAsStringList, Arrays.asList(new String[] {"item 1", "item 2"}));
    }
    
    private <T> void assertSpecificProperty(Function<String,T> specificAccessor, T value) {
        propertyContainer.define(value.getClass().toString(), (Class<T>)value.getClass(), value);
        assertThat(specificAccessor.apply(value.getClass().toString()), is(equalTo(value)));
        try {
            specificAccessor.apply("not contained");
        } catch (IllegalArgumentException ex) {
            return;
        }
        throw new AssertionError();
    }
    
    @Test
    public void testSpecialOperators() {
        propertyContainer.define("boolean", Boolean.class, true);
        assertThat(propertyContainer.getAsBoolean("boolean"), is(true));
        boolean newValue = propertyContainer.toggle("boolean");
        assertThat(newValue, is(false));
        assertThat(propertyContainer.getAsBoolean("boolean"), is(false));
    }
    
    @Test
    public void testGlobalPropertyContainer() {
        PropertyContainer pc1 = PropertyContainer.getGlobal();
        PropertyContainer pc2 = PropertyContainer.getGlobal();
        assertThat(pc1, is(notNullValue()));
        assertThat(pc1, is(CoreMatchers.sameInstance(pc2)));
    }
}
