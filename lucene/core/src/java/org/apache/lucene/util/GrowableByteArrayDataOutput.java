package org.apache.lucene.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.store.DataOutput;

/**
 * A {@link DataOutput} that can be used to build a byte[].
 * @lucene.internal
 */
public final class GrowableByteArrayDataOutput extends DataOutput {

  /** The bytes */
  public byte[] bytes;
  /** The length */
  public int length;

  /** Create a {@link GrowableByteArrayDataOutput} with the given initial capacity. */
  public GrowableByteArrayDataOutput(int cp) {
    this.bytes = new byte[ArrayUtil.oversize(cp, 1)];
    this.length = 0;
  }

  @Override
  public void writeByte(byte b) {
    if (length >= bytes.length) {
      bytes = ArrayUtil.grow(bytes);
    }
    bytes[length++] = b;
  }

  @Override
  public void writeBytes(byte[] b, int off, int len) {
    final int newLength = length + len;
    if (newLength > bytes.length) {
      bytes = ArrayUtil.grow(bytes, newLength);
    }
    System.arraycopy(b, off, bytes, length, len);
    length = newLength;
  }

}