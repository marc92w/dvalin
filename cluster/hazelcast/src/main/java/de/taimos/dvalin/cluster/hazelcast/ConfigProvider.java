package de.taimos.dvalin.cluster.hazelcast;

/*-
 * #%L
 * Dvalin Hazelcast support
 * %%
 * Copyright (C) 2016 - 2017 Taimos GmbH
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

import com.hazelcast.config.Config;

@FunctionalInterface
public interface ConfigProvider {
    
    /**
     * called by the bean creator to allow custom modifications to the configuration
     *
     * @param config the configuration to modify
     */
    void configure(Config config);
    
}
