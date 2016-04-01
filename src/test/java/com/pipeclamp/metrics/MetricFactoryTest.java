package com.pipeclamp.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import com.pipeclamp.metrics.functions.Averager;
import com.pipeclamp.metrics.functions.Histogram;
import com.pipeclamp.metrics.functions.MinMax;
import com.pipeclamp.metrics.functions.StdDeviation;
import com.pipeclamp.metrics.functions.Summer;

public class MetricFactoryTest {


	  private static final MetricFactory mf = MetricFactory.Instance;
	  
  @Test
  public void availableFunctionsFor() {
	  
	  MetricFactory mf = MetricFactory.Instance;
	  
	  List<String> expectedNumericFns = Arrays.asList(Summer.Id, Histogram.Id, Averager.Id, MinMax.Id, StdDeviation.Id);

	  Set<String> functions = mf.availableFunctionsFor(Integer.class);
	  assertTrue(expectedNumericFns.containsAll(functions));
	  
	  functions = mf.availableFunctionsFor(Long.class);
	  assertTrue(expectedNumericFns.containsAll(functions));
	  
	  functions = mf.availableFunctionsFor(Float.class);
	  assertTrue(expectedNumericFns.containsAll(functions));
	  
	  functions = mf.availableFunctionsFor(Double.class);
	  assertTrue(expectedNumericFns.containsAll(functions));
	  
	  functions = mf.availableFunctionsFor(BigDecimal.class);
	  assertTrue(expectedNumericFns.containsAll(functions));
  }

  @Test
  public void functionFor() {
    
	  assertEquals(Summer.IntegerSum, mf.functionFor(Summer.Id, Integer.class));
	  assertEquals(Summer.LongSum, mf.functionFor(Summer.Id, Long.class));
	  assertEquals(Summer.FloatSum, mf.functionFor(Summer.Id, Float.class));
	  assertEquals(Summer.DoubleSum, mf.functionFor(Summer.Id, Double.class));
	  assertEquals(Summer.BigDecimalSum, mf.functionFor(Summer.Id, BigDecimal.class));
	  
	  assertEquals(MinMax.Integer, mf.functionFor(MinMax.Id, Integer.class));
	  assertEquals(MinMax.Long, mf.functionFor(MinMax.Id, Long.class));
	  assertEquals(MinMax.Float, mf.functionFor(MinMax.Id, Float.class));
	  assertEquals(MinMax.Double, mf.functionFor(MinMax.Id, Double.class));
	  assertEquals(MinMax.BigDecimal, mf.functionFor(MinMax.Id, BigDecimal.class));
  }

  @Test
  public void showOnTest() {

	  File outFile = new File("showOnTest.txt");

	  try {
		  PrintStream out = new PrintStream(outFile);
		  mf.showOn(out);
		  out.close();

		  String contents = new String(Files.readAllBytes(Paths.get("showOnTest.txt")));

		  assertTrue(contents.contains(Summer.Id));
		  assertTrue(contents.contains(Averager.Id));
		  assertTrue(contents.contains(MinMax.Id));
		  assertTrue(contents.contains(Histogram.Id));

	  } catch (Exception ex) {
		  fail(ex.getMessage());
	  } finally {
		  outFile.deleteOnExit();
	  }
  }
}
