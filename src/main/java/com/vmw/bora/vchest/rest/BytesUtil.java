package com.vmw.bora.vchest.rest;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class BytesUtil {
	public static byte[] getArray(ByteBuffer bytes) {
		int length = bytes.remaining();

		if (bytes.hasArray()) {
			int boff = bytes.arrayOffset() + bytes.position();
			if (boff == 0 && length == bytes.array().length)
				return bytes.array();
			else
				return Arrays.copyOfRange(bytes.array(), boff, boff + length);
		}
		// else, DirectByteBuffer.get() is the fastest route
		byte[] array = new byte[length];
		bytes.duplicate().get(array);
		return array;
	}
}
