/*
 *
 *  *  Copyright 2014 Orient Technologies LTD (info(at)orientechnologies.com)
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *  *
 *  * For more information: http://www.orientechnologies.com
 *
 */
package com.orientechnologies.orient.stresstest.workload;

import com.orientechnologies.orient.stresstest.ODatabaseIdentifier;
import com.orientechnologies.orient.stresstest.OStressTesterSettings;

/**
 * Represents a workload for the stress test.
 *
 * @author Luca Garulli
 */
public interface OWorkload {
  String getName();
  
  void parseParameters(String params);

  void execute(OStressTesterSettings settings, ODatabaseIdentifier databaseIdentifier);

  String getPartialResult();

  String getFinalResult();

  String getFinalResultAsJson();
}