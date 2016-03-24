package com.pipeclamp.constraints;

import com.pipeclamp.validation.Validator;



/**
 *
 * @author Brian Remedios
 */
public class ValidatorTests {

	private Validator validator;

	public ValidatorTests() { }

//	@BeforeSuite
//	protected void setup() {
//		validator = new Validator(ErrorEvent.getClassSchema(), false);
//		validator.register(new RegexConstraint(RegexDescriptor.Hostname), "header", "hostname");
//
//		System.out.println("Validator config:");
//		System.out.println(validator);
//	}

//	@Test
//	public void testHostnameErrors() {
//
//		String[] hostNamePath = new String[] {"header", "hostname"};
//
//		for (String host : ErrorEventBuilder.InvalidHostNames) {
//			ErrorEvent ee = ErrorEventBuilder.newErrorEvent(host, "comcast");
//			Map<String[], Collection<Violation>> issuesByPath = validator.validate(ee);
//
//			ConstraintUtil.showIssues(issuesByPath, System.out);
//			Assert.assertTrue(issuesByPath.size() == 1);
//			String[] errorPath = issuesByPath.keySet().iterator().next();
//			Assert.assertTrue(Arrays.deepEquals(hostNamePath, errorPath));
//		}
//
//		for (String host : ErrorEventBuilder.ValidHostNames) {
//			ErrorEvent ee = ErrorEventBuilder.newErrorEvent(host, "comcast");
//			Map<String[], Collection<Violation>> issuesByPath = validator.validate(ee);
//
//			Assert.assertTrue(issuesByPath.isEmpty());
//		}
//	}
}
