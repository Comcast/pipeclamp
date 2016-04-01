package com.pipeclamp.classifiers;

import com.pipeclamp.api.Classifier;
import com.pipeclamp.util.StringUtil;

/**
 * 
 * @author Brian Remedios
 */
public interface Strings {

	Classifier<String> AreaCode = new BasicClassifier<String>("areaCode", "Retrieves the area code of a north american phone number. Null if it can't") {

		@Override
		public String classify(String item) {
			String digitsOnly = StringUtil.digitsIn(item);
			if (digitsOnly == null || digitsOnly.length() != 10) return null;
			return digitsOnly.substring(0, 3);
		}
	};
}
