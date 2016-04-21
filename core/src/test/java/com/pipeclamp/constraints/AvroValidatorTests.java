package com.pipeclamp.constraints;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.pipeclamp.ErrorEventBuilder;
import com.pipeclamp.avro.AvroValidator;
import com.pipeclamp.constraints.string.RegexConstraint;
import com.pipeclamp.constraints.string.RegexDescriptor;
import com.pipeclamp.constraints.string.StringLengthConstraint;
import com.pipeclamp.path.SimpleAvroPath;


/**
 *
 * @author Brian Remedios
 */
public class AvroValidatorTests {

	private AvroValidator validator;

	public AvroValidatorTests() { }

	@BeforeSuite
	protected void setup() {
//		validator = new AvroValidator(ErrorEvent.getClassSchema(), false);
//		validator.register(new StringLengthConstraint("parterLength", false, 3, 30), new SimpleAvroPath<String>("header", "partner"));
//		validator.register(new RegexConstraint(RegexDescriptor.Hostname), new SimpleAvroPath<String>("header", "hostname"));

		System.out.println("Validator config:");
		System.out.println(validator);
	}

	@Test
	public void testHostnameErrors() {

		final String hostNamePath = "header/hostname";

		for (String host : ErrorEventBuilder.InvalidHostNames) {
//			ErrorEvent ee = ErrorEventBuilder.newErrorEvent(host, "comcast");
//			Map<Path<GenericRecord,?>, Collection<Violation>> issuesByPath = validator.validate(ee);

//			ConstraintUtil.showIssues2(issuesByPath, System.out);
//			Assert.assertTrue(issuesByPath.size() == 1);
//			Path<GenericRecord,?> errorPath = issuesByPath.keySet().iterator().next();
//			Assert.assertEquals(hostNamePath, errorPath.toString());
		}

		for (String host : ErrorEventBuilder.ValidHostNames) {
	//		ErrorEvent ee = ErrorEventBuilder.newErrorEvent(host, "comcast");
	//		Map<Path<GenericRecord,?>, Collection<Violation>> issuesByPath = validator.validate(ee);

//			Assert.assertTrue(issuesByPath.isEmpty());
		}
	}
}
