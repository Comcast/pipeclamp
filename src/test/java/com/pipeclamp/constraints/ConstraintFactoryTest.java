package com.pipeclamp.constraints;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Type;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.avro.AvroConfiguration;
import com.pipeclamp.constraints.number.MathConstraint;
import com.pipeclamp.constraints.number.NumericConstraint;
import com.pipeclamp.constraints.string.StringLengthConstraint;
import com.pipeclamp.constraints.string.WordSetConstraint;

/**
 * 
 * @author Brian Remedios
 */
public class ConstraintFactoryTest {

	private static final Schema.Type[] ConstraintTypes = new Schema.Type[] {
			Type.STRING, Type.INT, Type.LONG, Type.RECORD
			};

	private static final ConstraintBuilder<?>[] Builders = new ConstraintBuilder[] {
		StringLengthConstraint.Builder, WordSetConstraint.Builder,
		NumericConstraint.Builder, MathConstraint.Builder
		};

	private static final ConstraintFactory<Schema.Type> Factory = AvroConfiguration.ConstraintFactory;
			
  @Test
  public void showOn() {
	  Factory.showOn(System.out);
  }

  @Test
  public void buildersForTest() {

//	  for (Schema.Type type : ConstraintTypes) {
//		  ConstraintBuilder<?> builder = Factory.builderFor(type, "kl");
//		  Assert.assertNotNull(builder);
//	  }

//	  for (ConstraintBuilder<?> cb : Builders) {
//		  Parameter<?>[] params = cb.parameters();
//		  for (Parameter<?> p : params) {
//			  ConstraintBuilder<?> builder = Factory.builderFor(p.id(), "");
//			  Assert.assertNotNull(builder);
//		  	}
//	  }
  }
}
