package de.taimos.dvalin.interconnect.model.ivo.util.converter;
/*
 * #%L
 * Dvalin interconnect transfer data model
 * %%
 * Copyright (C) 2016 Taimos GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author psigloch
 */
public class PrimitiveValueConverter implements IValueConverter {

	@Override
	public <Destination> Object convert(Object originalFieldValue, Destination target, Field originalField, Field targetField) {
		if((originalFieldValue instanceof BigDecimal) && (String.class.isAssignableFrom(targetField.getType()))) {
			return String.valueOf(originalFieldValue);
		}
		if((originalFieldValue instanceof String) && (BigDecimal.class.isAssignableFrom(targetField.getType()))) {
			return new BigDecimal((String) originalFieldValue);
		}
		if((originalFieldValue instanceof Date) && (DateTime.class.isAssignableFrom(targetField.getType()))) {
			return new DateTime(originalFieldValue);
		}
		if((originalFieldValue instanceof DateTime) && (Date.class.isAssignableFrom(targetField.getType()))) {
			return new DateTime(originalFieldValue).toDate();
		}

		return null;
	}
}
