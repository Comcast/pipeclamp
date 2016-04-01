package com.pipeclamp.metrics.aggregator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.avro.generic.GenericRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.api.Aggregator;
import com.pipeclamp.classifiers.Numbers;
import com.pipeclamp.metrics.functions.Averager;
import com.pipeclamp.metrics.functions.Counter;
import com.pipeclamp.metrics.functions.GroupingHistogram;
import com.pipeclamp.metrics.functions.MinMax;
import com.pipeclamp.metrics.functions.Summer;
import com.pipeclamp.path.SimpleAvroPath;
import com.pipeclamp.predicates.Nulls;
import com.pipeclamp.test.Person;

/**
 *
 * @author Brian Remedios
 */
public class AggregatorTest {

	private Aggregator<GenericRecord> agg;

	@BeforeSuite
	private void setup() {
		agg = new BasicAggregator<GenericRecord>();
		agg.register(new SimpleAvroPath<Integer>("bellybuttons"), Summer.IntegerSum, "bellybuttonCount", null);
		agg.register(new SimpleAvroPath<Integer>("weight"),	   	  Averager.FloatAvg, "avg weight", 		"kg");
		agg.register(new SimpleAvroPath<Integer>("bellybuttons"), MinMax.Integer, "min/max bellybuttons", null);

		agg.register(new SimpleAvroPath<String>("email"), new Counter<Object>(Nulls.NotNull), "non-null emails", null);
		
		agg.register(new SimpleAvroPath<Integer>("bellybuttons"), new GroupingHistogram<Number>(Numbers.OddEven), "bellybuttons odd/even", null);
	}

	@Test
	public void acceptAndCountAndClear() {

		List<Person> people = PeopleBuilder.createPeople(10);
		for (Person p : people) agg.accept(p);

		Assert.assertEquals(agg.count(), 10);

		agg.clear();

		Assert.assertEquals(agg.count(), 0);
	}

	@Test
	public void compute() {

		agg.clear();

		List<Person> people = PeopleBuilder.createPeople(10);
		for (Person p : people) {
			agg.accept(p);
		}

		Map<String, Object> resultsByFunction = agg.compute(System.out);

		Assert.assertEquals(8, resultsByFunction.size());

		long count = (Integer)resultsByFunction.get("bellybuttonCount");
		Assert.assertEquals(10, count);

		count = (Integer)resultsByFunction.get("non-null emails");
		Assert.assertEquals(5, count);
		
		Map<String, Integer> counts = (Map<String, Integer>)resultsByFunction.get("bellybuttons odd/even");
		Assert.assertNull(counts.get("even"));
		Assert.assertEquals(10, ((Integer)counts.get("odd")).intValue());
	}


	@Test
	public void testScheduler() {
		
//		List<Person> people = PeopleBuilder.createPeople(10);		
		final AtomicInteger callbackCount = new AtomicInteger(0);
		
		Runnable callback = new Runnable() {
			public void run() {
				System.out.println("count: " + callbackCount.incrementAndGet());
			}
		};
		
		agg.schedule(2, 0, callback);	

//		for (Person p : people) agg.accept(p);
		
		try {
			Thread.sleep(8500);
			} catch (InterruptedException ioe) {
				
			}

		Assert.assertEquals(callbackCount.get(), 5);
	}
}
