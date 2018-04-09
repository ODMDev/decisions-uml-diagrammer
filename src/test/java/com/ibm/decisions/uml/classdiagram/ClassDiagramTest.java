/*
* Copyright IBM Corp. 2018
*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*
**/
package com.ibm.decisions.uml.classdiagram;

import ilog.rules.bom.IlrObjectModel;
import ilog.rules.bom.dynamic.IlrDynamicObjectModel;
import ilog.rules.bom.mutable.IlrMutableObjectModel;
import ilog.rules.bom.serializer.IlrJavaSerializer;
import ilog.rules.bom.serializer.IlrSyntaxError;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.*;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Class diagram writer test, currently only testing that the writing doesn't end in an exception.
 * @author Jean-Louis Ardoint
 */
public class ClassDiagramTest extends TestCase {
  public void testLoanValidation() throws Exception {
    IlrObjectModel bom = readBOM("com/ibm/decisions/uml/classdiagram/loanvalidation.bom");
    StringWriter stringWriter = new StringWriter();
    ClassDiagramWriter writer = new ClassDiagramWriter(new PrintWriter(stringWriter));
    writer.writeModel(bom);
    System.out.println(stringWriter.toString());
  }

  public void testDSICreditcard() throws Exception {
    IlrObjectModel bom = readBOM("com/ibm/decisions/uml/classdiagram/dsi-creditcard.bom");
    StringWriter stringWriter = new StringWriter();
    ClassDiagramWriter writer = new ClassDiagramWriter(new PrintWriter(stringWriter));
    writer.writeModel(bom);
    System.out.println(stringWriter.toString());

  }

  public static IlrMutableObjectModel readBOM(String name) throws IOException {
    IlrDynamicObjectModel bom = new IlrDynamicObjectModel(IlrObjectModel.Kind.BUSINESS);
    try {
      Reader reader = getReader(name);
      IlrJavaSerializer javaSerializer = new IlrJavaSerializer();
      javaSerializer.readObjectModel(bom, reader);
    } catch (IlrSyntaxError e) {
      writeError(e);
    }
    return bom;
  }



  public static Reader getReader(String name) {
    InputStream stream = ClassDiagramTest.class.getClassLoader().getResourceAsStream(name);
    Reader reader = new InputStreamReader(stream);
    return new BufferedReader(reader);
  }


  public static void writeError(IlrSyntaxError error) {
    String[] messages = error.getErrorMessages();
    for (String message : messages)
      System.err.println(message);
    Assert.fail(error.getMessage());
  }

}