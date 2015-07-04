package org.lftechnology.outlier.instantreloader.utils;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 * @author anish
 *
 */
@RunWith(Parameterized.class)
public class NumberUtilsPTest {

  private String value;
  private int expected;

  public NumberUtilsPTest(String value, int expected) {
    this.value = value;
    this.expected = expected;
  }

  // creates the test data
  @Parameters
  public static Collection<Object[]> data() {
    // given
    Object[][] data = new Object[][] { {"1", 1}, {"20   -", -20}, 
                                       {"-  1", -1}, {"2 -", -2},
                                       {"   2-", -2}, {"2 -", -2},
                                       {"0", 0}
                                      };
    return Arrays.asList(data);
  }

  @Test
  public void testConvertStringToInteger() throws Exception {
    // when
    int result = NumberUtils.convertStringToInteger(value);

    // then
    Assert.assertEquals(expected, result);
  }
}
